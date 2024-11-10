package com.kshitij.hostel_hub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kshitij.hostel_hub.entities.User;
import com.kshitij.hostel_hub.repo.UserRepo;

@Controller
public class UserController {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String registerStudent(
            @RequestParam("userEmail") String email,
            @RequestParam("userPassword") String password,
            @RequestParam(value = "role", required = false) Boolean isAdminRole,
            Model model, RedirectAttributes redirectAttributes) {

        if (userRepository.findUserByUserEmail(email) != null) {
            model.addAttribute("error", "User with this email already exists.");
            return "register"; // Ensure this matches your template name
        }

        User user = new User();
        String role;
        if (isAdminRole != null && isAdminRole) {
            role = "ROLE_ADMIN";
        } else {
            role = "ROLE_USER";
        }
        user.setRoles(role);
        user.setUserEmail(email);
        user.setUserPassword(passwordEncoder.encode(password));

        userRepository.save(user);
        redirectAttributes.addFlashAttribute("email", email);
        // model.addAttribute("user", user); // Add user object to model for application
        // form
        System.out.println("Run usercontroller****************************");
        if ("ROLE_USER".equals(role)) {

            return "redirect:/applicationform";
        }
        redirectAttributes.addFlashAttribute("user", user);

        return "redirect:/dashboard_admin";
    }

    @GetMapping("/editstudent/{id}")
    public String editStudent(@PathVariable int id, Model model) {
        User student = userRepository.findById(id);
        model.addAttribute("student", student);
        return "edit_student"; // A separate form to edit student
    }

    @GetMapping("/deletestudent/{id}")
    public String deleteStudent(@PathVariable int id) {
        userRepository.deleteById(id);
        return "redirect:/manage_student";
    }

}
