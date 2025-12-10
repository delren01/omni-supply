package com.example.omni_supply; // Matches your folder structure

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class InventoryItem {
    // Every database table needs a unique ID.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increments (1, 2, 3...)
    private Long id;

    private String category;
    private String name;
    private String size;
    private double price;
    private int quantity;private String sku;
    private String location;


    public InventoryItem() {
    }

    public InventoryItem(String category, String name, String size, double price, int quantity) {
        this.category = category;
        this.name = name;
        this.size = size;
        this.price = price;
        this.quantity = quantity;
    }

    // --- GETTERS AND SETTERS ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSku() {
        return sku;
    }
    public void setSku(String sku) {
        this.sku = sku;
    }


    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }


    @Override
    public String toString() {
        return category + " - " + name + " (" + size + ")";
    }
}