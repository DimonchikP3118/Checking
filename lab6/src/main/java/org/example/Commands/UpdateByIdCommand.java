package org.example.Commands;

import org.example.ForFields.Person;
import org.example.Model.CollectionManager;
import org.example.Model.Ticket;

import java.util.Objects;
import java.util.stream.IntStream;

public class UpdateByIdCommand implements BaseCommand{
    @Override
    public String execute(CollectionManager collectionManager, Integer id, String path, Ticket ticket, Person person) {
        if (collectionManager.getTickets().isEmpty()) {
            return "\u001B[31mНет билетов\u001B[0m";
        }

        int index = IntStream.range(0, collectionManager.getTickets().size())
                .filter(i -> Objects.equals(collectionManager.getTickets().get(i).getId(), ticket.getId()))
                .findFirst()
                .orElse(-1);

        if (index != -1) {
            collectionManager.getTickets().set(index, ticket);
            return "\u001B[32mБилет по id = " + ticket.getId() + " - обновлен\u001B[0m";
        }

        return "\u001B[31mНет билетов с таким id\u001B[0m";
    }

    @Override
    public String getDescription() {
        return "\u001B[35m" + "update_by_id" + "\u001B[0m" + " - Обновить билет по id";
    }
}
