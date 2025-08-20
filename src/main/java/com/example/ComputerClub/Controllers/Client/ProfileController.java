package com.example.ComputerClub.Controllers.Client;

import com.example.ComputerClub.Controllers.Authorization.CustomUserDetails;
import com.example.ComputerClub.Model.User;
import com.example.ComputerClub.Service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

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

    @PostMapping("/replenishment/top-up")
    public String replenishment(@RequestParam("amount") double amount, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        userService.replenishment(user, amount);
        return "redirect:/profile";
    }

    @GetMapping("/edit")
    public String showEdit(Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        model.addAttribute("user", user);
        return "Client/editProf";
    }

    @PostMapping("/edit")
    public String editProfile(@RequestParam String userLogin,@RequestParam String userFullName, @RequestParam String userEmail,@RequestParam String userPassword,Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        userService.editProfile(user,userLogin,userEmail,userFullName);
        return "redirect:/profile";
    }

}
