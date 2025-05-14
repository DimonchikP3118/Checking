package org.example.Model;

import org.example.ForFields.Coordinates;
import org.example.ForFields.Person;
import org.example.ForFields.TicketType;

import java.io.Serializable;
import java.time.LocalDate;
/**
 * Представляет собой простой класс.
 * Этот класс позволяет работать с билетами.
 */
public class Ticket implements Comparable<Ticket>, Serializable {
    private static final long serialVersion = 1L;
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer price; //Поле может быть null, Значение поля должно быть больше 0
    private int discount; //Значение поля должно быть больше 0, Максимальное значение поля: 100
    private TicketType type; //Поле не может быть null
    private Person person; //Поле может быть null

    /**
     * Конструктор для создания тикета
     *
     * @param id номер идентификационный
     * @param name имя
     * @param coordinates координаты
     * @param creationDate дата создания
     * @param price цена
     * @param discount скидка
     * @param type тип
     * @param person человек
     */
    public Ticket(Integer id, String name, Coordinates coordinates, LocalDate creationDate, Integer price, int discount, TicketType type, Person person) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.discount = discount;
        this.type = type;
        this.person = person;
    }

    /**
     * Пустой конструктор
     */
    public Ticket() {
    }

    /**
     * Конструктор с именем и ценой
     * @param name имя
     * @param price цена
     */
    public Ticket(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    /**
     * Геттеры и сеттеры
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * Строковое изображение полей в читаемом виде
     * @return строку с полями класса Ticket
     */
    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates.toString() +
                ", creationDate=" + creationDate +
                ", price=" + price +
                ", discount=" + discount +
                ", type=" + type +
                ", person=" + person +
                '}';
    }

    @Override
    public int compareTo(Ticket o) {
        // если this > o - вернем больше 0
        // если this < o - вернем меньше 0
        // если this == 0 - вернем 0
        return this.price - o.price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
