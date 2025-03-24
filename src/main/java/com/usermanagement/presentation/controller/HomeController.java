package com.usermanagement.presentation.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser");
        model.addAttribute("isAuthenticated", isAuthenticated);
        return "home/index";
    }

    @GetMapping("/profile")
    public String profile() {
        return "home/dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "redirect:/oauth2/authorization/auth0";
    }
}
