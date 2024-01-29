package com.carrental.carrental.repo;

import com.carrental.carrental.model.Car;
import com.carrental.carrental.model.Office;
import com.carrental.carrental.model.enums.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CarRepo extends JpaRepository<Car,Long> {
    void deleteCarByPlateId(Long plateId);

    @Query(value = "SELECT * FROM car WHERE plate_id = ?1", nativeQuery = true)
    Optional<Car> findCarByPlateId(Long plateId);

    @Query(value = "SELECT * FROM car WHERE brand LIKE ?1%", nativeQuery = true)
    Optional<List<Car>> findCarsByBrand(String brand);

    @Query(value = "SELECT * FROM car WHERE color LIKE ?1%", nativeQuery = true)
    Optional<List<Car>> findCarsByColor(String color);

    @Query(value = "SELECT * FROM car WHERE year = ?1", nativeQuery = true)
    Optional<List<Car>> findCarsByYear(Integer year);

    @Query(value = "SELECT * FROM car WHERE type LIKE ?1%", nativeQuery = true)
    Optional<List<Car>> findCarsByType(String type);

    @Query(value = "SELECT * FROM car WHERE transmission_type LIKE ?1%", nativeQuery = true)
    Optional<List<Car>> findCarsByTransmissionType(String transmissionType);

    @Query(value = "SELECT * FROM car WHERE fuel_type LIKE ?1%", nativeQuery = true)
    Optional<List<Car>> findCarsByFuelType(String fuelType);

    @Query(value = "SELECT * FROM car WHERE body_style LIKE ?1%", nativeQuery = true)
    Optional<List<Car>> findCarsByBodyStyle(String bodyStyle);

    @Query(value = "SELECT * FROM car WHERE capacity = ?1", nativeQuery = true)
    Optional<List<Car>> findCarsByCapacity(Integer capacity);

    @Query(value = "SELECT * FROM car WHERE rate <= ?1", nativeQuery = true)
    Optional<List<Car>> findCarsByRate(Float rate);

    @Query(value = "SELECT * FROM car WHERE status  LIKE ?1%", nativeQuery = true)
    Optional<List<Car>> findCarsByStatus(String status);

    Optional<List<Car>> findCarsByOffice(Office office);

    //Office
    @Query(value = "SELECT * FROM car where brand LIKE ?1% AND office_id = ?2", nativeQuery = true)
    Optional<List<Car>> findCarsByBrandInOffice(String brand, Integer officeId);

    @Query(value = "SELECT * FROM car where color LIKE ?1% AND office_id = ?2", nativeQuery = true)
    Optional<List<Car>> findCarsByColorInOffice(String color, Integer officeId);

    @Query(value = "SELECT * FROM car where type LIKE ?1% AND office_id = ?2", nativeQuery = true)
    Optional<List<Car>> findCarsByTypeInOffice(String type, Integer officeId);

    @Query(value = "SELECT * FROM car where body_style LIKE ?1% AND office_id = ?2", nativeQuery = true)
    Optional<List<Car>> findCarsByBodyStyleInOffice(String bodyStyle, Integer officeId);

    @Query(value = "SELECT * FROM car where fuel_type LIKE ?1% AND office_id = ?2", nativeQuery = true)
    Optional<List<Car>> findCarsByFuelTypeInOffice(String fuelType, Integer officeId);

    @Query(value = "SELECT * FROM car where status LIKE ?1% AND office_id = ?2", nativeQuery = true)
    Optional<List<Car>> findCarsByStatusInOffice(String status, Integer officeId);

    @Query(value = "SELECT * FROM car where transmission_type LIKE ?1% AND office_id = ?2", nativeQuery = true)
    Optional<List<Car>> findCarsByTransmissionTypeInOffice(String transmissionType, Integer officeId);

    @Query(value = "SELECT * FROM car where capacity = ?1 AND office_id = ?2", nativeQuery = true)
    Optional<List<Car>> findCarsByCapacityInOffice(Integer capacity, Integer officeId);

    @Query(value = "SELECT * FROM car where rate <= ?1 AND office_id = ?2", nativeQuery = true)
    Optional<List<Car>> findCarsBellowRateInOffice(Float rate, Integer officeId);

    @Query(value = "SELECT * FROM car where year = ?1 AND office_id = ?2", nativeQuery = true)
    Optional<List<Car>> findCarsByYearInOffice(Integer year, Integer officeId);

    @Query(value = "SELECT * FROM car where plate_id = ?1 AND office_id = ?2", nativeQuery = true)
    Optional<List<Car>> findCarByPlateIdInOffice(Long plateId, Integer officeId);

    //Active
    @Query(value = "SELECT * FROM car where brand LIKE ?1% AND status = 'ACTIVE'", nativeQuery = true)
    Optional<List<Car>> findActiveCarsByBrand(String brand);

    @Query(value = "SELECT * FROM car where color LIKE ?1% AND status = 'ACTIVE'", nativeQuery = true)
    Optional<List<Car>> findActiveCarsByColor(String color);

    @Query(value = "SELECT * FROM car where type LIKE ?1% AND status = 'ACTIVE'", nativeQuery = true)
    Optional<List<Car>> findActiveCarsByType(String type);

    @Query(value = "SELECT * FROM car where body_style LIKE ?1% AND status = 'ACTIVE'", nativeQuery = true)
    Optional<List<Car>> findActiveCarsByBodyStyle(String bodyStyle);

    @Query(value = "SELECT * FROM car where fuel_type LIKE ?1% AND status = 'ACTIVE'", nativeQuery = true)
    Optional<List<Car>> findActiveCarsByFuelType(String fuelType);

    //Useless
    /*@Query(value = "SELECT * FROM car where status LIKE ?1% AND status = 'ACTIVE'", nativeQuery = true)
    Optional<List<Car>> findActiveCarsByStatus(String status);*/

    @Query(value = "SELECT * FROM car where transmission_type LIKE ?1% AND status = 'ACTIVE'", nativeQuery = true)
    Optional<List<Car>> findActiveCarsByTransmissionType(String transmissionType);

    @Query(value = "SELECT * FROM car where capacity = ?1 AND status = 'ACTIVE'", nativeQuery = true)
    Optional<List<Car>> findActiveCarsByCapacity(Integer capacity);

    @Query(value = "SELECT * FROM car where rate <= ?1 AND status = 'ACTIVE'", nativeQuery = true)
    Optional<List<Car>> findActiveCarsBellowRate(Float rate);

    @Query(value = "SELECT * FROM car where year = ?1 AND status = 'ACTIVE'", nativeQuery = true)
    Optional<List<Car>> findActiveCarsByYear(Integer year);

    @Query(value = "SELECT * FROM car where plate_id = ?1 AND status = 'ACTIVE'", nativeQuery = true)
    Optional<List<Car>> findActiveCarByPlateId(Long plateId);

    //added for frontend searching purpose
    @Query(value = "SELECT * FROM car where plate_id = ?1", nativeQuery = true)
    Optional<List<Car>> findCarByPlateIdFront(Long plateId);

}
