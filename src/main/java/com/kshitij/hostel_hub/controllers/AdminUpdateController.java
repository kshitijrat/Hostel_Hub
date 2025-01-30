package com.kshitij.hostel_hub.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kshitij.hostel_hub.entities.Changes;
import com.kshitij.hostel_hub.entities.MyRoom;
import com.kshitij.hostel_hub.entities.User;
import com.kshitij.hostel_hub.repo.ChangesRepo;
import com.kshitij.hostel_hub.repo.MyRoomRepo;
import com.kshitij.hostel_hub.repo.UserRepo;
import com.kshitij.hostel_hub.services.MyRoomService;

@Controller
public class AdminUpdateController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MyRoomRepo myRoomRepo;

    @Autowired
    private MyRoomService myRoomService;

    @Autowired
    private ChangesRepo changesRepo;

    // update admin name
    @PostMapping("/submit_username")
    public String submitUsername(@RequestParam("username") String username, @RequestParam("phone") String phone,
            Authentication authentication, Model model) {
        System.out.println("Admin update userName.......");
        User user = userRepo.findUserByUserEmail(authentication.getName());
        if (user != null) {
            user.setUserName(username);
            user.setUserPhone(phone);
            userRepo.save(user);
        }
        model.addAttribute("user", user);
        return "redirect:/dashboard";
    }

    @PostMapping("/admin/addstudent")
    public String editStudent(RedirectAttributes redirectAttribute, @RequestParam("userName") String userName,
            @RequestParam("userEmail") String userEmail, @RequestParam("userPhone") String userPhone,
            @RequestParam("gender") String gender, @RequestParam("hostelNumber") String hostelNumber) {
        // Handle the add logic
        System.out.println("Add Student in AdminUpdateController*********************");
        if (userRepo.findUserByUserEmail(userEmail) == null) {
            redirectAttribute.addFlashAttribute("error", "User Must be registered!");
            return "redirect:/manage_student";
        }
        User user = userRepo.findUserByUserEmail(userEmail);
        user.setUserName(userName);
        user.setGender(gender);
        user.setUserPhone(userPhone);
        user.setRoomType("Single");
        user.setHostelNumber(Integer.parseInt(hostelNumber));
        user.setRoles("ROLE_USER");
        //set roomnumber
        int roomNumber = myRoomService.lastRoomThroughHostel(Integer.parseInt(hostelNumber));
        user.setRoomNumber(roomNumber+"");
        userRepo.save(user);
        //set booking id
        Random random = new Random();
        int x = random.nextInt(10, 100);
        int y = random.nextInt(101, 200);
        String bookingId = "BKNo" + x + "Id" + y;
        MyRoom myRoom = new MyRoom();
        myRoom.setBookingId(bookingId);
        myRoom.setRoomNumber(roomNumber+"");
        myRoom.setDate(getCurrentDate());
        myRoom.setRoomType("Single");
        myRoom.setStatus("Pending");
        
        myRoom.setUser(user);
        myRoomRepo.save(myRoom);
        
        user.setMyRoom(myRoom);
        userRepo.save(user);

        //update changes----
        Changes changes = new Changes();
        changes.setDate(getCurrentDate());
        changes.setNotify("New Student Register! "+bookingId + " "+myRoom.getStatus());
        changes.setUserId(user.getId());
        changes.setType("Admin");
        changesRepo.save(changes);

        return "redirect:/manage_student";
    }

    @PostMapping("/admin/deletestudent/{id}")
    public String deleteStudent(@PathVariable int id, RedirectAttributes redirectAttribute) {
        // Handle the add logic
        System.out.println("Add Student in AdminUpdateController*********************");
        try {
            User user = userRepo.findById(id);
            if (user.getMyRoom() != null) {
                MyRoom myRoom = user.getMyRoom();
                myRoom.setUser(null);
                myRoomRepo.save(myRoom);
                myRoomRepo.deleteById(myRoom.getId());
            }
            userRepo.deleteById(id);
            redirectAttribute.addFlashAttribute("successMessage", "User Delete Successful!");
        } catch (Exception e) {
            System.out.println("error message: " + e.getLocalizedMessage());
            redirectAttribute.addFlashAttribute("errorMessage", "Something went wrong!");
        }
        return "redirect:/manage_student";
    }

    @PostMapping("/admin/deletebooking/{id}")
    public String deleteBooking(@PathVariable int id, RedirectAttributes redirectAttribute) {
        // Handle the deletion logic
        System.out.println("Delete Booking in AdminUpdateController*********************");
        MyRoom myRoom = myRoomRepo.findById(id);
        return "redirect:/manage_bookings";
    }

    public String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        return formattedDate;
    }

}
