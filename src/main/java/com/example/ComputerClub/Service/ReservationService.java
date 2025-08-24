package com.example.ComputerClub.Service;

import com.example.ComputerClub.DBO.ReservationRepository;
import com.example.ComputerClub.Model.Reservation;
import com.example.ComputerClub.Model.ReservationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class ReservationService {
    private ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getOverLappingReservations(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        return reservationRepository.findOverlappingReservations(startDate, startTime, endDate, endTime, List.of(ReservationStatus.ВЫПОЛНЕН, ReservationStatus.ОТМЕНЁН));
    }

    public void saveBooking(Reservation reservation) {
        reservationRepository.save(reservation);
    }
}
