package com.example.omni_supply;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    private final InventoryRepository repo;

    public CartController(InventoryRepository repo) {
        this.repo = repo;
    }

    // ADD ITEM TO CART
    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable Long id, HttpSession session) {
        // Get the cart from the session, or create a new one if empty
        List<InventoryItem> cart = (List<InventoryItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        // Find item and add to list
        repo.findById(id).ifPresent(cart::add);

        // Save back to session
        session.setAttribute("cart", cart);

        return "redirect:/customer"; // Stay on shopping page
    }

    // VIEW CART PAGE
    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        List<InventoryItem> cart = (List<InventoryItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        // Calculate Total Price
        double total = cart.stream().mapToDouble(InventoryItem::getPrice).sum();

        model.addAttribute("cartItems", cart);
        model.addAttribute("total", total);

        return "cart_view";
    }

    // EMPTY CART
    @GetMapping("/clearCart")
    public String clearCart(HttpSession session) {
        session.removeAttribute("cart");
        return "redirect:/cart";
    }
}