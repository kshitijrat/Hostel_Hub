package com.kshitij.hostel_hub.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kshitij.hostel_hub.entities.Activite;
import com.kshitij.hostel_hub.entities.User;
import com.kshitij.hostel_hub.other.StopWatch;
import com.kshitij.hostel_hub.repo.ActiveRepo;
import com.kshitij.hostel_hub.repo.UserRepo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private ActiveRepo activeRepo;

    private StopWatch stopWatch = new StopWatch();

    @GetMapping("/")
    public String home(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            stopWatch.start();
            return "redirect:/dashboard";
        }
        System.out.println("Error: in login controller************");
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        stopWatch.stop();
        saveActiveTime(authentication);
        new SecurityContextLogoutHandler().logout(request, response, null);
        return "redirect:/index";
    }

    private void saveActiveTime(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userRepository.findUserByUserEmail(authentication.getName());
            if (user != null) {
                long activeTimeSecs = stopWatch.getElapsedTimeSecs();
                String activeTime = String.format("%d seconds", activeTimeSecs);
                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                Activite activite = new Activite();
                activite.setUser(user);
                activite.setActiveTime(activeTime);
                activite.setDate(date);

                activeRepo.save(activite);
            }
        }
    }

   

}
