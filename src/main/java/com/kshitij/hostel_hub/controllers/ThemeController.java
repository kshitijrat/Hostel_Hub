package com.kshitij.hostel_hub.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ThemeController {
    // @GetMapping("/theme")
    // public String index(Model model, @RequestParam(value = "theme", defaultValue = "light") String theme) {
    //     model.addAttribute("theme", theme);
    //     return "index";  // return the HTML page (index.html)
    // }

    // @PostMapping("/change-theme")
    // public String changeTheme(@RequestParam("theme") String theme) {
    //     return "redirect:/?theme=" + theme;
    // }
}
