package org.example.Commands;

import org.example.CSV.CSVWriter;
import org.example.Model.CollectionManager;

public class SaveCommand {
    public static void SaveCollection(CollectionManager collectionManager){
        if (collectionManager.getTickets().isEmpty()){
            System.out.println("Коллекция пуста и нечего сохранять");
            return;
        }
        CSVWriter.writeCsv(collectionManager.getTickets());
    }
}
