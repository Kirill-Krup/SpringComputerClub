package com.example.ComputerClub.Controllers.Client;

import com.example.ComputerClub.Controllers.Authorization.CustomUserDetails;
import com.example.ComputerClub.Model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping
    public String showProfile(Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        if(user.getPhotoPath() == null){
            user.setPhotoPath("/Images/user.png");
        }
        model.addAttribute("user", user);
        return "Client/profile";
    }

    @GetMapping("/replenishment")
    public String showReplenishment(Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        model.addAttribute("user", user);
        return "Client/replenishment";
    }
}
