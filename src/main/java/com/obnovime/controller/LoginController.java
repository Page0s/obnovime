package com.obnovime.controller;

import com.obnovime.model.AppUser;
import com.obnovime.repository.AppUserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }


    @PostMapping("/login")
    public String processLogin(@RequestParam String email,
                               @RequestParam String password,
                               Model model,
                               HttpSession session) {
        // Validacija ulaznih podataka
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            model.addAttribute("error", "Email i lozinka ne smiju biti prazni!");
            return "login";
        }

        Optional<AppUser> user = appUserRepository.findByEmail(email);

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            session.setAttribute("user", user.get());
            System.out.println("Korisnik u sesiji: " + session.getAttribute("user"));

            return "redirect:/main";
        }

        model.addAttribute("error", "Neispravni podaci za prijavu!");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}