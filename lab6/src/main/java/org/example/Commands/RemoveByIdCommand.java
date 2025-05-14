package org.example.Commands;

import org.example.ForFields.Person;
import org.example.Model.CollectionManager;
import org.example.Model.Ticket;

import java.util.Objects;

public class RemoveByIdCommand implements BaseCommand{
    @Override
    public String execute(CollectionManager collectionManager, Integer id, String path, Ticket ticket, Person person) {
        if (collectionManager.getTickets().isEmpty()) {
            return "\u001B[31mНет билетов\u001B[0m";
        }

        Ticket ticketToRemove = collectionManager.getTickets().stream()
                .filter(t -> Objects.equals(t.getId(), id))
                .findFirst()
                .orElse(null);

        if (ticketToRemove != null) {
            collectionManager.getTickets().remove(ticketToRemove);
            return "\u001B[32mБилет по id = " + id + " удален\u001B[0m";
        }

        return "\u001B[31mНет билетов c таким id\u001B[0m";
    }

    @Override
    public String getDescription() {
        return "\u001B[35m" + "remove_by_id" + "\u001B[0m" + " - Удаление билета по id";
    }
}
