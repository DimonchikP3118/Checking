package org.example.ForFields;

import java.io.Serializable;
import java.util.Objects;
/**
 * Представляет Enum.
 * В этом классе перечисляются различные типы билетов.
 */
public enum TicketType implements Serializable {
    VIP("VIP",1),
    USUAL("Usual",2),
    BUDGETARY("Budgetary",3),
    CHEAP("Cheap",4);
    private String eng;
    private int Chisl;

    /**
     * Конструктор для создания типа билета
     *
     * @param eng переменная описания типа
     * @param Chisl числовой эквивалент типа билета или его значимость
     */
    TicketType(String eng, int Chisl) {
        this.eng = eng;
        this.Chisl = Chisl;
    }

    /**
     * Вывод всех типов билетов в консоль
     */
    public static void printAllTicketTypes() {
        System.out.println("\u001B[32m" + "Список типов билетов:" + "\u001B[0m");
        for (TicketType type : TicketType.values()) {
            System.out.println("\u001B[33m" + type + " - " + type.getChisl() + "\u001B[0m");
        }
    }
    /**
     * Возвращает название типа билета на английском языке
     *
     * @return тип билета на английском
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
    public int getChisl() {
        return Chisl;
    }
    public void setChisl(int chisl) {
        Chisl = chisl;
    }

    /**
     * Получение типа билета по его описанию
     *
     * @param chisl переменная описания типа
     * @return тип билета
     * @throws IllegalArgumentException нет такого типа
     */
    public static TicketType getTypeByNumber(int chisl){
        for (TicketType type: TicketType.values()){
            if (Objects.equals(type.getChisl(), chisl)){
                return type;
            }
        }
        throw new IllegalArgumentException("Нет такого типа");
    }
    public static TicketType getTypeByEng(String eng){
        for (TicketType type: TicketType.values()){
            if (Objects.equals(type.getEng(), eng)){
                return type;
            }
        }
        throw new IllegalArgumentException("Нет такого типа");
    }
}
