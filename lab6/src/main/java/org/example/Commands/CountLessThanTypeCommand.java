package org.example.Commands;

import org.example.ForFields.Person;
import org.example.Model.CollectionManager;
import org.example.Model.Ticket;

public class CountLessThanTypeCommand implements BaseCommand{
    @Override
    public String execute(CollectionManager collectionManager, Integer id, String path, Ticket ticket, Person person) {
        if (collectionManager.getTickets().isEmpty()) {
            return "\u001B[31m" + "Нет билетов" + "\u001B[0m";
        }
        try {
            long count = collectionManager.getTickets().stream()
                    .filter(t -> t.getType() != null && t.getType().getChisl() > id)
                    .count();
            return "Количество необходимых элементов: " + count;
        } catch (NullPointerException e) {
            return "Wrong argument";
        }
    }

    @Override
    public String getDescription() {
        return "\u001B[35m" + "count_less_than_type" + "\u001B[0m" + " - Подсчет количества билетов меньших, чем заданный";
    }
}
