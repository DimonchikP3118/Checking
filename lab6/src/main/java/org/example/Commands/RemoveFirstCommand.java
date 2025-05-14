package org.example.Commands;

import org.example.ForFields.Person;
import org.example.Model.CollectionManager;
import org.example.Model.Ticket;

public class RemoveFirstCommand implements BaseCommand{
    @Override
    public String execute(CollectionManager collectionManager, Integer id, String path, Ticket ticket, Person person) {
        if (collectionManager.getTickets().isEmpty()) {
            return "\u001B[31m" + "Нет билетов для удаления" + "\u001B[0m";
        }
        collectionManager.getTickets().remove(0);
        return "\u001B[31m" + "Первый билет удален" + "\u001B[0m";
    }

    @Override
    public String getDescription() {
        return "\u001B[35m" + "remove_first" + "\u001B[0m" + " - Удаление первого билета";
    }
}
