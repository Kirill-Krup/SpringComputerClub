package com.example.ComputerClub.DBO;

import com.example.ComputerClub.Model.Reservation;
import com.example.ComputerClub.Model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findAll();

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.status NOT IN (:excludedStatuses) " +
            "AND ( (r.startDate < :endDate OR (r.startDate = :endDate AND r.startTime < :endTime)) " +
            "AND   (r.endDate > :startDate OR (r.endDate = :startDate AND r.endTime > :startTime)) )")
    List<Reservation> findOverlappingReservations( @Param("startDate") LocalDate startDate,
    @Param("startTime") LocalTime startTime, @Param("endDate") LocalDate endDate, @Param("endTime") LocalTime endTime, @Param("excludedStatuses") List<ReservationStatus> excludedStatuses);

}
