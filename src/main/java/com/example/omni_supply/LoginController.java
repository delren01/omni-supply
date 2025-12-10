package com.example.omni_supply;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

// IMPORT SESSION
import jakarta.servlet.http.HttpSession; // Use javax.servlet.http.HttpSession if on older Spring

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
                               @RequestParam String secretCode) {

        if (userRepo.findByUsername(username) != null) {
            return "redirect:/register?error=exists";
        }

        String role = "CUSTOMER";

        if ("admin2025".equals(secretCode)) {
            role = "MANAGER";
        }

        User newUser = new User(username, password, role);
        userRepo.save(newUser);

        return "redirect:/login?success";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/perform_login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session, // <--- 1. Inject Session Here
                               Model model) {

        String cleanUser = username.trim();
        String cleanPass = password.trim();

        User user = userRepo.findByUsername(cleanUser);

        if (user == null) {
            return "redirect:/login?error";
        }

        if (!user.getPassword().equals(cleanPass)) {
            return "redirect:/login?error";
        }

        // --- 2. NEW LOGIC: SAVE TO SESSION ---
        // This is what allows other pages (like the store) to know who you are
        session.setAttribute("user", user.getUsername());
        session.setAttribute("role", user.getRole());
        // -------------------------------------

        if ("MANAGER".equalsIgnoreCase(user.getRole())) {
            return "redirect:/manager";
        } else {
            return "redirect:/customer";
        }
    }
}