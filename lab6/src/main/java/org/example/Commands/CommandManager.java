package org.example.Commands;

import org.example.ForFields.Person;
import org.example.Model.CollectionManager;
import org.example.Model.Ticket;

import java.util.HashMap;
import java.util.Objects;

public class CommandManager {

    private HashMap<String, BaseCommand> commands = new HashMap<>();

    public CommandManager() {
        commands.put("count_less_than_type",new CountLessThanTypeCommand());
        commands.put("group_counting_by_discount",new GroupCountingByDiscountCommand());
        commands.put("new_order",new NewOrderCommand());
        commands.put("remove_by_id",new RemoveByIdCommand());
        commands.put("filter_by_person",new FilterByPersonCommand());
        commands.put("show",new ShowCommand());
        commands.put("remove_first",new RemoveFirstCommand());
        commands.put("add",new AddCommand());
        commands.put("help",new HelpCommand());
        commands.put("clear",new ClearCommand());
        commands.put("remove_last",new RemoveLastCommand());
        commands.put("update_by_id",new UpdateByIdCommand());
        commands.put("info",new InfoCommand());
        commands.put("fast",new FastAddCommand());
    }
    public static void exec(String input, CollectionManager collectionManager, Ticket ticket, Integer id, Person person){
        CommandManager commandManager = new CommandManager();
        commandManager.response(input,collectionManager,ticket,id,person,null);
    }

    public String response(String input, CollectionManager collectionManager, Ticket ticket, Integer id, Person person, String path){
        String command = input.split(" ")[0];
        if (Objects.equals(command, "add")){
            return commands.get("add").execute(collectionManager,null,null,ticket,null);
        }
        if (Objects.equals(command, "update_by_id")){
            return commands.get("update_by_id").execute(collectionManager,null,null,ticket,null);
        }
        if (Objects.equals(command, "remove_by_id")){
            return commands.get("remove_by_id").execute(collectionManager,id,null,null,null);
        }
        if (Objects.equals(command, "filter_by_person")){
            return commands.get("filter_by_person").execute(collectionManager,null,null,ticket,person);
        }
        if (Objects.equals(command, "count_less_than_type")){
            return commands.get("count_less_than_type").execute(collectionManager,id,null,null,null);
        }
        if (commands.containsKey(command)){
            return commands.get(command).execute(collectionManager,null,null,null,null);
        }
        return "Неизвестная команда";
    }
    public String getcommandlist(){
        StringBuilder result = new StringBuilder();
        for (BaseCommand command: commands.values()) {
            result.append(command.getDescription()).append("\n");
        }
        return "\u001B[32m" + "Список доступных команд:" + "\u001B[0m" + "\n" + result;
    }
}
