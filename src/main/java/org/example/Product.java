package org.example;

import javafx.beans.property.*;

public class Product {
    private final StringProperty name;
    private final IntegerProperty count;
    private final DoubleProperty price;

    public Product(String name, int count, double price) {
        this.name = new SimpleStringProperty(name);
        this.count = new SimpleIntegerProperty(count);
        this.price = new SimpleDoubleProperty(price);
    }

    public String getName() { return name.get(); }
    public StringProperty nameProperty() { return name; }

    public int getCount() { return count.get(); }
    public void setCount(int count) { this.count.set(count); }
    public IntegerProperty countProperty() { return count; }

    public double getPrice() { return price.get(); }
    public void setPrice(double price) { this.price.set(price); }
    public DoubleProperty priceProperty() { return price; }
}