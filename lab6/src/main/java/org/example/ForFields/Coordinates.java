package org.example.ForFields;

import java.io.Serializable;

/**
 * Представляет простой класс.
 * Этот класс позволяет работать с координатами.
 */
public class Coordinates implements Serializable {
    private long x;
    private Integer y; //Значение поля должно быть больше -376, Поле не может быть null

    /**
     * Конструктор для создания координат
     *
     * @param x координата по х
     * @param y координата по у
     */
    public Coordinates(long x, Integer y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Геттеры и сеттеры
     */

    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    /**
     * Метод возвращающий строковое изображение координат
     *
     * @return строку с координатами
     */
    @Override
    public String toString(){
        return "(" + getX() + ", " + getY() + ")";
    }
}
