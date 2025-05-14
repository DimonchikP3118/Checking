package org.example.Commands;

import org.example.ForFields.Person;
import org.example.Model.CollectionManager;
import org.example.Model.Ticket;

public class ShowCommand implements BaseCommand{
    @Override
    public String execute(CollectionManager collectionManager, Integer id, String path, Ticket ticket, Person person) {
        if (collectionManager.getTickets().isEmpty()) {
            return "\u001B[31m" + "Нет билетов" + "\u001B[0m";
        }
        StringBuilder result = new StringBuilder();
        for (Ticket tickety : collectionManager.getTickets()) {
            result.append(tickety.toString()).append("\n");
        }
        return "Общее количество билетов52: " + collectionManager.getTickets().size() + "\n" + result.toString();
    }

    @Override
    public String getDescription() {
        return "\u001B[35m" + "show" + "\u001B[0m" + " - Вывод всех билетов";
    }
}
