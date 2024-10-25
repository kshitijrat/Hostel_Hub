package com.kshitij.hostel_hub.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminUpdateController {

    @PostMapping("/admin/editstudent/{id}")
    public String editStudent(@PathVariable int id, RedirectAttributes redirectAttribute) {
        // Handle the editing logic
        System.out.println("Edit Student in AdminUpdateController*********************");
        return "";
    }

    
    @PostMapping("/admin/deletestudent/{id}")
    public String deleteStudent(@PathVariable int id, RedirectAttributes redirectAttribute) {
        // Handle the deletion logic
        System.out.println("Delete Student in AdminUpdateController*********************");
        return "manage_student";
    }

}
