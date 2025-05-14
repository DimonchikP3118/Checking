package org.example.DATA;

import java.io.Serializable;

import org.example.ForFields.Person;
import org.example.ForFields.TicketType;
import org.example.Model.Ticket;

public class Request implements Serializable {
    private String message;
    private Ticket ticket;
    private TicketType type;
    private Person person;
    private Integer Id;
    private String path;

    public Request() {
    }

    public Request(String message, Integer id) {
        this.message = message;
        Id = id;
    }

    public Request(String message, String path) {
        this.message = message;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Request(String message, Person person) {
        this.message = message;
        this.person = person;
    }

    public Request(String message) {
        this.message = message;

    }

    public Request(TicketType type, String message) {
        this.type = type;
        this.message = message;
    }

    public Request(Ticket ticket, String message) {
        this.ticket = ticket;
        this.message = message;

    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Request{" +
                "message='" + message +
                '}';
    }
}
