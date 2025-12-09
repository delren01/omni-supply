package com.example.omni_supply;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    // MANAGER DASHBOARD: Full access (Edit/Delete/Add)
    @GetMapping("/manager")
    public String viewManagerPage(Model model) {
        // Fetch all items from the database
        var items = repo.findAll();
        // Send items to the HTML file
        model.addAttribute("items", items);
        // Load "dashboard.html"
        return "dashboard";
    }

    // CUSTOMER VIEW: Read-Only access (Shopping Catalog)
    @GetMapping("/customer")
    public String viewCustomerPage(Model model) {
        var items = repo.findAll();
        model.addAttribute("items", items);
        // Load "customer_view.html"
        return "customer_view";
    }

    // Manager Access:

    // HANDLE "ADD ITEM" FORM SUBMIT
    @PostMapping("/save")
    public String saveItem(InventoryItem item) {
        repo.save(item);
        return "redirect:/manager"; // Go back to Manager Dashboard
    }

    // SHOW EDIT FORM
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        // Find the item by ID, or throw error if missing
        InventoryItem item = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + id));

        model.addAttribute("item", item);
        return "edit_item"; // Loads the edit_item.html file
    }

    // SAVE UPDATES
    @PostMapping("/update/{id}")
    public String updateItem(@PathVariable("id") Long id, InventoryItem item) {
        // Force the ID to match so it updates the existing row
        item.setId(id);
        repo.save(item);
        return "redirect:/manager"; // Go back to Manager Dashboard
    }

    // DELETE ITEM
    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable("id") Long id) {
        repo.deleteById(id);
        return "redirect:/manager"; // Go back to Manager Dashboard
    }
}