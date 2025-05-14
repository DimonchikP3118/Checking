package org.example.Model;

import org.example.Model.Ticket;

import java.io.*;
import java.util.Date;
import java.util.Objects;
import java.util.Vector;

public class CollectionManager {
    private Vector<Ticket> tickets = new Vector<>();
    private final Date initializationDate = new Date();
    private final String filePath = "tickets.csv"; // Путь к файлу для сохранения

    public CollectionManager() {
        loadTickets();
    }

    public Vector<Ticket> getTickets() {
        return tickets;
    }

    public Date getInitializationDate() {
        return initializationDate;
    }

    public void saveTickets() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(tickets);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Boolean check(Integer id){
        for (Ticket ticket: getTickets()){
            if (Objects.equals(ticket.getId(), id)){
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private void loadTickets() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            tickets = (Vector<Ticket>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Коллекция пуста");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}