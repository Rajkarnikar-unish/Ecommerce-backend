package com.rajkarnikarunish.ecommercebackend.view.controller;

import com.rajkarnikarunish.ecommercebackend.models.LocalUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/register")
    public String userRegistration(@ModelAttribute LocalUser user, Model model) {
        System.out.println(user.getFirstName());
        System.out.println(user.getLastName());
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        System.out.println(user.getUsername());
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("username", user.getUsername());
        return "welcome";
    }
}
