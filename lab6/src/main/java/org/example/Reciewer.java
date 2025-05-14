package org.example;

import org.example.ForFields.*;
import org.example.Model.CollectionManager;
import org.example.Model.Ticket;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Reciewer {
    ArrayList<Integer> IDES = new ArrayList<>();

    public Ticket addTicketWithAll (Integer idos, String name, Coordinates coordinates, Integer price, int discount, TicketType type, Person person){
        return new Ticket(idos,name,coordinates,LocalDate.now(),price,discount,type,person);
    }

    public void updateIDWithAll(CollectionManager collectionManager,Integer idos, String name, Coordinates coordinates, Integer price, int discount, TicketType type, Person person){
        Reciewer reciewer = new Reciewer();
        reciewer.removeTicketbyID(collectionManager,idos);
        reciewer.addTicketWithAll(idos,name,coordinates,price,discount,type,person);
        System.out.println("Элемент по id = " + idos + " обновлен");
    }

    public void removeTicketbyID(CollectionManager collectionManager,Integer id){
        if(collectionManager.getTickets().isEmpty()){
            System.out.println("\u001B[31m" + "Нет билетов совсем" + "\u001B[0m");
            return;
        }
        for(Ticket ticket: collectionManager.getTickets()){
            if(Objects.equals(id, ticket.getId())){
                collectionManager.getTickets().remove(ticket);
                System.out.println("\u001B[32m" + "Билет по id = " + id + " удален" + "\u001B[0m");
                return;
            }
        }
        System.out.println("\u001B[31m" + "Нет билетов c таким id" + "\u001B[0m");
    }

    public Ticket createTicket(Integer Id){
        Scanner scanner = new Scanner(System.in);
        Ticket ticket = new Ticket();


        String ticketName = null;
        while (true){
            System.out.println("Введите название билета(String): ");
            ticketName = scanner.nextLine();
            if (ticketName.isEmpty()){
                System.out.println("\u001B[31m" + "Название не может быть пустое" + "\u001B[0m");
            }
            else{
                break;
            }
        }
        ticket.setName(ticketName);

        Integer price = null;
        while (true){
            System.out.println("Введите цену билета(Integer): ");
            try {
                price = Integer.valueOf(scanner.nextLine());
                if (price <= 0){
                    System.out.println("\u001B[31m" + "Билет должен стоить больше 0" + "\u001B[0m");
                }
                else{
                    break;
                }
            } catch (NumberFormatException e){
                System.out.println("\u001B[31m" + "Введите стоимость, используя только цифры" + "\u001B[0m");
            }
        }
        ticket.setPrice(price);

        Integer discount = null;
        while (true){
            System.out.println("Введите цену скидки(int): ");
            try {
                discount = Integer.valueOf(scanner.nextLine());
                if (discount <= 0 || discount > 100){
                    System.out.println("\u001B[31m" + "Скидка должна быть в диапазоне от 1 до 100" + "\u001B[0m");
                }
                else{
                    break;
                }
            } catch (NumberFormatException e){
                System.out.println("\u001B[31m" + "Введите скидку, используя только цифры" + "\u001B[0m");
            }
        }
        ticket.setDiscount(discount);

        if (Id == null){
            if (IDES.isEmpty()){
                ticket.setId(1);
                IDES.add(1);
            }else {
                ticket.setId(IDES.get(IDES.size()-1)+1);
                IDES.add(IDES.get(IDES.size()-1)+1);
            }
        } else{
            ticket.setId(Id);
        }

        ticket.setCreationDate(LocalDate.now());

        Long x = null;
        while (true){
            System.out.println("Введите координаты по X(long):");
            try{
                x = Long.valueOf(scanner.nextLine());
                break;
            }catch (NumberFormatException e){
                System.out.println("\u001B[31m" + "Введите координату, используя только цифры" + "\u001B[0m");
            }
        }

        Integer y = null;
        while (true){
            System.out.println("Введите координаты по Y(Integer):");
            try{
                y = Integer.valueOf(scanner.nextLine());
                if (y > -376){
                    break;
                }
                else{
                    System.out.println("\u001B[31m" + "Координата по Y должна быть больше -376" + "\u001B[0m");
                }
            }catch (NumberFormatException e){
                System.out.println("\u001B[31m" + "Введите координату, используя только цифры" + "\u001B[0m");
            }
        }

        Coordinates coordinates = new Coordinates(x,y);
        ticket.setCoordinates(coordinates);

        String tickettype = null;
        while (true) {
            TicketType.printAllTicketTypes();
            System.out.println("\u001B[35m" + "Выберите и впишите один тип билета из списка(цифрой):" + "\u001B[0m");
            tickettype = scanner.nextLine();
            boolean isValidType = false;
            try {
                for (TicketType type : TicketType.values()) {
                    if (Objects.equals(type.getChisl(), Integer.parseInt(tickettype))) {
                        ticket.setType(type);
                        isValidType = true;
                        break;
                    }
                }
            } catch (NumberFormatException ignored){

            }
            if (isValidType) {
                break;
            } else {
                System.out.println("\u001B[31m" + "Такого типа билета не существует" + "\u001B[0m");
            }
        }

        Person person = new Person();

        String IDI = null;
        while (true){
            System.out.println("Запишите паспорт ID человека(String и длина должна быть больше 8 символов):");
            IDI = scanner.nextLine();
            if (IDI.length() >= 8){
                person.setPassportID(IDI);
                break;
            }
            else {
                System.out.println("\u001B[31m" + "Длина ID должна быть не меньше 8" + "\u001B[0m");
            }
        }

        String color = null;
        while (true) {
            Color.printAllColors();
            System.out.println("\u001B[35m" + "Выберите и впишите один цвет глаз у человека(цифрой):" + "\u001B[0m");
            color = scanner.nextLine();
            boolean isValidType = false;
            try {
                for (Color calor : Color.values()) {
                    if (Objects.equals(calor.getNumber(), Integer.parseInt(color))) {
                        person.setEyeColor(calor);
                        isValidType = true;
                        break;
                    }
                }
            } catch (NumberFormatException ignored){
            }
            if (isValidType) {
                break;
            } else {
                System.out.println("\u001B[31m" + "Такого цвета глаз не существует" + "\u001B[0m");
            }
        }

        String natia = null;
        while (true) {
            Country.printAllCountries();
            System.out.println("\u001B[35m" + "Выберите и впишите национальность у человека(цифрой):" + "\u001B[0m");
            natia = scanner.nextLine();
            boolean isValidType = false;
            try {
                for (Country country : Country.values()) {
                    if (Objects.equals(country.getNumber(), Integer.parseInt(natia))) {
                        person.setNationality(country);
                        isValidType = true;
                        break;
                    }
                }
            } catch (NumberFormatException ignored){
            }
            if (isValidType) {
                break;
            } else {
                System.out.println("\u001B[31m" + "Такой национальности не существует" + "\u001B[0m");
            }
        }

        ticket.setPerson(person);

        return ticket;
    }
    public Person makePerson(){
        Person person = new Person();
        System.out.println("Чтобы найти похожих людей нужно ввести несколько полей:");
        String color = null;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            Color.printAllColors();
            System.out.println("\u001B[35m" + "Выберите и впишите один цвет глаз у человека(цифрой):" + "\u001B[0m");
            color = scanner.nextLine();
            boolean isValidType = false;
            try {
                for (Color calor : Color.values()) {
                    if (Objects.equals(calor.getNumber(), Integer.parseInt(color))) {
                        isValidType = true;
                        break;
                    }
                }
            } catch (NumberFormatException e){
                System.out.println("Введите цифру только");
                continue;
            }
            if (isValidType) {
                break;
            } else {
                System.out.println("\u001B[31m" + "Такого цвета глаз не существует" + "\u001B[0m");
            }
        }

        String natia = null;
        while (true) {
            Country.printAllCountries();
            System.out.println("\u001B[35m" + "Выберите и впишите национальность у человека(цифрой):" + "\u001B[0m");
            natia = scanner.nextLine();
            boolean isValidType = false;
            try {
                for (Country country : Country.values()) {
                    if (Objects.equals(country.getNumber(), Integer.parseInt(natia))) {
                        isValidType = true;
                        break;
                    }
                }
            } catch (NumberFormatException e){
                System.out.println("Введите цифру только");
                continue;
            }
            if (isValidType) {
                break;
            } else {
                System.out.println("\u001B[31m" + "Такой национальности не существует" + "\u001B[0m");
            }
        }
        person.setNationality(Country.getCountryByNumber(Integer.parseInt(natia)));
        person.setEyeColor(Color.getColorByNumber(Integer.parseInt(color)));
        return person;
    }
}
