package com.example.ComputerClub.Controllers.Client;

import com.example.ComputerClub.Controllers.Authorization.CustomUserDetails;
import com.example.ComputerClub.Model.Reservation;
import com.example.ComputerClub.Model.ReservationStatus;
import com.example.ComputerClub.Model.User;
import com.example.ComputerClub.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingController {
    private ReservationService reservationService;

    public BookingController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping(value = "/getReservations", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Reservation> getReservations(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime){
        return reservationService.getOverLappingReservations(startDate, startTime, endDate, endTime);
    }

    @GetMapping
    public String showBooking(Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        model.addAttribute("user", user);
        return "Client/booking";
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> booking(@RequestBody Reservation reservation) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        reservation.setUser(user);
        reservationService.saveBooking(reservation);
        return ResponseEntity.ok(reservation);
    }
}
