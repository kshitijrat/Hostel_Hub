package com.kshitij.hostel_hub.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kshitij.hostel_hub.entities.Complaint;
import com.kshitij.hostel_hub.entities.MyRoom;
import com.kshitij.hostel_hub.entities.User;
import com.kshitij.hostel_hub.repo.ComplaintRepo;
import com.kshitij.hostel_hub.repo.MyRoomRepo;
import com.kshitij.hostel_hub.repo.UserRepo;
import com.kshitij.hostel_hub.services.ChangesServices;
import com.kshitij.hostel_hub.services.ComplaintService;



@Controller
public class DashboardController {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private  MyRoomRepo myRoomRepo;

    @Autowired
    private ChangesServices changesServices;

    @Autowired
    private ComplaintRepo complaintRepo;

    @Autowired
    private ComplaintService complaintService;


    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        System.out.println("Enter in dashbaord controller...............");
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userRepository.findUserByUserEmail(authentication.getName());
            // Get the current date
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = currentDate.format(formatter);

            // Set dummy activity data for demonstration
            user.setActivityDate1(currentDate.minusDays(5).format(formatter));
            user.setActivityDate2(currentDate.minusDays(2).format(formatter));
            user.setActivityDate3(formattedDate);

            LocalDate upcomingBookingDate = currentDate.plusDays(10);
            user.setUpcomingBookingId("BKG12345"); // Example booking ID
            user.setUpcomingBookingDate(upcomingBookingDate.format(formatter));

            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                model.addAttribute("user", user);
                // List<User> totalUsersList = userRepository.findUserByRoles("ROLE_USER");
                List<MyRoom> totalStudents = myRoomRepo.findByStatus("Approved");
                model.addAttribute("totalStudents", totalStudents.size());
                List<MyRoom> pendingrequestList = myRoomRepo.findByStatus("Pending");
                model.addAttribute("pendingRequests", pendingrequestList.size());
                
                System.out.println("Enter in dashbord controller..............");
                if (changesServices.isNewUpdateForAdmin()) {
                    model.addAttribute("newUpdate", "New Update!");
                }

                if(complaintService.isNewComplaint()){
                    List<Complaint> complaints = complaintRepo.findAll();
                    model.addAttribute("newComplaint", complaints);
                }
                
                return "dashboard_admin"; // Admin dashboard
            } else {
                //update change notifi show
                if(changesServices.isNewUpdateForUser(user.getId())){
                    model.addAttribute("newUpdate", "New Update!");
                }

                model.addAttribute("user", user);
                model.addAttribute("bookingId", myRoomRepo.findMyRoomByUser(user).getBookingId());
                return "dashboard"; // Normal user dashboard
            }
        }
        System.out.println("Error::::::::::::::");
        return "redirect:/index";
    }

}
