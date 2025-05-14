package org.example.Commands;

import org.example.ForFields.Person;
import org.example.Model.CollectionManager;
import org.example.Model.Ticket;

public class ClearCommand implements BaseCommand{
    @Override
    public String execute(CollectionManager collectionManager, Integer id, String path, Ticket ticket, Person person) {
        if (collectionManager.getTickets().isEmpty()) {
            return "\u001B[31m" + "Нет билетов" + "\u001B[0m";
        }
        collectionManager.getTickets().clear();
        return "Коллекция очищена";
    }

    @Override
    public String getDescription() {
        return "\u001B[35m" + "clear" + "\u001B[0m" + " - Очистка коллекции";
    }
}
