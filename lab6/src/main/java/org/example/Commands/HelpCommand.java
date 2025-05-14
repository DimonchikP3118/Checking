package org.example.Commands;

import org.example.ForFields.Person;
import org.example.Model.CollectionManager;
import org.example.Model.Ticket;

public class HelpCommand implements BaseCommand{
    @Override
    public String execute(CollectionManager collectionManager, Integer id, String path, Ticket ticket, Person person) {
        CommandManager commandManager = new CommandManager();
        return commandManager.getcommandlist();
    }

    @Override
    public String getDescription() {
        return "\u001B[35m" + "help" + "\u001B[0m" + " - Справка по доступным командам";
    }
}
