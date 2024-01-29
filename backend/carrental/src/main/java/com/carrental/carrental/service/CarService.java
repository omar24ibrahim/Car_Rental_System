package com.carrental.carrental.service;

import com.carrental.carrental.model.Car;
import com.carrental.carrental.model.Office;
import com.carrental.carrental.model.enums.CarStatus;
import com.carrental.carrental.repo.CarRepo;
import com.carrental.carrental.repo.OfficeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarRepo carRepo;
    private final OfficeRepo officeRepo;

    private final StatusLogService statusLogService;

    @Autowired
    public CarService(CarRepo carRepo, OfficeRepo officeRepo,
                      StatusLogService statusLogService) {
        this.carRepo = carRepo;
        this.officeRepo = officeRepo;
        this.statusLogService = statusLogService;
    }

    public ResponseEntity<String> addCar(Car car) { //edit save part
        if (!carRepo.existsById(car.getPlateId())) {
            if (officeRepo.existsById(car.getOffice().getOfficeId())) {
                Car newCar = new Car(car.getPlateId(), car.getBrand(), car.getType(), car.getYear(),
                        car.getStatus(), car.getRate(), car.getTransmissionType(), car.getFuelType(),
                        car.getBodyStyle(), car.getColor(), car.getCapacity(), car.getImageUrl(), car.getOffice());
                carRepo.save(newCar);
                statusLogService.insert(new Date(), car, car.getStatus());
                return new ResponseEntity<>("Successfully added", HttpStatus.CREATED);
            }
            return new ResponseEntity<>("No office with id " + car.getOffice().getOfficeId(), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Car with plate id " + car.getPlateId() + " already exists", HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> findAllCars() {
        List<Car> cars = carRepo.findAll();
        if (cars.isEmpty()) {
            return new ResponseEntity<>("No cars currently existing", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    public ResponseEntity<String> updateCar(Car car) {
        if (carRepo.existsById(car.getPlateId())) {
            if (officeRepo.existsById(car.getOffice().getOfficeId())) {
                carRepo.save(car);
                statusLogService.insert(new Date(), car, car.getStatus());
                return new ResponseEntity<>("Successfully updated", HttpStatus.OK);
            }
            return new ResponseEntity<>("No office with id " + car.getOffice().getOfficeId(), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("No car with plate id " + car.getPlateId(), HttpStatus.UNAUTHORIZED);
    }

    @Transactional
    public void deleteCar(Long plateId) {
        carRepo.deleteCarByPlateId(plateId);
    }

    public ResponseEntity<?> findCarByPlateId(Long plateId) {
        Optional<Car> car = carRepo.findCarByPlateId(plateId);
        if (car.isPresent()) {
            return new ResponseEntity<>(car, HttpStatus.OK);
        }
        return new ResponseEntity<>("No car with plate id " + plateId, HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findCarsByBrand(String brand) {
        Optional<List<Car>> cars = carRepo.findCarsByBrand(brand);
        if (cars.isPresent()) {
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }
        return new ResponseEntity<>("No car with " + brand + " brand was found", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findCarsByType(String type) {
        Optional<List<Car>> cars = carRepo.findCarsByType(type);
        if (cars.isPresent()) {
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }
        return new ResponseEntity<>("No car with " + type + " type was found", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findCarsByYear(Integer year) {
        Optional<List<Car>> cars = carRepo.findCarsByYear(year);
        if (cars.isPresent()) {
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }
        return new ResponseEntity<>("No car with " + year + " year was found", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findCarsByTransmissionType(String transmissionType) {
        Optional<List<Car>> cars = carRepo.findCarsByTransmissionType(transmissionType);
        if (cars.isPresent()) {
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }
        return new ResponseEntity<>("No car with " + transmissionType + " transmission type was found", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findCarsByFuelType(String fuelType) {
        Optional<List<Car>> cars = carRepo.findCarsByFuelType(fuelType);
        if (cars.isPresent()) {
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }
        return new ResponseEntity<>("No car with " + fuelType + " fuel type was found", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findCarsByBodyStyle(String bodyStyle) {
        Optional<List<Car>> cars = carRepo.findCarsByBodyStyle(bodyStyle);
        if (cars.isPresent()) {
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }
        return new ResponseEntity<>("No car with " + bodyStyle + " body style was found", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findCarsByColor(String color) {
        Optional<List<Car>> cars = carRepo.findCarsByColor(color);
        if (cars.isPresent()) {
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }
        return new ResponseEntity<>("No car with " + color + " color was found", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findCarsByCapacity(Integer capacity) {
        Optional<List<Car>> cars = carRepo.findCarsByCapacity(capacity);
        if (cars.isPresent()) {
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }
        return new ResponseEntity<>("No car with " + capacity + " seats capacity was found", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findCarsByRate(Float rate) {
        Optional<List<Car>> cars = carRepo.findCarsByRate(rate);
        if (cars.isPresent()) {
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }
        return new ResponseEntity<>("No car with " + rate + " $ rate was found", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findCarsByStatus(String status) {
        Optional<List<Car>> cars = carRepo.findCarsByStatus(status);
        if (cars.isPresent()) {
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }
        return new ResponseEntity<>("No " + status + " car was found", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findCarsByOffice(String officeEmail) {
        Optional<Office> office = officeRepo.findOfficeByEmail(officeEmail);
        if(office.isPresent()) {
            Optional<List<Car>> cars = carRepo.findCarsByOffice(office.get());
            if (cars.isPresent()) {
                return new ResponseEntity<>(cars, HttpStatus.OK);
            }
            return new ResponseEntity<>("No car registered for office with id " + office.get().getOfficeId() + " was found", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("No office with email " + office.get().getEmail() + " was found", HttpStatus.NO_CONTENT);
    }

    // Office
    public ResponseEntity<?> findCarsByBrandInOffice(String brand, String officeEmail) {
        Optional<Office> office = officeRepo.findOfficeByEmail(officeEmail);
        if (office.isPresent()) {
            Optional<List<Car>> cars = carRepo.findCarsByBrandInOffice(brand, office.get().getOfficeId());
            if (cars.isPresent()) {
                return new ResponseEntity<>(cars, HttpStatus.OK);
            }
            return new ResponseEntity<>("No car registered for office with id " + office.get().getOfficeId() + " was found matching", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("No office with email " + office.get().getEmail() + " was found", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findCarsByColorInOffice(String color, String officeEmail) {
        Optional<Office> office = officeRepo.findOfficeByEmail(officeEmail);
        if (office.isPresent()) {
            Optional<List<Car>> cars = carRepo.findCarsByColorInOffice(color, office.get().getOfficeId());
            if (cars.isPresent()) {
                return new ResponseEntity<>(cars, HttpStatus.OK);
            }
            return new ResponseEntity<>("No car registered for office with id " + office.get().getOfficeId() + " was found matching", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("No office with email " + office.get().getEmail() + " was found", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findCarsByTypeInOffice(String type, String officeEmail) {
        Optional<Office> office = officeRepo.findOfficeByEmail(officeEmail);
        if (office.isPresent()) {
            Optional<List<Car>> cars = carRepo.findCarsByTypeInOffice(type, office.get().getOfficeId());
            if (cars.isPresent()) {
                return new ResponseEntity<>(cars, HttpStatus.OK);
            }
            return new ResponseEntity<>("No car registered for office with id " + office.get().getOfficeId() + " was found matching", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("No office with email " + office.get().getEmail() + " was found", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findCarsByBodyStyleInOffice(String bodyStyle, String officeEmail) {
        Optional<Office> office = officeRepo.findOfficeByEmail(officeEmail);
        if (office.isPresent()) {
            Optional<List<Car>> cars = carRepo.findCarsByBodyStyleInOffice(bodyStyle, office.get().getOfficeId());
            if (cars.isPresent()) {
                return new ResponseEntity<>(cars, HttpStatus.OK);
            }
            return new ResponseEntity<>("No car registered for office with id " + office.get().getOfficeId() + " was found matching", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("No office with email " + office.get().getEmail() + " was found", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findCarsByFuelTypeInOffice(String fuelType, String officeEmail) {
        Optional<Office> office = officeRepo.findOfficeByEmail(officeEmail);
        if (office.isPresent()) {
            Optional<List<Car>> cars = carRepo.findCarsByFuelTypeInOffice(fuelType, office.get().getOfficeId());
            if (cars.isPresent()) {
                return new ResponseEntity<>(cars, HttpStatus.OK);
            }
            return new ResponseEntity<>("No car registered for office with id " + office.get().getOfficeId() + " was found matching", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("No office with email " + office.get().getEmail() + " was found", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findCarsByStatusInOffice(String status, String officeEmail) {
        Optional<Office> office = officeRepo.findOfficeByEmail(officeEmail);
        if (office.isPresent()) {
            Optional<List<Car>> cars = carRepo.findCarsByStatusInOffice(status, office.get().getOfficeId());
            if (cars.isPresent()) {
                return new ResponseEntity<>(cars, HttpStatus.OK);
            }
            return new ResponseEntity<>("No car registered for office with id " + office.get().getOfficeId() + " was found matching", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("No office with email " + office.get().getEmail() + " was found", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findCarsByTransmissionTypeInOffice(String transmissionType, String officeEmail) {
        Optional<Office> office = officeRepo.findOfficeByEmail(officeEmail);
        if (office.isPresent()) {
            Optional<List<Car>> cars = carRepo.findCarsByTransmissionTypeInOffice(transmissionType, office.get().getOfficeId());
            if (cars.isPresent()) {
                return new ResponseEntity<>(cars, HttpStatus.OK);
            }
            return new ResponseEntity<>("No car registered for office with id " + office.get().getOfficeId() + " was found matching", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("No office with email " + office.get().getEmail() + " was found", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findCarsByCapacityInOffice(Integer capacity, String officeEmail) {
        Optional<Office> office = officeRepo.findOfficeByEmail(officeEmail);
        if (office.isPresent()) {
            Optional<List<Car>> cars = carRepo.findCarsByCapacityInOffice(capacity, office.get().getOfficeId());
            if (cars.isPresent()) {
                return new ResponseEntity<>(cars, HttpStatus.OK);
            }
            return new ResponseEntity<>("No car registered for office with id " + office.get().getOfficeId() + " was found matching", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("No office with email " + office.get().getEmail() + " was found", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findCarsBellowRateInOffice(Float rate, String officeEmail) {
        Optional<Office> office = officeRepo.findOfficeByEmail(officeEmail);
        if (office.isPresent()) {
            Optional<List<Car>> cars = carRepo.findCarsBellowRateInOffice(rate, office.get().getOfficeId());
            if (cars.isPresent()) {
                return new ResponseEntity<>(cars, HttpStatus.OK);
            }
            return new ResponseEntity<>("No car registered for office with id " + office.get().getOfficeId() + " was found matching", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("No office with email " + office.get().getEmail() + " was found", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findCarsByYearInOffice(Integer year, String officeEmail) {
        Optional<Office> office = officeRepo.findOfficeByEmail(officeEmail);
        if (office.isPresent()) {
            Optional<List<Car>> cars = carRepo.findCarsByYearInOffice(year, office.get().getOfficeId());
            if (cars.isPresent()) {
                return new ResponseEntity<>(cars, HttpStatus.OK);
            }
            return new ResponseEntity<>("No car registered for office with id " + office.get().getOfficeId() + " was found matching", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("No office with email " + office.get().getEmail() + " was found", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findCarByPlateIdInOffice(long plateId, String officeEmail) {
        Optional<Office> office = officeRepo.findOfficeByEmail(officeEmail);
        if (office.isPresent()) {
            Optional<List<Car>> car = carRepo.findCarByPlateIdInOffice(plateId, office.get().getOfficeId());  //added for frontend searching purpose
            if (car.isPresent()) {
                return new ResponseEntity<>(car, HttpStatus.OK);
            }
            return new ResponseEntity<>("No car registered for office with id " + office.get().getOfficeId() + " was found matching", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("No office with email " + office.get().getEmail() + " was found", HttpStatus.NO_CONTENT);
    }

    // Active
    public ResponseEntity<?> findActiveCarsByBrand(String brand) {
        Optional<List<Car>> cars = carRepo.findActiveCarsByBrand(brand);
            if (cars.isPresent()) {
                return new ResponseEntity<>(cars, HttpStatus.OK);
            }
        return new ResponseEntity<>("No active car matching", HttpStatus.NO_CONTENT);
        }

    public ResponseEntity<?> findActiveCarsByColor(String color) {
        Optional<List<Car>> cars = carRepo.findActiveCarsByColor(color);
            if (cars.isPresent()) {
                return new ResponseEntity<>(cars, HttpStatus.OK);
            }
        return new ResponseEntity<>("No active car matching", HttpStatus.NO_CONTENT);
        }

    public ResponseEntity<?> findActiveCarsByType(String type) {
        Optional<List<Car>> cars = carRepo.findActiveCarsByType(type);
            if (cars.isPresent()) {
                return new ResponseEntity<>(cars, HttpStatus.OK);
            }
        return new ResponseEntity<>("No active car matching", HttpStatus.NO_CONTENT);
        }

    public ResponseEntity<?> findActiveCarsByBodyStyle(String bodyStyle) {
        Optional<List<Car>> cars = carRepo.findActiveCarsByBodyStyle(bodyStyle);
            if (cars.isPresent()) {
                return new ResponseEntity<>(cars, HttpStatus.OK);
            }
        return new ResponseEntity<>("No active car matching", HttpStatus.NO_CONTENT);
        }

    public ResponseEntity<?> findActiveCarsByFuelType(String fuelType) {
        Optional<List<Car>> cars = carRepo.findActiveCarsByFuelType(fuelType);
            if (cars.isPresent()) {
                return new ResponseEntity<>(cars, HttpStatus.OK);
            }
        return new ResponseEntity<>("No active car matching", HttpStatus.NO_CONTENT);
        }

    //Useless
    /*public ResponseEntity<?> findActiveCarsByStatus(String status) {
    Optional<List<Car>> cars = carRepo.findActiveCarsByStatus(status);
        if (cars.isPresent()) {
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }
    return new ResponseEntity<>("No active car matching", HttpStatus.NO_CONTENT);
    }*/

    public ResponseEntity<?> findActiveCarsByTransmissionType(String transmissionType) {
        Optional<List<Car>> cars = carRepo.findActiveCarsByTransmissionType(transmissionType);
            if (cars.isPresent()) {
                return new ResponseEntity<>(cars, HttpStatus.OK);
            }
        return new ResponseEntity<>("No active car matching", HttpStatus.NO_CONTENT);
        }

    public ResponseEntity<?> findActiveCarsByCapacity(Integer capacity) {
        Optional<List<Car>> cars = carRepo.findActiveCarsByCapacity(capacity);
            if (cars.isPresent()) {
                return new ResponseEntity<>(cars, HttpStatus.OK);
            }
        return new ResponseEntity<>("No active car matching", HttpStatus.NO_CONTENT);
        }

    public ResponseEntity<?> findActiveCarsBellowRate(Float rate) {
        Optional<List<Car>> cars = carRepo.findActiveCarsBellowRate(rate);
            if (cars.isPresent()) {
                return new ResponseEntity<>(cars, HttpStatus.OK);
            }
        return new ResponseEntity<>("No active car matching", HttpStatus.NO_CONTENT);
        }

    public ResponseEntity<?> findActiveCarsByYear(Integer year) {
        Optional<List<Car>> cars = carRepo.findActiveCarsByYear(year);
        if (cars.isPresent()) {
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }
        return new ResponseEntity<>("No active car matching", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findActiveCarByPlateId(long plateId) {
        Optional<List<Car>> car = carRepo.findActiveCarByPlateId(plateId);   //added for frontend searching purpose
        if (car.isPresent()) {
            return new ResponseEntity<>(car, HttpStatus.OK);
        }
        return new ResponseEntity<>("No active car matching", HttpStatus.NO_CONTENT);
    }

    //added for frontend searching purpose
    public ResponseEntity<?> findCarByPlateIdFront(long plateId) {
        Optional<List<Car>> car = carRepo.findCarByPlateIdFront(plateId);
        if (car.isPresent()) {
            return new ResponseEntity<>(car, HttpStatus.OK);
        }
        return new ResponseEntity<>("No active car matching", HttpStatus.NO_CONTENT);
    }
}