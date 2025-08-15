package com.example.ComputerClub.Controllers.Client;

import com.example.ComputerClub.Controllers.Authorization.CustomUserDetails;
import com.example.ComputerClub.Model.User;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String showHome(Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        if(user.getPhotoPath() == null){
            user.setPhotoPath("/Images/user.png");
        }
        model.addAttribute("userLogin", user.getLogin());
        model.addAttribute("userBalance", user.getWallet());
        model.addAttribute("userPhoto", user.getPhotoPath());
        return "Client/home";
    }
}
