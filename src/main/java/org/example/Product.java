package org.example;

/**
 * Класс-модель товара.
 * Он говорит программе, что у каждого товара есть имя, количество и цена.
 */
public class Product {
    private String name;
    private int count;
    private double price;

    // Это "Конструктор" — он создает новый товар
    public Product(String name, int count, double price) {
        this.name = name;
        this.count = count;
        this.price = price;
    }

    // Эти методы (Геттеры) нужны Таблице, чтобы она могла прочитать данные
    public String getName() { return name; }
    public int getCount() { return count; }
    public double getPrice() { return price; }

    // Эти методы (Сеттеры) понадобятся позже для редактирования
    public void setName(String name) { this.name = name; }
    public void setCount(int count) { this.count = count; }
    public void setPrice(double price) { this.price = price; }
}