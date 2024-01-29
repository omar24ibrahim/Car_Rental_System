package com.carrental.carrental.controller;

import com.carrental.carrental.model.LoginRequest;
import com.carrental.carrental.model.RegistrationRequest;
import com.carrental.carrental.model.Reservation;
import com.carrental.carrental.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/user")
@AllArgsConstructor
@CrossOrigin("*") //4200
public class UserController {

    private final UserService userService;

    @PostMapping(path = "signup")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
        return userService.register(request);
    }
    // api/v1/registration/confirm?token=83d95fca-407c-4373-9dea-020a96fb0e18 (ex)
    @GetMapping(path = "confirm")
    public ResponseEntity<String> confirm(@RequestParam("token") String token) {
        return userService.confirmToken(token);
    }

    @PostMapping(path = "login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        return  userService.login(request);
    }

    @GetMapping(path = "/find/name/{name}")
    public ResponseEntity<?> getUsersByName(@PathVariable("name") String name) {
        return userService.findUsersByName(name);
    }

    @GetMapping(path = "/find/id/{id}")
    public ResponseEntity<?> getUsersById(@PathVariable("id") Integer id) {
        return userService.findUsersById(id);
    }

    @GetMapping(path = "/find/email/{email}")
    public ResponseEntity<?> getUsersByEmail(@PathVariable("email") String email) {
        return userService.findUsersByEmail(email);
    }

    @GetMapping(path = "/find/all")
    public ResponseEntity<?> getAllUsers() {
        return userService.findAllUsers();
    }
}
