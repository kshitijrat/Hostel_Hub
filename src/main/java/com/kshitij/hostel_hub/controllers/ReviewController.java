package com.kshitij.hostel_hub.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kshitij.hostel_hub.entities.Review;
import com.kshitij.hostel_hub.entities.User;
import com.kshitij.hostel_hub.repo.ReviewRepo;
import com.kshitij.hostel_hub.repo.UserRepo;

@Controller
public class ReviewController {

    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/review_form")
    public String reviewForm(@RequestParam("rating") String rating, @RequestParam("message") String message,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        System.out.println("Enter in review controller...............");
        User user = userRepo.findUserByUserEmail(authentication.getName());
        System.out.println("User not null: " + user.getUserEmail());
        Review review = new Review();
        ;
        // if user not give review
        if(reviewRepo.findReviewByUser(user) == null)review.setUser(user);
        review.setMessage(message);
        review.setRating(Integer.parseInt(rating));
        review.setDate(getCurrentDate());
        reviewRepo.save(review);
        
        redirectAttributes.addFlashAttribute("flashMessage", "Thanks for your review!");
        return "redirect:/review";
    }

    public String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        return formattedDate;
    }

}
