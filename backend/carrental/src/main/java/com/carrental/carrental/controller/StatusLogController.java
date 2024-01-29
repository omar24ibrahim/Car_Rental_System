package com.carrental.carrental.controller;

import com.carrental.carrental.model.StatusRequest;
import com.carrental.carrental.service.StatusLogService;
import com.carrental.carrental.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/log")
@AllArgsConstructor
@CrossOrigin("*")
public class StatusLogController {

    private final StatusLogService statusLogService;

    @PostMapping
    public ResponseEntity<?> getAllStates(@RequestBody StatusRequest statusRequest) {
        return statusLogService.getAllEntries(statusRequest);
    }
}
