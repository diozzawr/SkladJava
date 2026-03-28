package org.example;

public class Product {
    private String name;
    private int count;
    private double price;

    // ОСТАВЛЯЕМ ТОЛЬКО ОДИН ЭТОТ КОНСТРУКТОР:
    public Product(String name, int count, double price) {
        this.name = name;
        this.count = Math.max(0, count);
        this.price = Math.max(0.0, price);
    }

    // Тут должны быть геттеры (getName, getCount и т.д.)
    public String getName() { return name; }
    public int getCount() { return count; }
    public double getPrice() { return price; }
}