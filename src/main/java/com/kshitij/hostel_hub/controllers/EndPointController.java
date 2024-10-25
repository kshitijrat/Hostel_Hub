package com.kshitij.hostel_hub.controllers;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kshitij.hostel_hub.entities.BankAccount;
import com.kshitij.hostel_hub.entities.Changes;
import com.kshitij.hostel_hub.entities.Complaint;
import com.kshitij.hostel_hub.entities.MyRoom;
import com.kshitij.hostel_hub.entities.Payment;
import com.kshitij.hostel_hub.entities.User;
import com.kshitij.hostel_hub.repo.MyRoomRepo;
import com.kshitij.hostel_hub.repo.PaymentRepo;
import com.kshitij.hostel_hub.repo.UserRepo;
import com.kshitij.hostel_hub.services.BankAccountService;
import com.kshitij.hostel_hub.services.ChangesServices;
import com.kshitij.hostel_hub.services.ComplaintService;
import com.kshitij.hostel_hub.services.MyRoomService;
import com.kshitij.hostel_hub.services.UserService;


@Controller
public class EndPointController {


    @Autowired
    private ChangesServices changesServices;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private MyRoomRepo myRoomRepo;

    @Autowired
    private MyRoomService myRoomService;

    @Autowired
    private UserService userService;


    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping("/notification")
    public String showNotificationPage(Model model, Authentication authentication) {
        System.out.println("show notification page enter................");
        String email = authentication.getName();

        User user = userRepository.findUserByUserEmail(email);
        // MyRoom myRoom = myRoomRepo.findMyRoomByUser(user);
        System.out.println("user email is: " + email);
        System.out.println("user role is: " + user.getRoles());

        // set for user
        if (user.getRoles().equalsIgnoreCase("ROLE_USER")) {
            List<Changes> notifications = changesServices.getLastFiveUserChanges(user.getId());
            model.addAttribute("notifications", notifications);

        }
        // set for admin
        else if (user.getRoles().equalsIgnoreCase("ROLE_ADMIN")) {
            List<Changes> notifications = changesServices.getAllAdminChanges();
            model.addAttribute("notifications", notifications);
        }
        
        return "notification";
    }

    @GetMapping("/index")
    public String login() {
        return "index";
    }

    @GetMapping("/gallery")
    public String gallery() {
        return "gallery";
    }

    @GetMapping("/dashboard_admin")
    public String showDashboardAdmin() {
        System.out.println("Enter dashboard admin*********************");
        return "dashboard_admin";
    }

    @GetMapping("/bank_account")
    public String showBankAccount(Model model) {
        model.addAttribute("bankAccount", new BankAccount());
        model.addAttribute("bankAccounts", bankAccountService.getAllBankAccounts());
        System.out.println("Enter bank account*********************");
        return "bank_account";
    }

    @GetMapping("/settings")
    public String showSettings(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userRepository.findUserByUserEmail(email);
        model.addAttribute("user", user);
        System.out.println("Show setting run...................");
        return "settings";
    }

    @GetMapping("/login")
    public String showLoginPage2() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        System.out.println("Enter in register*****************");
        return "register";
    }

    @GetMapping("/payment_report")
    public String showPaymentReport(Authentication authentication, Model model) {

        String email = authentication.getName();
        User user = userRepository.findUserByUserEmail(email);
        List<Payment> payment = paymentRepo.findByUser(user);
        MyRoom myRoom = myRoomRepo.findMyRoomByUser(user);
        model.addAttribute("payments", payment);
        model.addAttribute("user", user);
        model.addAttribute("myroom", myRoom);
        return "payment_report";

    }

    @GetMapping("/room_report")
    public String showRoomReport(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userRepository.findUserByUserEmail(email);
        MyRoom myRoom = myRoomRepo.findMyRoomByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("myroom", myRoom);
        return "room_report";
    }

    @GetMapping("/requestformadmin")
    public String showRequestForm(Authentication authentication, Model model) {
        List<User> userList = myRoomService.getPendingRequestUser();
        List<MyRoom> myRoomsList = myRoomRepo.findByStatus("Pending");
        model.addAttribute("myRoomList", myRoomsList);
        model.addAttribute("userList", userList);
        int max = Math.max(userList.size(), myRoomsList.size());
        model.addAttribute("max", max);
        System.out.println("Enter in request for admin******************");
        return "requestformadmin";
    }

    @GetMapping("/manage_student")
    public String showMangeStudent(Model model, @RequestParam(defaultValue = "0") int page) {
        List<User> users = userRepository.findAll();
        List<User> students = userService.getOnlyNormalUser(users);
        model.addAttribute("students", students);
        System.out.println("Enter in manage student*********************");
        return "manage_student";
    }

    @GetMapping("/manage_rooms")
    public String showMangeRooms(Model model) {
        long totalOccupied = myRoomRepo.countByStatus("Approved");

        long totalAvailable = 3600 - totalOccupied;

        model.addAttribute("totalAvailable", totalAvailable);
        model.addAttribute("totalOccupied", totalOccupied);
        System.out.println("Enter in manage rooms******************************");
        return "manage_rooms";
    }

    @GetMapping("/search-room")
    @ResponseBody
    public String searchRoom(@RequestParam("query") String roomNo) {
        System.out.println("Enter in search room***********************");
        int roomNumber;
        try {
            roomNumber = Integer.parseInt(roomNo);
        } catch (NumberFormatException e) {
            System.out.println("In search room, catch...............");
            return "Invalid room number";
        }

        if (roomNumber < 101 || roomNumber > 1300) {
            System.out.println("room number out of range...............");
            return "Room number out of range";
        }

        MyRoom myRoom = myRoomRepo.findRoomByRoomNumber(roomNo);
        if (myRoom != null) {
            System.out.println("Room not available.............");
            return "Room not available";
        } else {
            System.out.println("Room is available.............");
            return "Room is available";
        }

    }

    @GetMapping("/manage_bookings")
    public String showMangeBookings(Model model) {
        List<MyRoom> myRooms = myRoomRepo.findAll();

        model.addAttribute("myRoom", myRooms);
        System.out.println("Enter in manage bookings*************************");
        return "manage_bookings";
    }

    @GetMapping("/admin_setting")
    public String showAdminSetting() {
        System.out.println("Enter in admin setting*******************");
        return "admin_setting";
    }

    @GetMapping("/manage_hostel")
    public String showManageHostel() {
        System.out.println("Enter manage hostl.................");
        return "manage_hostel";
    }

    @GetMapping("/complaint")
    public String showComplaintPage(Authentication authentication, Model model) {

        String email = authentication.getName();
        System.out.println("email is: " + email);
        User user = userRepository.findUserByUserEmail(email);
        model.addAttribute("user", user);
        MyRoom myRoom = myRoomRepo.findMyRoomByUser(user);
        model.addAttribute("room", myRoom);
        System.out.println("Enter in complaint page...........");
        return "complaint";
    }

    @GetMapping("/privacy")
    public String showPrivacyPage() {
        return "privacy";
    }

    @GetMapping("/terms_of_services")
    public String sowTemrsOfSerivices() {
        return "terms_of_services";
    }

    @GetMapping("/about")
    public String showAbout() {
        return "about";
    }

    @GetMapping("/student_biodata")
    public String showStudentBiodataPage(Model model, @RequestParam("bookingId") String bookingId) {
        // You can now use the bookingId to retrieve student biodata or perform other
        // actions
        model.addAttribute("bookingId", bookingId);

        return "student_biodata"; // Return the page view
    }

    @GetMapping("/admin_complaint")
    public String showAdminComplaint(Model model){
        System.out.println("Enter in admin complaint..............");
        List<Complaint> list = complaintService.getPendingComplaint();
        Collections.reverse(list);
        model.addAttribute("complaints", list);
        return "admin_complaint";
    }

}
