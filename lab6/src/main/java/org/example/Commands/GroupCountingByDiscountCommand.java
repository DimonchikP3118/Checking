package org.example.Commands;

import org.example.ForFields.Person;
import org.example.Model.CollectionManager;
import org.example.Model.Ticket;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupCountingByDiscountCommand implements BaseCommand{
    @Override
    public String execute(CollectionManager collectionManager, Integer id, String path, Ticket ticket, Person person) {
        if (collectionManager.getTickets().isEmpty()) {
            return "\u001B[31mНет билетов\u001B[0m";
        }
        Map<Integer, Long> discountGroups = collectionManager.getTickets().stream()
                .collect(Collectors.groupingBy(
                        Ticket::getDiscount,
                        Collectors.counting()
                ));

        return discountGroups.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> "Скидка: " + entry.getKey() + ", количество: " + entry.getValue())
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String getDescription() {
        return "\u001B[35m" + "group_counting_by_discount" + "\u001B[0m" + " - Сгруппировать билеты по скидке";
    }
}
