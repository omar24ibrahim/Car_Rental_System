package com.carrental.carrental.repo;

import com.carrental.carrental.model.Car;
import com.carrental.carrental.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReservationRepo extends JpaRepository<Reservation, Integer> {

    Optional<Reservation> findReservationByReservationId(Integer id);

    void deleteReservationByReservationId(Integer reservationId);


    @Query(value = "SELECT u.id, u.email, u.first_name, u.last_name, c.plate_id, c.brand, c.type, c.year, c.status, c.rate, r.reservation_id, r.start_date, r.end_date" +
            " FROM reservation r JOIN Car c ON r.plate_id = c.plate_id " +
            "JOIN User u ON r.id = u.id " +
            "WHERE r.start_date >= ?1 AND r.start_date <= ?2",nativeQuery = true)
    List<Object[]> findReservationsByDateRange(Date startDate, Date endDate);


    List<Reservation> findReservationsByCar(Car car);

    @Query(value = "SELECT u.id, u.email, u.first_name, u.last_name, c.plate_id, c.brand, c.type, c.year, c.status, c.rate, r.reservation_id, r.start_date, r.end_date" +
            " FROM reservation r " +
            "JOIN car c on r.plate_id = c.plate_id " +
            "JOIN User u ON r.id = u.id " +
            "WHERE r.plate_id = ?1 AND r.start_date >= ?2 AND r.start_date <= ?3",nativeQuery = true)
    List<Object[]> findReservationsForCarInDateRange(Long plate_id, Date startDate, Date endDate);

    @Query(value = "SELECT u.id, u.email, u.first_name, u.last_name, c.plate_id, c.brand, c.type, c.year, c.status, c.rate, r.reservation_id, r.start_date, r.end_date" +
            " FROM reservation r " +
            "JOIN car c on r.plate_id = c.plate_id " +
            "JOIN User u ON r.id = u.id " +
            "WHERE u.email = ?1", nativeQuery = true)
    List<Object[]> findReservationsForCustomerWithDetails(String email);

    @Query(value = "SELECT u.id, u.email, u.first_name, u.last_name, c.plate_id, c.brand, c.type, c.year, c.status, c.rate, r.reservation_id, r.start_date, r.end_date" +
            " FROM reservation r " +
            "JOIN car c on r.plate_id = c.plate_id " +
            "JOIN User u ON r.id = u.id ", nativeQuery = true)
    List<Object[]> findAllReservations();

    @Query(value = "SELECT CAST(r.start_date AS DATE), SUM(ABS(DATEDIFF(r.end_date, r.start_date)) * c.rate) AS payment" +
            " FROM reservation r " +
            "JOIN car c on r.plate_id = c.plate_id " +
            "WHERE r.start_date >= ?1 AND r.start_date <= ?2 " +
            "GROUP BY CAST(r.start_date AS DATE) ", nativeQuery = true)
    List<Object[]> findPayments(Date startDate, Date endDate);
}
