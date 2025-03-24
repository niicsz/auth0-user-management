package com.usermanagement.presentation.controller;

import com.usermanagement.application.dto.UserDTO;
import com.usermanagement.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String viewProfile(Authentication authentication, Model model) {
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
            String email = oauth2User.getAttribute("email");

            try {
                UserDTO userDTO = userService.getUserByEmail(email);
                model.addAttribute("user", userDTO);
            } catch (Exception e) {
                model.addAttribute("errorMessage", "Não foi possível carregar os dados do usuário");
            }
        }

        return "user/profile";
    }
}