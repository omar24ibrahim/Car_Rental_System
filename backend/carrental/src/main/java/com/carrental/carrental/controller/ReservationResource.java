package com.carrental.carrental.controller;

import com.carrental.carrental.model.Reservation;
import com.carrental.carrental.model.ReservationRequest;
import com.carrental.carrental.service.CarService;
import com.carrental.carrental.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/reservations")
@AllArgsConstructor
@CrossOrigin("*")
public class ReservationResource {
    private final ReservationService reservationService;
    private final CarService carService;


    @GetMapping("/all")
    public ResponseEntity<?> getAllReservations() {
        return reservationService.findAllReservations();
    }

    @GetMapping("/find/id/{reservationId}")
    public ResponseEntity<?> getReservationByReservationId(
            @PathVariable("reservationId") Integer reservationId) {
        return reservationService.findReservationByReservationId(reservationId);
    }

    @PostMapping("/find")
    public ResponseEntity<?> getReservationsByDate(@RequestBody ReservationRequest reservationRequest){
        return reservationService.findReservations(reservationRequest);
    }

    @GetMapping("/find/car/{plateId}")
    public ResponseEntity<?> getReservationsByPlateId(@PathVariable("plateId") Long plateId) {
        return reservationService.findReservationsByCar(plateId);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addReservation(@RequestBody Reservation reservation) {
        return reservationService.addReservation(reservation);
    }

    @DeleteMapping("delete/{reservationId}")
    public ResponseEntity<String> deleteReservation(@PathVariable("reservationId") Integer reservationId) {
        return reservationService.deleteReservation(reservationId);
    }

    @PostMapping("payments")
    public ResponseEntity<?> findAllPayments(@RequestBody ReservationRequest reservationRequest){
        return reservationService.findAllPayments(reservationRequest);
    }
}
