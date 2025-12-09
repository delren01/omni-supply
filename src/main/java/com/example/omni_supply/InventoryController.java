package com.example.omni_supply;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Import List to iterate over items
import java.util.List;

@Controller
public class InventoryController {

    private final InventoryRepository repo;

    // Connects to the Database automatically
    public InventoryController(InventoryRepository repo) {
        this.repo = repo;
    }

    // Redirects everyone to the Login Page first
    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    // MANAGER DASHBOARD: Full access with Stats
    @GetMapping("/manager")
    public String viewManagerPage(Model model) {
        // Fetch all items from the database
        List<InventoryItem> items = repo.findAll();

        // Initialize variables for calculations
        int totalItems = items.size();
        int lowStockCount = 0;
        double totalValue = 0.0;

        // Loop through items to calculate stats
        for (InventoryItem item : items) {
            // Count low stock (less than 10)
            if (item.getQuantity() < 10) {
                lowStockCount++;
            }
            // Calculate value (Price * Quantity)
            totalValue += (item.getPrice() * item.getQuantity());
        }

        // Send calculated stats + list to the HTML file
        model.addAttribute("items", items);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("lowStockCount", lowStockCount);
        model.addAttribute("totalValue", totalValue);

        // Loads dashboard.html
        return "dashboard";
    }

    // CUSTOMER VIEW: Read-Only access (Shopping Catalog)
    @GetMapping("/customer")
    public String viewCustomerPage(Model model) {
        var items = repo.findAll();
        model.addAttribute("items", items);
        return "customer_view";
    }

    // --- Manager Actions ---

    // HANDLE "ADD ITEM" FORM SUBMIT
    @PostMapping("/save")
    public String saveItem(InventoryItem item) {
        repo.save(item);
        return "redirect:/manager"; // Go back to Manager Dashboard
    }

    // SHOW EDIT FORM
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        InventoryItem item = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + id));

        model.addAttribute("item", item);
        return "edit_item";
    }

    // SAVE UPDATES
    @PostMapping("/update/{id}")
    public String updateItem(@PathVariable("id") Long id, InventoryItem item) {
        item.setId(id);
        repo.save(item);
        return "redirect:/manager";
    }

    // DELETE ITEM
    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable("id") Long id) {
        repo.deleteById(id);
        return "redirect:/manager";
    }
}