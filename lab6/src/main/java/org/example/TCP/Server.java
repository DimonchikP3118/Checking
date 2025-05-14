package org.example.TCP;

import org.example.Commands.CommandManager;
import org.example.Commands.SaveCommand;
import org.example.DATA.Request;
import org.example.DATA.Response;
import org.example.Model.CollectionManager;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class Server {
    private static CollectionManager collectionManager;
    private static CommandManager commandManager;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        collectionManager = new CollectionManager();
        commandManager = new CommandManager();

        startConsoleInputHandler();

        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.bind(new InetSocketAddress(8080));

        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("TCP-сервер запущен на порту " + 8080);

        while (true) {
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                keyIterator.remove();

                try {
                    if (key.isAcceptable()) {
                        handleAccept(key, selector);
                    } else if (key.isReadable()) {
                        handleRead(key, selector);
                    }
                } catch (SocketException e) {
                    System.err.println("Клиент отключился: " + e.getMessage());
                    key.channel().close();
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("Ошибка обработки запроса: " + e.getMessage());
                    key.channel().close();
                }
            }
        }
    }

    private static void startConsoleInputHandler() {
        new Thread(() -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                while (true) {
                    String input = reader.readLine();
                    if (input.equalsIgnoreCase("save")) {
                        SaveCommand.SaveCollection(collectionManager);
                        collectionManager.saveTickets();
                    } else if (input.equalsIgnoreCase("exit")) {
                        SaveCommand.SaveCollection(collectionManager);
                        collectionManager.saveTickets();
                        System.exit(0);
                    }
                }
            } catch (IOException e) {
                System.err.println("Ошибка чтения ввода: " + e.getMessage());
            }
        }).start();
    }

    private static void handleAccept(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverChannel.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ);
        System.out.println("Новое подключение от " + clientChannel.getRemoteAddress());
    }

    private static void handleRead(SelectionKey key, Selector selector) throws IOException, ClassNotFoundException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(8192);
        int bytesRead;

        try {
            bytesRead = clientChannel.read(buffer);
        } catch (IOException e) {
            key.cancel();
            clientChannel.close();
            System.out.println("Клиент отключился");
            return;
        }

        if (bytesRead == -1) {
            key.cancel();
            clientChannel.close();
            System.out.println("Клиент закрыл соединение");
            return;
        }

        if (bytesRead > 0) {
            buffer.flip();
            ByteArrayInputStream bi = new ByteArrayInputStream(buffer.array(), 0, bytesRead);
            ObjectInputStream oi = new ObjectInputStream(bi);
            Request request = (Request) oi.readObject();

            System.out.println("Получено сообщение от клиента " + clientChannel.getRemoteAddress() + ": " + request);

            Response response = processRequest(request);
            handleWrite(clientChannel,response);
        }
    }

    private static Response processRequest(Request request) {
        String response;
        if (request == null) {
            response = "Wrong argument";
        } else if (Objects.equals(request.getMessage(), "add")) {
            response = commandManager.response(request.getMessage(), collectionManager, request.getTicket(), null, null, null);
        } else if (Objects.equals(request.getMessage(), "check")) {
            return new Response(collectionManager.check(request.getId()));
        } else if (Objects.equals(request.getMessage(), "update_by_id")) {
            response = commandManager.response(request.getMessage(), collectionManager, request.getTicket(), null, null, null);
        } else if (Objects.equals(request.getMessage(), "filter_by_person")) {
            response = commandManager.response(request.getMessage(), collectionManager, null, null, request.getPerson(), null);
        } else if (Objects.equals(request.getMessage(), "remove_by_id")) {
            response = commandManager.response(request.getMessage(), collectionManager, null, request.getId(), null, null);
        } else if (Objects.equals(request.getMessage(), "exit")) {
            collectionManager.saveTickets();
            response = "Server shutting down";
        } else if (Objects.equals(request.getMessage(), "count_less_than_type")) {
            response = commandManager.response(request.getMessage(), collectionManager, null, request.getType().getChisl(), null, null);
        } else {
            response = commandManager.response(request.getMessage(), collectionManager, null, null, null, null);
        }
        return new Response(response);
    }

    private static void handleWrite(SocketChannel clientChannel,Response response) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(response);
        objectOutputStream.close();

        ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        while (buffer.hasRemaining()) {
            clientChannel.write(buffer);
        }

        System.out.println("Ответ отправлен клиенту " + clientChannel.getRemoteAddress());
    }
}