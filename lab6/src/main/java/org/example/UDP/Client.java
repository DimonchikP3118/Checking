package org.example.UDP;

import org.example.CSV.CSVReader;
import org.example.DATA.Request;
import org.example.DATA.Response;
import org.example.ForFields.*;
import org.example.Model.Ticket;
import org.example.Reciewer;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // Создаем датаграмный сокет
            DatagramSocket socket = new DatagramSocket(); // возьмет любой свободный порт
            Scanner scanner = new Scanner(System.in);
            Reciewer reciewer = new Reciewer();
            while (scanner.hasNextLine()){
                Request request;
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                String input = scanner.nextLine();
                if (Objects.equals(input.split(" ")[0], "update_by_id")) {
                    try {
                        int id = Integer.parseInt(input.split(" ")[1]);
                        Request checkRequest = new Request("check", id);

                        try (ByteArrayOutputStream byteArrayOutputStreamCheck = new ByteArrayOutputStream();
                             ObjectOutputStream objectOutputStreamCheck = new ObjectOutputStream(byteArrayOutputStreamCheck)) {

                            objectOutputStreamCheck.writeObject(checkRequest);

                            // Отправляем сообщение серверу
                            InetAddress serverAddress = InetAddress.getByName("localhost");
                            DatagramPacket sendPacket = new DatagramPacket(byteArrayOutputStreamCheck.toByteArray(), byteArrayOutputStreamCheck.size(), serverAddress, 8080);
                            socket.send(sendPacket);

                            // Получаем ответ от сервера
                            byte[] buffer = new byte[4096];
                            DatagramPacket inputPacket = new DatagramPacket(buffer, buffer.length);
                            socket.receive(inputPacket);

                            try (ObjectInputStream oi = new ObjectInputStream(new ByteArrayInputStream(inputPacket.getData()))) {
                                Response response = (Response) oi.readObject();
                                if (response.getBe()) {
                                    System.out.println("Создадим билет для обновления");
                                    Ticket ticket = reciewer.createTicket(id);
                                    request = new Request(ticket, input.split(" ")[0]);
                                } else {
                                    System.out.println("Нет подходящих билетов");
                                    continue;
                                }
                            }
                        }
                    } catch (Exception e){
                        System.out.println("Wrong argument");
                        continue;
                    }
                } else if (Objects.equals("execute_script",input.split(" ")[0])) {
                    try {
                        ArrayList<String> commands = CSVReader.readCsv(input.split(" ")[1]);
                        for (String command : commands) {
                            byteArrayOutputStream = new ByteArrayOutputStream();
                            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                            String[] elements = command.split(" ");
                            if (Objects.equals(elements[0], "add")){
                                try {
                                    Ticket ticket = reciewer.addTicketWithAll(null, elements[1], new Coordinates(Long.parseLong(elements[2]), Integer.valueOf(elements[3])), Integer.valueOf(elements[4]), Integer.parseInt(elements[5]), TicketType.getTypeByNumber(Integer.parseInt(elements[6])), new Person(elements[7], Color.getColorByNumber(Integer.parseInt(elements[8])), Country.getCountryByNumber(Integer.parseInt(elements[9]))));
                                    request = new Request(ticket, elements[0]);
                                } catch (Exception e){
                                    System.out.println("Wrong argument");
                                    continue;
                                }
                            } else if (Objects.equals(elements[0],"count_less_than_type")) {
                                try {
                                    TicketType type = TicketType.getTypeByEng(elements[1]);
                                    request = new Request(type,elements[0]);
                                } catch (Exception e){
                                    System.out.println("Wrong argument");
                                    continue;
                                }
                            } else if (Objects.equals(elements[0],"filter_by_person")) {
                                try {
                                    Person person = new Person(null, Color.getColorByNumber(Integer.parseInt(elements[1])), Country.getCountryByNumber(Integer.parseInt(elements[2])));
                                    request = new Request(elements[0], person);
                                } catch (Exception e){
                                    System.out.println("Wrong argument");
                                    continue;
                                }
                            } else if (Objects.equals(elements[0],"remove_by_id")) {
                                try {
                                    request = new Request(elements[0], Integer.parseInt(elements[1]));
                                } catch (Exception e){
                                    System.out.println("Wrong argument");
                                    continue;
                                }
                            } else if (Objects.equals(elements[0],"update_by_id")) {
                                try {
                                    Ticket ticket = reciewer.addTicketWithAll(Integer.parseInt(elements[1]), elements[2], new Coordinates(Long.parseLong(elements[3]), Integer.valueOf(elements[4])), Integer.valueOf(elements[5]), Integer.parseInt(elements[6]), TicketType.getTypeByNumber(Integer.parseInt(elements[7])), new Person(elements[8], Color.getColorByNumber(Integer.parseInt(elements[9])), Country.getCountryByNumber(Integer.parseInt(elements[10]))));
                                    request = new Request(ticket, elements[0]);
                                } catch (Exception e){
                                    System.out.println("Wrong argument");
                                    continue;
                                }
                            } else {
                                request = Client.command(input);
                                if (request == null) {
                                    System.out.println("Не удалось создать запрос");
                                    continue;
                                }
                            }
                            objectOutputStream.writeObject(request);

                            // Отправляем сообщение серверу
                            InetAddress serverAddress = InetAddress.getByName("localhost");
                            DatagramPacket sendPacket = new DatagramPacket(byteArrayOutputStream.toByteArray(), byteArrayOutputStream.size(), serverAddress, 8080);
                            socket.send(sendPacket);
                            System.out.println("Отправлено сообщение: " + request);

                            // Получаем ответ от сервера
                            byte[] buffer = new byte[4096];
                            DatagramPacket inputPacket = new DatagramPacket(buffer, buffer.length);
                            socket.receive(inputPacket);

                            ObjectInputStream oi = new ObjectInputStream(new ByteArrayInputStream(inputPacket.getData()));
                            Response response = (Response) oi.readObject();

                            System.out.println("Получено сообщение от сервера: " + response);
                        }
                        continue;
                    } catch (Exception e){
                        System.out.println("Wrong argument");
                        continue;
                    }
                } else {
                    request = Client.command(input);
                    if (request == null) {
                        System.out.println("Не удалось создать запрос");
                        continue;
                    }
                }
                objectOutputStream.writeObject(request);

                // Отправляем сообщение серверу
                InetAddress serverAddress = InetAddress.getByName("localhost");
                DatagramPacket sendPacket = new DatagramPacket(byteArrayOutputStream.toByteArray(), byteArrayOutputStream.size(), serverAddress, 8080);
                socket.send(sendPacket);
                System.out.println("Отправлено сообщение: " + request);

                // Получаем ответ от сервера
                byte[] buffer = new byte[81920];
                DatagramPacket inputPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(inputPacket);

                ObjectInputStream oi = new ObjectInputStream(new ByteArrayInputStream(inputPacket.getData()));
                Response response = (Response) oi.readObject();

                System.out.println("Получено сообщение от сервера: " + response);
            }

            // Закрываем сокет
            socket.close();
        } catch (UnknownHostException e) {
            System.err.println("Неизвестный хост: " + e.getMessage());
        } catch (IOException | ClassNotFoundException  e) {
            System.err.println("Ошибка отправки/приема данных: " + e.getMessage());
        }
    }

    public static Request command(String input){
        Request request;
        Reciewer reciewer = new Reciewer();
        try {
            if (Objects.equals(input.split(" ")[0], "add")){
                Ticket ticket = reciewer.createTicket(null);
                request = new Request(ticket,input);
            } else if (Objects.equals(input.split(" ")[0], "exit")) {
                System.exit(0);
                request = new Request(input);
            } else if (Objects.equals(input.split(" ")[0], "remove_by_id")) {
                request = new Request(input.split(" ")[0],Integer.parseInt(input.split(" ")[1]));
            }  else if (Objects.equals(input.split(" ")[0], "filter_by_person")) {
                Person person = reciewer.makePerson();
                request = new Request(input.split(" ")[0],person);
            } else if (Objects.equals(input.split(" ")[0], "count_less_than_type")) {
                TicketType type = TicketType.getTypeByEng(input.split(" ")[1]);
                request = new Request(type,input.split(" ")[0]);
            } else {
                request = new Request(input);
            }
        } catch (Exception e){
            System.out.println("Wrong argument");
            return null;
        }
        return request;
    }
}
