package org.example.Commands;

import org.example.ForFields.Person;
import org.example.Model.CollectionManager;
import org.example.Model.Ticket;

public interface BaseCommand {
    String execute(CollectionManager collectionManager, Integer id, String path, Ticket ticket, Person person);

    String getDescription();
}
