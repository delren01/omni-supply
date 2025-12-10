package com.example.omni_supply;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@Controller
public class InventoryController {

    private final InventoryRepository repo;

    public InventoryController(InventoryRepository repo) {
        this.repo = repo;
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
    public String viewManagerPage(Model model) {
        List<InventoryItem> items = repo.findAll();

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

        return "dashboard";
    }

    // --- CUSTOMER STOREFRONT ---

    @GetMapping("/customer")
    public String viewCustomerPage(Model model, HttpSession session) {
        // Fetch inventory items
        var items = repo.findAll();
        model.addAttribute("items", items);

        // Check the session for the cart
        List<InventoryItem> cart = (List<InventoryItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        model.addAttribute("cartItems", cart);

        return "customer_view";
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        List<InventoryItem> cart = (List<InventoryItem>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        double total = 0.0;
        for (InventoryItem item : cart) {
            total += item.getPrice();
        }

        model.addAttribute("cartItems", cart);
        model.addAttribute("total", total);

        return "cart";
    }

    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable("id") Long id, HttpSession session) {
        List<InventoryItem> cart = (List<InventoryItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        // Find item and add to session cart
        repo.findById(id).ifPresent(cart::add);
        session.setAttribute("cart", cart);

        return "redirect:/customer";
    }

    @GetMapping("/clearCart")
    public String clearCart(HttpSession session) {
        session.removeAttribute("cart");
        return "redirect:/cart";
    }

    // --- MANAGER ACTIONS ---

    @PostMapping("/save")
    public String saveItem(InventoryItem item) {
        repo.save(item);
        return "redirect:/manager";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        InventoryItem item = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + id));

        model.addAttribute("item", item);
        return "edit_item";
    }

    @PostMapping("/update/{id}")
    public String updateItem(@PathVariable("id") Long id, InventoryItem item) {
        item.setId(id);
        repo.save(item);
        return "redirect:/manager";
    }

    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable("id") Long id) {
        repo.deleteById(id);
        return "redirect:/manager";
    }
}