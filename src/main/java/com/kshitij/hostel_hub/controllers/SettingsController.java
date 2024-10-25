package com.kshitij.hostel_hub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kshitij.hostel_hub.entities.User;
import com.kshitij.hostel_hub.repo.UserRepo;



@Controller
public class SettingsController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/updateSettings")
    public String updateSettings(@RequestParam("userName") String name,
                                 @RequestParam("userEmail") String email,
                                 @RequestParam("userPhone") String phone,
                                 @RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmNewPassword") String confirmNewPassword,
                                 Authentication authentication,
                                 Model model) {
        // Fetch the user based on authentication
        String authEmail = authentication.getName();
        User user = userRepo.findUserByUserEmail(authEmail);

        // Update user details
        user.setUserName(name);
        user.setUserEmail(email);
        user.setUserPhone(phone);

        // Update password if provided and valid
        if (!currentPassword.isEmpty() && !newPassword.isEmpty() && newPassword.equals(confirmNewPassword)) {
            if(passwordEncoder.matches(currentPassword, user.getUserPassword())){
                user.setUserPassword(passwordEncoder.encode(confirmNewPassword));
            }
        }

        // Save the updated user details
        userRepo.save(user);

        // Add attributes for confirmation or further actions
        model.addAttribute("user", user);

        return "redirect:/settings"; // Ensure this matches your settings view path
    }
}

