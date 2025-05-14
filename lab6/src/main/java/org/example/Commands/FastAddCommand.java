package org.example.Commands;

import org.example.ForFields.*;
import org.example.Model.CollectionManager;
import org.example.Model.Ticket;

import java.time.LocalDate;

public class FastAddCommand implements BaseCommand{
    @Override
    public String execute(CollectionManager collectionManager, Integer id, String path, Ticket ticket, Person person) {
        Ticket ticket1 = new Ticket(1,"Zebra", new Coordinates(2,4), LocalDate.now(),500,5, TicketType.VIP,new Person("52", Color.BLACK, Country.RUSSIA));
        Ticket ticket2 = new Ticket(2,"Alpha", new Coordinates(2,4), LocalDate.now(),500,5, TicketType.CHEAP,new Person("52", Color.BLACK, Country.RUSSIA));
        Ticket ticket3 = new Ticket(3,"Gamma", new Coordinates(2,4), LocalDate.now(),500,5, TicketType.BUDGETARY,new Person("52", Color.BLACK, Country.RUSSIA));
        collectionManager.getTickets().add(ticket1);
        collectionManager.getTickets().add(ticket2);
        collectionManager.getTickets().add(ticket3);
        return "";
    }

    @Override
    public String getDescription() {
        return "\u001B[35m" + "fast" + "\u001B[0m" + " - Быстрое создание билетов";
    }
}
