package org.example.ForFields;

import java.io.Serializable;

/**
 * Представляет простой класс.
 * Этот класс позволяет работать с людьми.
 */
public class Person implements Serializable {
    private String passportID; //Значение этого поля должно быть уникальным, Строка не может быть пустой, Длина строки должна быть не меньше 8, Поле может быть null
    private Color eyeColor; //Поле не может быть null
    private Country nationality; //Поле может быть null

    /**
     * Пустой конструктор
     */
    public Person() {
    }

    /**
     * Конструктор для создания человека
     *
     * @param passportID пасспорт
     * @param eyeColor цвет глаз
     * @param nationality национальность
     */
    public Person(String passportID, Color eyeColor, Country nationality) {
        this.passportID = passportID;
        this.eyeColor = eyeColor;
        this.nationality = nationality;
    }
    /**
     * Геттеры и сеттеры
     */
    public String getPassportID() {
        return passportID;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }
    /**
     * Метод возвращающий строковое изображение полей у класса Person
     *
     * @return строку с полями
     */
    @Override
    public String toString(){
        return "ПаспортID=" + passportID + ", " + "цвет глаз=" + eyeColor + ", национальность=" + nationality;
    }
}
