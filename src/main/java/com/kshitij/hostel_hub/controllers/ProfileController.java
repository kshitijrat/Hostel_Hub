package com.kshitij.hostel_hub.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kshitij.hostel_hub.entities.Payment;
import com.kshitij.hostel_hub.entities.User;
import com.kshitij.hostel_hub.repo.UserRepo;
import com.kshitij.hostel_hub.services.PaymentService;

@Controller
public class ProfileController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/profile")
    public String showProfile(Model model) {
        // Retrieve the current user's authentication details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            // UserDetails user = (UserDetails) authentication.getPrincipal();
            User user = userRepo.findUserByUserEmail(authentication.getName());
            // Add user details to the model
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

            //
            // Fetch the user from session or database
            List<Payment> payments = paymentService.getPaymentsByUser(user); // Fetch payment history

            List<String> paymentDates = payments.stream()
                    .map(Payment::getPaymentDate)
                    .collect(Collectors.toList());
            List<Double> paymentAmounts = payments.stream()
                    .map(Payment::getAmount)
                    .collect(Collectors.toList());

            model.addAttribute("user", user);
            model.addAttribute("payments", payments);
            model.addAttribute("paymentDates", paymentDates);
            model.addAttribute("paymentAmounts", paymentAmounts);
            System.out.println("run profil getting.....................");
        }

        return "profile";
    }

    

}
