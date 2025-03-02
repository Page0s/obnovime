package com.obnovime.controller;

import com.obnovime.model.AppUser;
import com.obnovime.repository.AppUserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
public class AppUserController {

    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping
    public String showUserProfile(Model model, HttpSession session) {
        AppUser user = (AppUser) session.getAttribute("user");
        
        if (user == null) {
            return "redirect:/login";
        }
        
        // Get the fresh user data from the database
        AppUser freshUser = appUserRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Create a display user with the password for the form
        AppUser displayUser = new AppUser();
        displayUser.setId(freshUser.getId());
        displayUser.setFirstName(freshUser.getFirstName());
        displayUser.setLastName(freshUser.getLastName());
        displayUser.setEmail(freshUser.getEmail());
        displayUser.setPhoneNumber(freshUser.getPhoneNumber());
        displayUser.setDepartment(freshUser.getDepartment());
        displayUser.setUserType(freshUser.getUserType());
        displayUser.setPassword(freshUser.getPassword());
        
        model.addAttribute("user", displayUser);
        return "profile";
    }
    
    @PostMapping("/update")
    public String updateUserProfile(@ModelAttribute AppUser updatedUser, 
                                   @RequestParam(required = false) String password,
                                   HttpSession session) {
        AppUser currentUser = (AppUser) session.getAttribute("user");
        
        if (currentUser == null) {
            return "redirect:/login";
        }
        
        // Get the user from the database to ensure we have the latest data
        AppUser user = appUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Update user fields
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        user.setEmail(updatedUser.getEmail());
        user.setDepartment(updatedUser.getDepartment());
        
        // Only update password if it's not empty and has changed
        if (password != null && !password.isEmpty()) {
            user.setPassword(password);
        }
        
        // Save updated user
        appUserRepository.save(user);
        
        // Update session
        session.setAttribute("user", user);
        
        return "redirect:/profile?success=true";
    }
}
