package com.carrental.carrental.controller;

import com.carrental.carrental.model.Car;
import com.carrental.carrental.model.Office;
import com.carrental.carrental.model.enums.CarStatus;
import com.carrental.carrental.service.CarService;
import com.carrental.carrental.service.OfficeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/car")
@AllArgsConstructor
@CrossOrigin("*")
public class CarResource { // This is a mirroring of whatever we have in the service
    private final CarService carService;
    private final OfficeService officeService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllCars(){
        return carService.findAllCars();
    }

    //api/car/find/500
    @GetMapping("/find/plate/{plateId}")
    public ResponseEntity<?> getCarByPlateId(@PathVariable("plateId") Long plateId){
        return carService.findCarByPlateIdFront(plateId);  //added for frontend searching purpose
    }

    @GetMapping("/find/brand/{brand}")
    public ResponseEntity<?> getCarsByBrand(@PathVariable("brand") String brand){
        return carService.findCarsByBrand(brand);
    }

    @GetMapping("/find/type/{type}")
    public ResponseEntity<?> getCarsByType(@PathVariable("type") String type){
        return carService.findCarsByType(type);
    }

    @GetMapping("/find/year/{year}")
    public ResponseEntity<?> getCarByYear(@PathVariable("year") Integer year){
        return carService.findCarsByYear(year);
    }

    @GetMapping("/find/transmission/{transmissionType}")
    public ResponseEntity<?> getCarsByTransmissionType(@PathVariable("transmissionType") String transmissionType){
        return carService.findCarsByTransmissionType(transmissionType);
    }

    @GetMapping("/find/fuel/{fuelType}")
    public ResponseEntity<?> getCarsByFuelType(@PathVariable("fuelType") String fuelType){
        return carService.findCarsByFuelType(fuelType);
    }

    @GetMapping("/find/body/{bodyStyle}")
    public ResponseEntity<?> getCarsByBodyStyle(@PathVariable("bodyStyle") String bodyStyle){
        return carService.findCarsByBodyStyle(bodyStyle);
    }

    @GetMapping("/find/color/{color}")
    public ResponseEntity<?> getCarsByColor(@PathVariable("color") String color){
        return carService.findCarsByColor(color);
    }

    @GetMapping("/find/capacity/{capacity}")
    public ResponseEntity<?> getCarsByCapacity(@PathVariable("capacity") Integer capacity){
        return carService.findCarsByCapacity(capacity);
    }

    @GetMapping("/find/rate/{rate}")
    public ResponseEntity<?> getCarsByRate(@PathVariable("rate") Float rate){
        return carService.findCarsByRate(rate);
    }

    @GetMapping("/find/status/{status}")
    public ResponseEntity<?> getCarsByStatus(@PathVariable("status") String status){
        return carService.findCarsByStatus(status);
    }

    @GetMapping("/find/office/{officeEmail}")
    public ResponseEntity<?> getCarsByOfficeId(@PathVariable("officeEmail") String officeEmail){
        return carService.findCarsByOffice(officeEmail);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCar(@RequestBody Car car){
        return carService.addCar(car);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCar(@RequestBody Car car){
        return carService.updateCar(car);
    }

    @DeleteMapping("/delete/{plateId}")
    public ResponseEntity<?> deleteCar(@PathVariable("plateId") Long plateId){
        carService.deleteCar(plateId);
        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/find/inOffice/{officeEmail}/brand/{value}")
    public ResponseEntity<?> getCarsByBrandInOffice(@PathVariable("officeEmail") String officeEmail, @PathVariable("value") String value) {
        return carService.findCarsByBrandInOffice(value, officeEmail);
    }

    @GetMapping("/find/inOffice/{officeEmail}/color/{value}")
    public ResponseEntity<?> getCarsByColorInOffice(@PathVariable("officeEmail") String officeEmail, @PathVariable("value") String value) {
        return carService.findCarsByColorInOffice(value, officeEmail);
    }

    @GetMapping("/find/inOffice/{officeEmail}/type/{value}")
    public ResponseEntity<?> getCarsByTypeInOffice(@PathVariable("officeEmail") String officeEmail, @PathVariable("value") String value) {
        return carService.findCarsByTypeInOffice(value, officeEmail);
    }

    @GetMapping("/find/inOffice/{officeEmail}/body/{value}")
    public ResponseEntity<?> getCarsByBodyStyleInOffice(@PathVariable("officeEmail") String officeEmail, @PathVariable("value") String value) {
        return carService.findCarsByBodyStyleInOffice(value, officeEmail);
    }

    @GetMapping("/find/inOffice/{officeEmail}/fuel/{value}")
    public ResponseEntity<?> getCarsByFuelTypeInOffice(@PathVariable("officeEmail") String officeEmail, @PathVariable("value") String value) {
        return carService.findCarsByFuelTypeInOffice(value, officeEmail);
    }

    @GetMapping("/find/inOffice/{officeEmail}/status/{value}")
    public ResponseEntity<?> getCarsByStatusInOffice(@PathVariable("officeEmail") String officeEmail, @PathVariable("value") String value) {
        return carService.findCarsByStatusInOffice(value, officeEmail);
    }

    @GetMapping("/find/inOffice/{officeEmail}/transmission/{value}")
    public ResponseEntity<?> getCarsByTransmissionTypeInOffice(@PathVariable("officeEmail") String officeEmail, @PathVariable("value") String value) {
        return carService.findCarsByTransmissionTypeInOffice(value, officeEmail);
    }

    @GetMapping("/find/inOffice/{officeEmail}/capacity/{value}")
    public ResponseEntity<?> getCarsByCapacityInOffice(@PathVariable("officeEmail") String officeEmail, @PathVariable("value") Integer value) {
        return carService.findCarsByCapacityInOffice(value, officeEmail);
    }

    @GetMapping("/find/inOffice/{officeEmail}/rate/{value}")
    public ResponseEntity<?> getCarsBellowRateInOffice(@PathVariable("officeEmail") String officeEmail, @PathVariable("value") Float value) {
        return carService.findCarsBellowRateInOffice(value, officeEmail);
    }

    @GetMapping("/find/inOffice/{officeEmail}/year/{value}")
    public ResponseEntity<?> getCarsByYearInOffice(@PathVariable("officeEmail") String officeEmail, @PathVariable("value") Integer value) {
        return carService.findCarsByYearInOffice(value, officeEmail);
    }

    @GetMapping("/find/inOffice/{officeEmail}/plate/{value}")
    public ResponseEntity<?> getCarByPlateIdInOffice(@PathVariable("officeEmail") String officeEmail, @PathVariable("value") long value) {
        return carService.findCarByPlateIdInOffice(value, officeEmail);
    }

    @GetMapping("/find/active/brand/{value}")
    public ResponseEntity<?> getActiveCarsByBrand(@PathVariable("value") String value) {
        return carService.findActiveCarsByBrand(value);
    }

    @GetMapping("/find/active/color/{value}")
    public ResponseEntity<?> getActiveCarsByColor(@PathVariable("value") String value) {
        return carService.findActiveCarsByColor(value);
    }

    @GetMapping("/find/active/type/{value}")
    public ResponseEntity<?> getActiveCarsByType(@PathVariable("value") String value) {
        return carService.findActiveCarsByType(value);
    }

    @GetMapping("/find/active/body/{value}")
    public ResponseEntity<?> getActiveCarsByBodyStyle(@PathVariable("value") String value) {
        return carService.findActiveCarsByBodyStyle(value);
    }

    @GetMapping("/find/active/fuel/{value}")
    public ResponseEntity<?> getActiveCarsByFuelType(@PathVariable("value") String value) {
        return carService.findActiveCarsByFuelType(value);
    }

    //Useless
    /*@GetMapping("/find/active/status/{value}")
    public ResponseEntity<?> getActiveCarsByStatus(@PathVariable("value") String value) {
        return carService.findActiveCarsByStatus(value);
    }*/

    @GetMapping("/find/active/transmission/{value}")
    public ResponseEntity<?> getActiveCarsByTransmission(@PathVariable("value") String value) {
        return carService.findActiveCarsByTransmissionType(value);
    }

    @GetMapping("/find/active/capacity/{value}")
    public ResponseEntity<?> getActiveCarsByCapacity(@PathVariable("value") Integer value) {
        return carService.findActiveCarsByCapacity(value);
    }

    @GetMapping("/find/active/rate/{value}")
    public ResponseEntity<?> getActiveCarsBellowRate(@PathVariable("value") Float value) {
        return carService.findActiveCarsBellowRate(value);
    }

    @GetMapping("/find/active/year/{value}")
    public ResponseEntity<?> getActiveCarsByYear(@PathVariable("value") Integer value) {
        return carService.findActiveCarsByYear(value);
    }

    @GetMapping("/find/active/plate/{value}")
    public ResponseEntity<?> getActiveCarByPlate(@PathVariable("value") long value) {
        return carService.findActiveCarByPlateId(value);
    }

}
