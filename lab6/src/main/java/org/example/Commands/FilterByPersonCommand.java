package org.example.Commands;

import org.example.ForFields.Person;
import org.example.Model.CollectionManager;
import org.example.Model.Ticket;

import java.util.Objects;
import java.util.stream.Collectors;

public class FilterByPersonCommand implements BaseCommand{
    @Override
    public String execute(CollectionManager collectionManager, Integer id, String path, Ticket ticket, Person person) {
        if (collectionManager.getTickets().isEmpty()) {
            return "\u001B[31mНет билетов\u001B[0m";
        }

        String result = collectionManager.getTickets().stream()
                .filter(Objects::nonNull)
                .filter(t -> t.getPerson() != null)
                .filter(t -> Objects.equals(person.getEyeColor().getNumber(), t.getPerson().getEyeColor().getNumber()))
                .filter(t -> Objects.equals(person.getNationality().getNumber(), t.getPerson().getNationality().getNumber()))
                .map(Ticket::toString)
                .collect(Collectors.joining("\n"));

        return result.isEmpty()
                ? "Совпадений не найдено"
                : "Билеты с похожими людьми\n" + result;
    }

    @Override
    public String getDescription() {
        return "\u001B[35m" + "filter_by_person" + "\u001B[0m" + " - Найти билеты с похожими людьми";
    }
}
