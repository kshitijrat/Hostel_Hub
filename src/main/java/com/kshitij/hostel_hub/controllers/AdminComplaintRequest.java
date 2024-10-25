package com.kshitij.hostel_hub.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kshitij.hostel_hub.entities.Changes;
import com.kshitij.hostel_hub.entities.Complaint;
import com.kshitij.hostel_hub.entities.User;
import com.kshitij.hostel_hub.repo.ChangesRepo;
import com.kshitij.hostel_hub.repo.ComplaintRepo;
import com.kshitij.hostel_hub.repo.UserRepo;



@Controller
public class AdminComplaintRequest {
    @Autowired
    private UserRepo userRepo;


    @Autowired
    private ComplaintRepo complaintRepo;


    @Autowired
    private ChangesRepo changesRepo;

    @PostMapping("/admin/complaintrequest")
    public String complaintRequest(@RequestParam("email")String email, @RequestParam("complaintId")String complaintId){
        try {
            System.out.println("Complaint Controller Complaint Request ...........................");
            User user = userRepo.findUserByUserEmail(email);
            Complaint complaint = complaintRepo.findComplaintById(Integer.parseInt(complaintId));
            complaint.setStatus("Accepted");
            complaintRepo.save(complaint);
            //update changes
            Changes changes = new Changes();
            changes.setDate(getCurrentDate());
            changes.setIsnew(true);
            changes.setNotify("Your Complaint is Accepted");
            changes.setType("USER");
            changes.setUserId(user.getId());
            changesRepo.save(changes);

        } catch (Exception e) {
            System.out.println("error is: "+e.getMessage());
        }
        return "redirect:/admin_complaint";
    }

    public String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        return formattedDate;
    }
    
}
