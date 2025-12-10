package com.example.omni_supply;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@Controller
public class InventoryController {

    private final InventoryRepository inventoryRepo;
    private final SupplierShipmentRepository shipmentRepo; // <--- NEW INJECTION

    // Constructor Injection for BOTH Repositories
    public InventoryController(InventoryRepository inventoryRepo, SupplierShipmentRepository shipmentRepo) {
        this.inventoryRepo = inventoryRepo;
        this.shipmentRepo = shipmentRepo;
    }

    // --- NAVIGATION ROOTS ---

    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }

    // --- MANAGER DASHBOARD ---

    @GetMapping("/manager")
    public String viewManagerPage(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        List<InventoryItem> items;
        if (keyword != null && !keyword.isEmpty()) {
            items = inventoryRepo.findByNameContainingIgnoreCase(keyword);
        } else {
            items = inventoryRepo.findAll();
        }

        int totalItems = items.size();
        int lowStockCount = 0;
        double totalValue = 0.0;

        for (InventoryItem item : items) {
            if (item.getQuantity() < 10) {
                lowStockCount++;
            }
            totalValue += (item.getPrice() * item.getQuantity());
        }

        model.addAttribute("items", items);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("lowStockCount", lowStockCount);
        model.addAttribute("totalValue", totalValue);
        model.addAttribute("keyword", keyword);

        return "dashboard";
    }

    // --- SHIPMENTS (NEW LOGIC) ---

    @GetMapping("/shipments")
    public String viewShipments(Model model) {
        // Load all inventory items (for the "Select Item" dropdown)
        model.addAttribute("items", inventoryRepo.findAll());
        // Load history of shipments
        model.addAttribute("shipments", shipmentRepo.findAll());
        return "shipments";
    }

    @PostMapping("/addShipment")
    public String addShipment(@RequestParam Long itemId,
                              @RequestParam int quantity,
                              @RequestParam String supplierName,
                              @RequestParam String status) {

        // 1. Find the existing Inventory Item
        InventoryItem item = inventoryRepo.findById(itemId).orElseThrow();

        // 2. Create the Shipment Log using your specific Entity structure
        SupplierShipment shipment = new SupplierShipment();
        shipment.setSupplierName(supplierName);
        shipment.setItemName(item.getName());   // Copy details from the item
        shipment.setCategory(item.getCategory());
        shipment.setSize(item.getSize());
        shipment.setPrice(item.getPrice());
        shipment.setQuantity(quantity);
        shipment.setStatus(status);

        shipmentRepo.save(shipment);

        // 3. IF status is "Arrived", update the actual inventory
        if ("Arrived".equalsIgnoreCase(status)) {
            item.setQuantity(item.getQuantity() + quantity);
            inventoryRepo.save(item);
        }

        return "redirect:/shipments";
    }

    // --- CUSTOMER & CART ---

    @GetMapping("/customer")
    public String viewCustomerPage(Model model, HttpSession session) {
        model.addAttribute("items", inventoryRepo.findAll());
        List<InventoryItem> cart = (List<InventoryItem>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();
        model.addAttribute("cartItems", cart);
        return "customer_view";
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        List<InventoryItem> cart = (List<InventoryItem>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        double total = 0.0;
        for (InventoryItem item : cart) total += item.getPrice();

        model.addAttribute("cartItems", cart);
        model.addAttribute("total", total);
        return "cart";
    }

    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable("id") Long id, HttpSession session) {
        List<InventoryItem> cart = (List<InventoryItem>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        inventoryRepo.findById(id).ifPresent(cart::add);
        session.setAttribute("cart", cart);
        return "redirect:/customer";
    }

    @GetMapping("/clearCart")
    public String clearCart(HttpSession session) {
        session.removeAttribute("cart");
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(HttpSession session) {
        List<InventoryItem> cart = (List<InventoryItem>) session.getAttribute("cart");
        if (cart != null && !cart.isEmpty()) {
            for (InventoryItem cartItem : cart) {
                InventoryItem dbItem = inventoryRepo.findById(cartItem.getId()).orElse(null);
                if (dbItem != null) {
                    int newQuantity = Math.max(0, dbItem.getQuantity() - 1);
                    dbItem.setQuantity(newQuantity);
                    inventoryRepo.save(dbItem);
                }
            }
            session.removeAttribute("cart");
        }
        return "redirect:/customer?success=purchased";
    }

    // --- MANAGER ACTIONS ---

    @PostMapping("/save")
    public String saveItem(InventoryItem item) {
        inventoryRepo.save(item);
        return "redirect:/manager";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        InventoryItem item = inventoryRepo.findById(id).orElseThrow();
        model.addAttribute("item", item);
        return "edit_item";
    }

    @PostMapping("/update/{id}")
    public String updateItem(@PathVariable("id") Long id, InventoryItem item) {
        item.setId(id);
        inventoryRepo.save(item);
        return "redirect:/manager";
    }

    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable("id") Long id) {
        inventoryRepo.deleteById(id);
        return "redirect:/manager";
    }
}