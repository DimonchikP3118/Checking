package org.example.CSV;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
/**
 * Представляет простой класс.
 * Этот класс позволяет работать с файлами в формате csv.
 */
public class CSVReader {
    /**
     * Читает файл CSV и возвращает список команд
     *
     * @param path путь к файлу
     * @return список команд из файла
     */
    public static ArrayList<String> readCsv(String path) {
        String line = "";
        String splitter = ";";
        ArrayList<String> commands = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(path);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitter);
                for (String command : data) {
                    commands.add(command.trim());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return commands;
    }
}