package com.kshitij.hostel_hub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kshitij.hostel_hub.entities.MyRoom;
import com.kshitij.hostel_hub.entities.User;
import com.kshitij.hostel_hub.repo.MyRoomRepo;
import com.kshitij.hostel_hub.repo.UserRepo;

@Controller
public class StartController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MyRoomRepo myRoomRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveAllUsers() {

        User user = new User();
        MyRoom myRoom = new MyRoom();
        // 1
        user.setUserEmail("xyz@gmail.com");
        user.setUserPassword(passwordEncoder.encode("1234"));
        user.setUserName("xyz");
        user.setUserPhone("123456787");
        user.setGender("Male");
        user.setHostelNumber(1);
        user.setRoomNumber("101");
        user.setRoles("ROLE_USER");
        user.setRoomType("single");

        myRoom.setBookingId("BKNo57Id186");
        myRoom.setDate(" 2024-10-24   20:36:41");
        myRoom.setRoomNumber("101");
        myRoom.setRoomType("single");
        myRoom.setStatus("Pending");
        myRoom.setUser(user);

        myRoomRepo.save(myRoom);
        user.setMyRoom(myRoom);
        userRepo.save(user);
        user = new User();
        myRoom = new MyRoom();

        // 2
        user.setUserEmail("xyz1@gmail.com");
        user.setUserPassword(passwordEncoder.encode("1234"));
        user.setUserName("xyz1");
        user.setUserPhone("123256787");
        user.setGender("Male");
        user.setHostelNumber(1);
        user.setRoomNumber("102");
        user.setRoles("ROLE_USER");
        user.setRoomType("single");

        myRoom.setBookingId("BKNo57Id116");
        myRoom.setDate(" 2024-10-24   20:42:41");
        myRoom.setRoomNumber("102");
        myRoom.setRoomType("single");
        myRoom.setStatus("Pending");
        myRoom.setUser(user);

        myRoomRepo.save(myRoom);
        user.setMyRoom(myRoom);
        userRepo.save(user);
        user = new User();
        myRoom = new MyRoom();

        // 3
        user.setUserEmail("xyz2@gmail.com");
        user.setUserPassword(passwordEncoder.encode("1234"));
        user.setUserName("xyz2");
        user.setUserPhone("123656787");
        user.setGender("Male");
        user.setHostelNumber(1);
        user.setRoomNumber("103");
        user.setRoles("ROLE_USER");
        user.setRoomType("single");

        myRoom.setBookingId("BKNo27Id186");
        myRoom.setDate(" 2024-10-24   21:16:41");
        myRoom.setRoomNumber("103");
        myRoom.setRoomType("single");
        myRoom.setStatus("Pending");
        myRoom.setUser(user);

        myRoomRepo.save(myRoom);
        user.setMyRoom(myRoom);
        userRepo.save(user);
        user = new User();
        myRoom = new MyRoom();

        // 4
        user.setUserEmail("xyz3@gmail.com");
        user.setUserPassword(passwordEncoder.encode("1234"));
        user.setUserName("xyz3");
        user.setUserPhone("123936787");
        user.setGender("Male");
        user.setHostelNumber(1);
        user.setRoomNumber("104");
        user.setRoles("ROLE_USER");
        user.setRoomType("single");

        myRoom.setBookingId("BKNo51Id186");
        myRoom.setDate(" 2024-10-24   21:43:41");
        myRoom.setRoomNumber("104");
        myRoom.setRoomType("single");
        myRoom.setStatus("Pending");
        myRoom.setUser(user);

        myRoomRepo.save(myRoom);
        user.setMyRoom(myRoom);
        userRepo.save(user);
        user = new User();
        myRoom = new MyRoom();

        // 5
        user.setUserEmail("xyz4@gmail.com");
        user.setUserPassword(passwordEncoder.encode("1234"));
        user.setUserName("xyz4");
        user.setUserPhone("653456787");
        user.setGender("Male");
        user.setHostelNumber(1);
        user.setRoomNumber("105");
        user.setRoles("ROLE_USER");
        user.setRoomType("single");

        myRoom.setBookingId("BKNo57Id196");
        myRoom.setDate(" 2024-10-24   22:23:12");
        myRoom.setRoomNumber("105");
        myRoom.setRoomType("single");
        myRoom.setStatus("Pending");
        myRoom.setUser(user);

        myRoomRepo.save(myRoom);
        user.setMyRoom(myRoom);
        userRepo.save(user);
        user = new User();
        myRoom = new MyRoom();

        // 6
        user.setUserEmail("xyz5@gmail.com");
        user.setUserPassword(passwordEncoder.encode("1234"));
        user.setUserName("xyz5");
        user.setUserPhone("1236547899");
        user.setGender("Male");
        user.setHostelNumber(1);
        user.setRoomNumber("106");
        user.setRoles("ROLE_USER");
        user.setRoomType("single");

        myRoom.setBookingId("BKNo57Id186");
        myRoom.setDate(" 2024-10-24   20:36:41");
        myRoom.setRoomNumber("106");
        myRoom.setRoomType("single");
        myRoom.setStatus("Pending");
        myRoom.setUser(user);

        myRoomRepo.save(myRoom);
        user.setMyRoom(myRoom);
        userRepo.save(user);
        user = new User();
        myRoom = new MyRoom();

    }

}
