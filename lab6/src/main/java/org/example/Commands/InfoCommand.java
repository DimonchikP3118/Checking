package org.example.Commands;

import org.example.ForFields.Person;
import org.example.Model.CollectionManager;
import org.example.Model.Ticket;

public class InfoCommand implements BaseCommand{
    @Override
    public String execute(CollectionManager collectionManager, Integer id, String path, Ticket ticket, Person person) {
        return "Тип коллекции: " + collectionManager.getTickets().getClass() + "\n Количество элементов коллекции: " + collectionManager.getTickets().size() + "\n Дата инициализации: " + collectionManager.getInitializationDate();
    }

    @Override
    public String getDescription() {
        return "\u001B[35m" + "info" + "\u001B[0m" + " - Информация о коллекции";
    }
}
