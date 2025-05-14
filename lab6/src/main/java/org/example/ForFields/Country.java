package org.example.ForFields;

import java.io.Serializable;
import java.util.Objects;
/**
 * Представляет Enum.
 * В этом классе перечисляются различные страны.
 */
public enum Country implements Serializable {
    RUSSIA("Russia",1),
    UNITED_KINGDOM("Britain",2),
    SOUTH_KOREA("Korea",3);
    private String eng;
    private int number;

    /**
     * Конструктор для создания страны
     *
     * @param eng переменная доступа к элементу енама
     */
    Country(String eng,int number) {
        this.eng = eng;
        this.number = number;
    }

    /**
     * Выводит список доступных стран
     */
    public static void printAllCountries() {
        System.out.println("\u001B[32m" + "Список стран для человека:" + "\u001B[0m");
        for (Country country : Country.values()) {
            System.out.println("\u001B[33m" + country + " - " + country.number + "\u001B[0m");
        }
    }
    /**
     * Возвращает название страны на английском языке
     *
     * @return страну на английском
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
     * Найти подходящую страну по ее описанию
     *
     * @param number переменная доступа к элементу енама
     * @return страна
     * @throws IllegalArgumentException нет такой страны
     */
    public static Country getCountryByNumber(int number){
        for (Country country: Country.values()){
            if (Objects.equals(country.getNumber(), number)){
                return country;
            }
        }
        throw new IllegalArgumentException("Нет такой страны");
    }
    public static Country getCountryByEng(String eng){
        for (Country country: Country.values()){
            if (Objects.equals(country.getEng(),eng)){
                return country;
            }
        }
        throw new IllegalArgumentException("Нет такой страны");
    }
}