package com.example.omni_supply;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class LoginController {

    private final UserRepository userRepo;

    public LoginController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // 1. Show the Registration Page
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    // 2. Process the Registration
    @PostMapping("/perform_register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String secretCode) { // <--- Catch the code

        // Check if username already exists (Optional safety check)
        if (userRepo.findByUsername(username) != null) {
            return "redirect:/register?error=exists";
        }

        // DETERMINE ROLE
        String role = "CUSTOMER"; // Default role

        // If they know the secret code, then they are given manager role
        if ("admin2025".equals(secretCode)) {
            role = "MANAGER";
        }

        // Save to Database
        User newUser = new User(username, password, role);
        userRepo.save(newUser);

        // Send them to login page to sign in with their new account
        return "redirect:/login?success";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/perform_login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               Model model) {

        // Cleans the inputs
        String cleanUser = username.trim();
        String cleanPass = password.trim();

        // Finds user
        User user = userRepo.findByUsername(cleanUser);

        System.out.println("Login Attempt: '" + cleanUser + "'");

        if (user == null) {
            System.out.println("User not found in DB.");
            return "redirect:/login?error";
        }

        // Check Password
        if (!user.getPassword().equals(cleanPass)) {
            System.out.println("Password wrong. DB has: " + user.getPassword());
            return "redirect:/login?error";
        }

        // Success
        if ("MANAGER".equalsIgnoreCase(user.getRole())) {
            return "redirect:/manager";
        } else {
            return "redirect:/customer";
        }
    }
}