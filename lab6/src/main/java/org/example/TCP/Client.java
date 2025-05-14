package org.example.TCP;

import org.example.CSV.CSVReader;
import org.example.DATA.Request;
import org.example.DATA.Response;
import org.example.ForFields.*;
import org.example.Model.Ticket;
import org.example.Reciewer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Reciewer reciewer = new Reciewer();

        while (true) {
            try {
                // Устанавливаем соединение с сервером
                try (SocketChannel socketChannel = SocketChannel.open()) {
                    socketChannel.connect(new InetSocketAddress("localhost", 8080));

                    // Отправка запроса
                    while (true){
                        try {
                            System.out.print("Введите команду: ");
                            String input = scanner.nextLine();
                            Request request;

                            if (Objects.equals(input.split(" ")[0], "update_by_id")) {
                                request = handleUpdateById(input, reciewer,args);
                                if (request == null) continue;
                            }
                            else if (Objects.equals("execute_script", input.split(" ")[0])) {
                                handleExecuteScript(input, reciewer,socketChannel);
                                continue;
                            }
                            else {
                                request = command(input, reciewer);
                                if (request == null) continue;
                            }
                            makeRequest(request,socketChannel);

                            // Получение ответа
                            getResponse(socketChannel);
                        } catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    }

                }
            } catch (IOException e) {
                System.err.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private static Request handleUpdateById(String input, Reciewer reciewer,String[] args) throws IOException, ClassNotFoundException {
        try {
            int id = Integer.parseInt(input.split(" ")[1]);
            Request checkRequest = new Request("check", id);

            try (SocketChannel checkSocket = SocketChannel.open()) {
                checkSocket.connect(new InetSocketAddress("localhost", Integer.parseInt(args[0])));

                // Отправка запроса проверки
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(checkRequest);
                objectOutputStream.close();

                ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
                checkSocket.write(buffer);

                // Получение ответа
                buffer = ByteBuffer.allocate(81920);
                checkSocket.read(buffer);

                ByteArrayInputStream bi = new ByteArrayInputStream(buffer.array());
                ObjectInputStream oi = new ObjectInputStream(bi);
                Response response = (Response) oi.readObject();

                if (response.getBe()) {
                    System.out.println("Создадим билет для обновления");
                    Ticket ticket = reciewer.createTicket(id);
                    return new Request(ticket, input.split(" ")[0]);
                } else {
                    System.out.println("Нет подходящих билетов");
                    return null;
                }
            }
        } catch (Exception e) {
            System.out.println("Wrong argument");
            return null;
        }
    }

    private static void handleExecuteScript(String input, Reciewer reciewer,SocketChannel socketChannel) {
        try {
            ArrayList<String> commands = CSVReader.readCsv(input.split(" ")[1]);
            for (String command : commands) {
                String[] elements = command.split(" ");
                Request request = null;

                if (Objects.equals(elements[0], "add")) {
                    try {
                        Ticket ticket = reciewer.addTicketWithAll(null, elements[1],
                                new Coordinates(Long.parseLong(elements[2]), Integer.valueOf(elements[3])),
                                Integer.valueOf(elements[4]), Integer.parseInt(elements[5]),
                                TicketType.getTypeByNumber(Integer.parseInt(elements[6])),
                                new Person(elements[7], Color.getColorByNumber(Integer.parseInt(elements[8])),
                                        Country.getCountryByNumber(Integer.parseInt(elements[9]))));
                        request = new Request(ticket, elements[0]);
                    } catch (Exception e) {
                        System.out.println("Wrong argument");
                        continue;
                    }
                }
                else if (Objects.equals(elements[0], "count_less_than_type")) {
                    try {
                        TicketType type = TicketType.getTypeByEng(elements[1]);
                        request = new Request(type, elements[0]);
                    } catch (Exception e) {
                        System.out.println("Wrong argument");
                        continue;
                    }
                }
                else if (Objects.equals(elements[0], "filter_by_person")) {
                    try {
                        Person person = new Person(null,
                                Color.getColorByNumber(Integer.parseInt(elements[1])),
                                Country.getCountryByNumber(Integer.parseInt(elements[2])));
                        request = new Request(elements[0], person);
                    } catch (Exception e) {
                        System.out.println("Wrong argument");
                        continue;
                    }
                }
                else if (Objects.equals(elements[0], "remove_by_id")) {
                    try {
                        request = new Request(elements[0], Integer.parseInt(elements[1]));
                    } catch (Exception e) {
                        System.out.println("Wrong argument");
                        continue;
                    }
                }
                else if (Objects.equals(elements[0], "update_by_id")) {
                    try {
                        Ticket ticket = reciewer.addTicketWithAll(
                                Integer.parseInt(elements[1]), elements[2],
                                new Coordinates(Long.parseLong(elements[3]), Integer.valueOf(elements[4])),
                                Integer.valueOf(elements[5]), Integer.parseInt(elements[6]),
                                TicketType.getTypeByNumber(Integer.parseInt(elements[7])),
                                new Person(elements[8], Color.getColorByNumber(Integer.parseInt(elements[9])),
                                        Country.getCountryByNumber(Integer.parseInt(elements[10]))));
                        request = new Request(ticket, elements[0]);
                    } catch (Exception e) {
                        System.out.println("Wrong argument");
                        continue;
                    }
                }
                else {
                    request = command(command, reciewer);
                    if (request == null) continue;
                }
                makeRequest(request,socketChannel);
                getResponse(socketChannel);
            }
        } catch (Exception e) {
            System.out.println("Wrong argument");
        }
    }

    public static Request command(String input, Reciewer reciewer) {
        Request request;
        try {
            if (Objects.equals(input.split(" ")[0], "add")) {
                Ticket ticket = reciewer.createTicket(null);
                request = new Request(ticket, input);
            }
            else if (Objects.equals(input.split(" ")[0], "exit")) {
                System.exit(0);
                request = new Request(input);
            }
            else if (Objects.equals(input.split(" ")[0], "remove_by_id")) {
                request = new Request(input.split(" ")[0], Integer.parseInt(input.split(" ")[1]));
            }
            else if (Objects.equals(input.split(" ")[0], "filter_by_person")) {
                Person person = reciewer.makePerson();
                request = new Request(input.split(" ")[0], person);
            }
            else if (Objects.equals(input.split(" ")[0], "count_less_than_type")) {
                TicketType type = TicketType.getTypeByEng(input.split(" ")[1]);
                request = new Request(type, input.split(" ")[0]);
            }
            else {
                request = new Request(input);
            }
        } catch (Exception e) {
            System.out.println("Wrong argument");
            return null;
        }
        return request;
    }
    public static void makeRequest(Request request,SocketChannel socketChannel) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);
        objectOutputStream.close();

        ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        socketChannel.write(buffer);
        System.out.println("Отправлено сообщение: " + request);
    }
    public static void getResponse(SocketChannel socketChannel) throws IOException, ClassNotFoundException {
        ArrayList<ByteBuffer> bufferList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            long bytesRead = socketChannel.read(buffer);
            System.out.println(bytesRead);
            if (bytesRead > 0) {
                bufferList.add(buffer);
            }
            if (bytesRead < buffer.capacity()) {
                break;
            }
        }
        ByteBuffer bigBuffer = ByteBuffer.allocate(bufferList.size() * 8192);
        for (ByteBuffer byteBuffer : bufferList) {
            bigBuffer.put(byteBuffer.array());
        }
        System.out.println(bigBuffer.capacity());
//        ByteBuffer buffer = ByteBuffer.allocate(999999999);
//        socketChannel.read(bigBuffer);

        ByteArrayInputStream bi = new ByteArrayInputStream(bigBuffer.array());
        ObjectInputStream oi = new ObjectInputStream(bi);
        Response response = (Response) oi.readObject();

        System.out.println("Получено сообщение от сервера: " + response);
    }
}