package com.carrental.carrental.service;

import com.carrental.carrental.model.*;
import com.carrental.carrental.model.enums.CarStatus;
import com.carrental.carrental.repo.CarRepo;
import com.carrental.carrental.repo.ReservationRepo;
import com.carrental.carrental.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepo reservationRepo;
    private final CarRepo carRepo;
    private final UserRepo userRepo;

    private final StatusLogService statusLogService;

    @Autowired
    public ReservationService(ReservationRepo reservationRepo, CarRepo carRepo,
                              UserRepo userRepo, StatusLogService statusLogService){
        this.reservationRepo = reservationRepo;
        this.carRepo = carRepo;
        this.userRepo = userRepo;
        this.statusLogService = statusLogService;
    }

    @Transactional
    public ResponseEntity<String> addReservation(Reservation reservation) {
        Optional<Car> carOptional = carRepo.findCarByPlateId(reservation.getCar().getPlateId());
        Optional<User> userOptional = userRepo.findById(reservation.getUser().getId());
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(carOptional.isPresent()){
                Car car = carOptional.get();
                if(car.getStatus() == CarStatus.ACTIVE){
                    car.setStatus(CarStatus.RENTED);
                    Reservation newReservation = new Reservation(reservation.getStartDate(),
                            reservation.getEndDate(), car, user);
                    reservationRepo.save(newReservation);
                    statusLogService.insert(reservation.getStartDate(), car, car.getStatus());
                    return new ResponseEntity<>("Successfully reserved", HttpStatus.OK);
                }
                return new ResponseEntity<>("Car selected is unavailable for now",
                        HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>("No car with plate id " + reservation.getCar().getPlateId()
                    + " was found", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("No user with id " + reservation.getUser().getId()
                + " was found", HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> findReservations(ReservationRequest reservationRequest){
        if (reservationRequest.getEmail() != null) {
            List<Object[]> reservations = reservationRepo.
                    findReservationsForCustomerWithDetails(reservationRequest.getEmail());
            if(reservations.isEmpty()){
                return new ResponseEntity<>("No reservations for "+
                        reservationRequest.getEmail(),
                        HttpStatus.NOT_FOUND );
            }
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        } else if (reservationRequest.getStartDate() != null && reservationRequest.getPlateId() != null) {
            Optional<Car> optionalCar = carRepo.findCarByPlateId(reservationRequest.getPlateId());
            if(optionalCar.isPresent()){
                Car car = optionalCar.get();
                List<Object[]> reservations = reservationRepo.
                        findReservationsForCarInDateRange(car.getPlateId(), reservationRequest.getStartDate(),
                                reservationRequest.getEndDate());
                if(reservations.isEmpty()){
                    return new ResponseEntity<>("No reservations between "+
                            reservationRequest.getStartDate()+" and "+reservationRequest.getEndDate(),
                            HttpStatus.NOT_FOUND );
                }
                return new ResponseEntity<>(reservations, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("No car wih plate id "+
                        reservationRequest.getPlateId(), HttpStatus.NOT_FOUND);
            }
        } else {
            List<Object[]> reservations = reservationRepo.findReservationsByDateRange
                    (reservationRequest.getStartDate(), reservationRequest.getEndDate());
            if(reservations.isEmpty()){
                return new ResponseEntity<String>("No reservations between "+
                        reservationRequest.getStartDate()+" and "+reservationRequest.getEndDate(),
                        HttpStatus.NOT_FOUND );
            }
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> findAllReservations() {
        List<Object[]> reservations = reservationRepo.findAllReservations();
        if(reservations.isEmpty())
        {
            return new ResponseEntity<>("No reservations currently exist", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @Transactional //will recheck this after deciding criteria for delete
    public ResponseEntity<String> deleteReservation(Integer reservationId) {
        if(reservationRepo.findReservationByReservationId(reservationId).isPresent()){
            reservationRepo.deleteReservationByReservationId(reservationId);
            return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Reservation with id "+reservationId+" is not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> findReservationByReservationId(Integer reservationId) {
        Optional<Reservation> optionalReservation = reservationRepo.findReservationByReservationId(reservationId);
        if(optionalReservation.isPresent())
        {
            Reservation reservation = optionalReservation.get();
            return new ResponseEntity<>(reservation, HttpStatus.OK);
        }
        return new ResponseEntity<>("No reservation with id " + reservationId + " was found", HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> findReservationsByCar(Long plateId) {
        Optional<Car> optionalCar = carRepo.findCarByPlateId(plateId);
        if (optionalCar.isPresent()){
            Car newCar = optionalCar.get();
            List<Reservation> reservations = reservationRepo.findReservationsByCar(newCar);
            if(!reservations.isEmpty())
            {
                return new ResponseEntity<>(reservations, HttpStatus.OK);
            }
            return new ResponseEntity<>("No reservation for car with plate id " +
                    newCar.getPlateId() + " was found", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("No car with plate id " + plateId +
                " was found", HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> findAllPayments(ReservationRequest reservationRequest) {
        List<Object[]> payments = reservationRepo.findPayments(reservationRequest.getStartDate(), reservationRequest.getEndDate());
        if(payments.isEmpty())
        {
            return new ResponseEntity<>("No payment within period", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
}
