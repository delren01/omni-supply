package com.example.omni_supply;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    // One Order links to One Item
    @ManyToOne
    @JoinColumn(name = "item_id") // Creates a column "item_id" in the database
    private InventoryItem item;

    private int quantity;
    private String status;     // ex: "Pending"
    private String packaging;  // ex: "Box"
    private String carrier;    // ex: "FedEx"

    public Order() {
    }

    public Order(String customerName, InventoryItem item, int quantity) {
        this.customerName = customerName;
        this.item = item;
        this.quantity = quantity;
        this.status = "Pending"; // Default status
        this.packaging = "Standard";
        this.carrier = "TBD";
    }

    //  GETTERS AND SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public InventoryItem getItem() { return item; }
    public void setItem(InventoryItem item) { this.item = item; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPackaging() { return packaging; }
    public void setPackaging(String packaging) { this.packaging = packaging; }

    public String getCarrier() { return carrier; }
    public void setCarrier(String carrier) { this.carrier = carrier; }
}