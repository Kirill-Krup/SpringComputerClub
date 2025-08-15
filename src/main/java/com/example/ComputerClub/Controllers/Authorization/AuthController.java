package com.example.ComputerClub.Controllers.Authorization;

import com.example.ComputerClub.Model.User;
import com.example.ComputerClub.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {this.userService = userService;}

    @GetMapping("/login")
    public String showLoginWindow(){
        return "Authorization/login";
    }

    @GetMapping("/registration")
    public String showRegistrationWindow(Model model) {
        model.addAttribute("user", new User());
        return "Authorization/registration";
    }

    @PostMapping("/register")
    public String regNewUser(@ModelAttribute("user") User user, Model model) {
        try{
            userService.registerNewUser(user);
            UserDetails userDetails = userService.loadUserByUsername(user.getEmail());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(), userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            return "redirect:/home";
        } catch (UsernameNotFoundException e) {
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("user", user);
        return "Authorization/registration";
    } catch (RuntimeException e) {
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("user", user);
        return "Authorization/registration";
    }
    }
}
