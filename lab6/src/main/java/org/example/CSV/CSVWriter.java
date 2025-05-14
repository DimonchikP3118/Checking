package org.example.CSV;

import org.example.Model.Ticket;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
/**
 * Представляет простой класс.
 * Этот класс позволяет работать с файлами в формате csv.
 */
public class CSVWriter {
    /**
     * Записывает коллекцию в файл CSV
     *
     * @param tickets коллекция, где находятся тикеты
     */
    public static void writeCsv(Vector<Ticket> tickets) {
        String pathe = System.getenv("File");
        if (pathe == null){
            System.out.println("Переменная окружения не задана");
            return;
        }
        try (FileWriter fw = new FileWriter(pathe)) {
            for (Ticket ticket : tickets) {
                fw.write(String.valueOf(ticket.getId()));
                fw.write(";");
                fw.write(ticket.getName());
                fw.write(";");
                fw.write(ticket.getCoordinates().toString());
                fw.write(";");
                fw.write(String.valueOf(ticket.getCreationDate()));
                fw.write(";");
                fw.write(String.valueOf(ticket.getPrice()));
                fw.write(";");
                fw.write(String.valueOf(ticket.getDiscount()));
                fw.write(";");
                fw.write(ticket.getType().getEng());
                fw.write(";");
                fw.write(ticket.getPerson().toString());
                fw.write(";");
                fw.write("\n");
            }
            System.out.println("Коллекция сохранена в файл: " + pathe);
        } catch (IOException ignored) {
        }
    }
}
