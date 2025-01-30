package com.kshitij.hostel_hub.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kshitij.hostel_hub.entities.Changes;
import com.kshitij.hostel_hub.entities.Checkout;
import com.kshitij.hostel_hub.entities.MyRoom;
import com.kshitij.hostel_hub.entities.User;
import com.kshitij.hostel_hub.repo.ChangesRepo;
import com.kshitij.hostel_hub.repo.CheckoutRepo;
import com.kshitij.hostel_hub.repo.MyRoomRepo;
import com.kshitij.hostel_hub.repo.UserRepo;

@Controller
public class CheckOutController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MyRoomRepo myRoomRepo;

    @Autowired
    private CheckoutRepo checkoutRepo;

    @Autowired
    private ChangesRepo changesRepo;

    @PostMapping("/checkoutrequest")
    public String checkOutRequest(@RequestParam("userEmail") String email,
            @RequestParam("checkoutDate") String checkoutDate,
            @RequestParam("roomNumber") String roomNumber, @RequestParam("reason") String reason,
            RedirectAttributes redirectAttributes) {
        User user = userRepo.findUserByUserEmail(email);
        if (user == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email Not Match!");
            return "redirect:/checkout";
        }
        MyRoom myRoom = myRoomRepo.findRoomByRoomNumber(roomNumber);
        if (myRoom == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Room Number Incorrect!");
            return "redirect:/checkout";
        }

        Checkout checkout = new Checkout();
        checkout.setCheckoutRequestDate(checkoutDate);
        checkout.setReason(reason);
        checkout.setUser(user);
        checkout.setRoom(myRoom);
        checkout.setStatus("Pending");
        checkoutRepo.save(checkout);

        // update changes
        Changes changes = new Changes();
        changes.setDate(getCurrentDate());
        changes.setIsnew(true);
        changes.setNotify("Request for leaving hostel");
        changes.setType("Admin");
        changes.setUserId(user.getId());
        changesRepo.save(changes);

        redirectAttributes.addFlashAttribute("successMessage", "Your Request is sent to our staff!");
        return "redirect:/checkout";
    }

    @PostMapping("/approve_checkout")
    public String approveCheckout(@RequestParam("email") String email, @RequestParam("roomNo") String roomNo,
            RedirectAttributes redirectAttribute) {
        User user = userRepo.findUserByUserEmail(email);
        MyRoom myRoom = myRoomRepo.findMyRoomByUser(user);
        Checkout checkout = checkoutRepo.findByUser(user);
        int id = user.getId();

        try {
            // Check if the current date matches the checkout date
            if (checkout.getCheckoutRequestDate().equalsIgnoreCase(getCurrentDate())) {
                if (user.getMyRoom() != null) {
                    myRoom.setUser(null);
                    myRoomRepo.save(myRoom);
                    myRoomRepo.deleteById(myRoom.getId());
                    // UPDATE user SET my_room_id = NULL WHERE my_room_id = 602;
                    // DELETE FROM my_room WHERE id = 602;
                }
                userRepo.deleteById(id);
                redirectAttribute.addFlashAttribute("successMessage", "User deleted successfully!");
            } else {
                checkout.setStatus("Approved");
                checkoutRepo.save(checkout);
            }

        } catch (Exception e) {
            System.out.println("error message: " + e.getLocalizedMessage());
            redirectAttribute.addFlashAttribute("errorMessage", "Something went wrong!");
        }
        return "redirect:/admin_checkout";
    }

    public String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        return formattedDate;
    }

}
