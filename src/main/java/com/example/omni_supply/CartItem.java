package com.example.omni_supply;

public class CartItem {
    private InventoryItem item;
    private int quantity;

    public CartItem(InventoryItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    // Getters and Setters
    public InventoryItem getItem() { return item; }
    public void setItem(InventoryItem item) { this.item = item; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getTotalPrice() {
        return item.getPrice() * quantity;
    }
}