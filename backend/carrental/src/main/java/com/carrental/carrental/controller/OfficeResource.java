package com.carrental.carrental.controller;

import com.carrental.carrental.model.Office;
import com.carrental.carrental.model.User;
import com.carrental.carrental.model.enums.Branch;
import com.carrental.carrental.service.OfficeService;
import com.carrental.carrental.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/office")
@CrossOrigin("*")
public class OfficeResource {
    private final OfficeService officeService;
    private final UserService userService;
    @GetMapping("/all")
    public ResponseEntity<?> getAllOffices() {
        return officeService.findAllOffices();
    }

    @GetMapping("/find/id/{officeId}")
    public ResponseEntity<?> getOfficeByOfficeId(@PathVariable("officeId") Integer officeId) {
        return officeService.findOfficeByOfficeId(officeId);
    }

    @GetMapping("/find/country/{country}")
    public ResponseEntity<?> getOfficesByCountry(@PathVariable("country") String country) {
        return officeService.findOfficesByCountry(country);
    }

    @GetMapping("/find/city/{city}")
    public ResponseEntity<?> getOfficesByCity(@PathVariable("city") String city) {
        return officeService.findOfficesByCity(city);
    }

    @GetMapping("/find/branch/{branch}")
    public ResponseEntity<?> getOfficesByBranch(@PathVariable("branch") Branch branch) {
        return officeService.findOfficesByBranch(branch);
    }

    @GetMapping("/find/All/OfficesId")
    public ResponseEntity<?> getAllOfficesId(){
        return officeService.findAllOfficeIds();
    }

    /*@GetMapping("/find/cars/{officeId}")
    public ResponseEntity<List<Integer>> getOfficePlateIds(@PathVariable("officeId") Integer officeId) {
        List<Integer> cars = officeService.findOfficePlateIds(officeId);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }*/

    @PostMapping("/add")
    public ResponseEntity<String> addOffice(@RequestBody Office office) {
        return officeService.addOffice(office);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateOffice(@RequestBody Office office) {
        //TODO CHECK IF IT UPDATES STH IN USER
        return officeService.updateOffice(office);
    }

    //ADDED PART TO DELETE OFFICE USER
    @DeleteMapping("/delete/{officeId}")
    public ResponseEntity<String> deleteOffice(@PathVariable("officeId") Integer officeId) {
        ResponseEntity<?> responseEntity = officeService.findOfficeByOfficeId(officeId);
        if(responseEntity.getStatusCode().isSameCodeAs(HttpStatus.OK)){
            Office office = (Office) responseEntity.getBody();
            userService.deleteUser(office.getEmail());
            officeService.deleteOffice(officeId);
            return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("NOT FOUND", HttpStatus.NOT_FOUND);
    }
}
