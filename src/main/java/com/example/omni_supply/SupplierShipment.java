package com.example.omni_supply;

import jakarta.persistence.*;

@Entity
public class SupplierShipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String supplierName;
    private String category;
    private String itemName;
    private String size;
    private double price;
    private int quantity;
    private String status; // e.g., "Arrived", "Delayed"

    public SupplierShipment() {
    }


    public SupplierShipment(String supplierName, String category, String itemName, String size, double price, int quantity, String status) {
        this.supplierName = supplierName;
        this.category = category;
        this.itemName = itemName;
        this.size = size;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    // --- GETTERS AND SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}