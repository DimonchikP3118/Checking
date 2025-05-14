package org.example.ForFields;

import java.io.Serializable;
import java.util.Objects;
/**
 * Представляет Enum.
 * В этом классе перечисляются различные цвета.
 */
public enum Color implements Serializable {
    GREEN("Green",1),
    BLACK("Black",2),
    BLUE9("Blue",3),
    ORANGE("Orange",4),
    WHITE("White",5);
    private String eng;
    private int number;

    /**
     * Конструктор для создания цвета
     *
     * @param eng поле для доступа к цвету
     */
    Color(String eng,int number) {
        this.eng = eng;
        this.number = number;
    }

    /**
     * Выводит список цветов глаз
     */
    public static void printAllColors() {
        System.out.println("\u001B[32m" + "Список цветов глаз для человека:" + "\u001B[0m");
        for (Color color : Color.values()) {
            System.out.println("\u001B[33m" + color + " - " + color.number + "\u001B[0m");
        }
    }

    /**
     * Возвращает название цвета на английском языке
     *
     * @return цвет на английском
     */
    @Override
    public String toString() {
        return eng;
    }

    /**
     * Геттеры и сеттеры
     */

    public String getEng() {
        return eng;
    }
    public void setEng(String eng) {
        this.eng = eng;
    }
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     *
     * @param number поле для доступа к цвету
     * @return нужный цвет по его описанию
     */
    public static Color getColorByNumber(int number){
        for (Color color: Color.values()){
            if (Objects.equals(color.getNumber(), number)){
                return color;
            }
        }
        throw new IllegalArgumentException("Нет такого цвета");
    }
    public static Color getColorByEng(String eng){
        for (Color color: Color.values()){
            if (Objects.equals(color.getEng(),eng)){
                return color;
            }
        }
        throw new IllegalArgumentException("Нет такого цвета");
    }
}
