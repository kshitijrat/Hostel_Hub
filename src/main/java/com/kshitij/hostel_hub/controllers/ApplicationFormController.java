package com.kshitij.hostel_hub.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kshitij.hostel_hub.entities.Changes;
import com.kshitij.hostel_hub.entities.MyRoom;
import com.kshitij.hostel_hub.entities.Review;
import com.kshitij.hostel_hub.entities.User;
import com.kshitij.hostel_hub.repo.ChangesRepo;
import com.kshitij.hostel_hub.repo.MyRoomRepo;
import com.kshitij.hostel_hub.repo.ReviewRepo;
import com.kshitij.hostel_hub.repo.UserRepo;
import com.kshitij.hostel_hub.services.MyRoomService;

@Controller
public class ApplicationFormController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MyRoomRepo myRoomRepo;

    @Autowired
    private MyRoomService myRoomService;

    @Autowired
    private ChangesRepo changesRepo;

    @Autowired
    private ReviewRepo reviewRepo;

    @GetMapping("/applicationform")
    public String showApplicationFrom(Model model) {
        // String email = authentication.getName();
        // User user = userRepo.findUserByUserEmail(email);
        // model.addAttribute("user", user);
        return "applicationform";
    }

    @PostMapping("/register2")
    public String submitApplicationForm(@RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam("hostelNumber") String hostelNumberString,
            @RequestParam("gender") String gender,
            @RequestParam("roomType") String roomType,
            Model model, RedirectAttributes redirectAttribute) {
        System.out.println("Runnnnnn Application Form(/register2) register new student hostel form***************");
        User user = userRepo.findUserByUserEmail(email);
        
        // user ko register ke time hi email se registe krwaya tha is liye check kiya ki ye vahi user hai ya nhi
        if (user == null) {
            System.out.println("User is null");
            System.out.println("User: " + user);
            return "redirect:/applicationform";
        }

        //check phone number is valid or not
        try{
            long pn = Long.parseLong(phone);
            user.setUserPhone(phone);
        }catch(Exception e){
            redirectAttribute.addFlashAttribute("error", "Phone Number not valid");
            return "redirect:/applicationform";
        }


        // set hostel number string to int
        char c = hostelNumberString.charAt(hostelNumberString.length() - 1);
        int hostelNo = c - '0';
        int roomNumber = myRoomService.lastRoomThroughHostel(hostelNo);
        System.out.println("hostel No: "+hostelNo);
        System.out.println("Room no: "+roomNumber);
        //set booking id
        Random random = new Random();
        int x = random.nextInt(10, 100);
        int y = random.nextInt(101, 200);
        String bookingId = "BKNo" + x + "Id" + y;

        user.setRoomNumber(roomNumber + "");
        user.setRoomType(roomType);
        user.setGender(gender);
        user.setHostelNumber(c - '0');
        user.setUserName(name);
        
        
        MyRoom myRoom = new MyRoom();
        myRoom.setRoomNumber(roomNumber + "");
        myRoom.setUser(user);
        myRoom.setBookingId(bookingId);
        myRoom.setRoomType(roomType);
        myRoom.setStatus("Pending");
        myRoom.setDate(getCurrentDate() + "   " + getCurrentTime());
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

        //update review of user
        // Review review = new Review();
        // review.setUser(user);
        // review.setMessage("");
        // review.setRating(0);
        // reviewRepo.save(review);

        model.addAttribute("success", "User Register Successful!");
        return "redirect:/dashboard";
    }

    public String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        return formattedDate;
    }

    public String getCurrentTime() {
        LocalTime currTime = LocalTime.now();
        int hr = currTime.getHour();
        int min = currTime.getMinute();
        int sec = currTime.getSecond();
        return hr + ":" + min + ":" + sec + "";
    }

}
