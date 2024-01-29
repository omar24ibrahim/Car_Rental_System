package com.carrental.carrental.service;

import com.carrental.carrental.model.Office;
import com.carrental.carrental.model.User;
import com.carrental.carrental.model.enums.UserRole;
import com.carrental.carrental.model.enums.Branch;
import com.carrental.carrental.repo.OfficeRepo;
import com.carrental.carrental.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OfficeService {
    private final OfficeRepo officeRepo;
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailValidatorService emailValidatorService;

    //YOU SHOULD CREATE OFFICE USING CONSTRUCTOR TO AUTO GENERATE ID
    //ADDED CHECK FOR UNIQUE CONSTRAINTS (CITY,COUNTY,BRANCH) AND (EMAIL)
    //ADD OFFICE TO USER TABLE WITH ROLE AS OFFICE WILL NEED IT IN BACKEND
    public ResponseEntity<String> addOffice(Office office) {
        String email = office.getEmail();
        boolean isValidEmail = emailValidatorService.test(email); //validate request email
        if(!isValidEmail) {
            return new ResponseEntity<>("email not valid", HttpStatus.UNAUTHORIZED);
        }
        if(isExist(email)){
            return new ResponseEntity<>("email already exist", HttpStatus.UNAUTHORIZED);
        }
        if(officeRepo.existsByCityAndCountryAndBranch(office.getCity(),
                office.getCountry(), office.getBranch())){
            return new ResponseEntity<>("office in "+office.getCity()+", "+office.getCountry()
                    +" of "+office.getBranch()+" already exists", HttpStatus.UNAUTHORIZED);
        }
        String encodedPassword = bCryptPasswordEncoder.encode(office.getPassword());
        office.setPassword(encodedPassword);
        Office newOffice = new Office(office.getCountry(), office.getCity(), office.getBranch(),
                office.getPassword(), office.getEmail());
        officeRepo.save(newOffice);
        // TODO ADD OFFICE TO USER WITH OFFICE ROLE
        User officeUser = new User(office.getCity(),office.getCountry()+", "+office.getBranch(),
                office.getEmail(), office.getPassword(), UserRole.OFFICE);
        officeUser.setEnabled(Boolean.TRUE); //NO NEED FOR EMAIL CONFIRMATION
        userRepo.save(officeUser);
        return new ResponseEntity<>("Successfully created", HttpStatus.CREATED);
    }

    public ResponseEntity<?> findAllOffices() {
        List<Office> offices = officeRepo.findAll();
        if(offices.isEmpty())
        {
            return new ResponseEntity<>("No offices currently exist", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(offices, HttpStatus.OK);
    }

    public ResponseEntity<?> findAllOfficeIds(){
        Optional<List<Integer>> officesId = officeRepo.findAllOfficeIds();
        if(officesId.isEmpty()){
            return new ResponseEntity<>("No offices currently exist", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(officesId,HttpStatus.OK);
    }

    public ResponseEntity<String> updateOffice(Office office) {
        if(officeRepo.existsById(office.getOfficeId()))
        {
            officeRepo.save(office);
            return new ResponseEntity<>("Successfully updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("No office with id " + office.getOfficeId() + " was found", HttpStatus.NO_CONTENT);
    }

    @Transactional
    public void deleteOffice(Integer officeId) {
        officeRepo.deleteOfficeByOfficeId(officeId);
    }

    //YOU NEED TO USE .GET() TO ACTUALLY RETURN AN OFFICE
    public ResponseEntity<?> findOfficeByOfficeId(Integer officeId) {
        Optional<Office> office = officeRepo.findOfficeByOfficeId(officeId);
        if(office.isPresent())
        {
            return new ResponseEntity<>(office.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("No office with id " + officeId + " was found", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findOfficesByCountry(String country) {
        Optional<List<Office>> offices = officeRepo.findOfficesByCountry(country);
        if(offices.isPresent())
        {
            return new ResponseEntity<>(offices, HttpStatus.OK);
        }
        return new ResponseEntity<>("No office in " + country + " was found", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findOfficesByCity(String city) {
        Optional<List<Office>> offices = officeRepo.findOfficesByCity(city);
        if(offices.isPresent())
        {
            return new ResponseEntity<>(offices, HttpStatus.OK);
        }
        return new ResponseEntity<>("No office in " + city + " was found", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findOfficesByBranch(Branch branch) {
        Optional<List<Office>> offices = officeRepo.findOfficesByBranch(branch);
        if(offices.isPresent())
        {
            return new ResponseEntity<>(offices, HttpStatus.OK);
        }
        return new ResponseEntity<>("No office in " + branch.getDisplayName() + " was found", HttpStatus.NO_CONTENT);
    }

    public boolean isExist(String email){
        Optional<Office> office = officeRepo.findOfficeByEmail(email);
        return office.isPresent();
    }
}
