package com.carrental.carrental.service;

import com.carrental.carrental.model.*;
import com.carrental.carrental.model.enums.CarStatus;
import com.carrental.carrental.repo.StatusLogRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class StatusLogService {

    @Autowired
    private final StatusLogRepo statusLogRepo;

    public void insert(Date date, Car car, CarStatus status) {
        StatusLog entry = new StatusLog(date, car, status);
        statusLogRepo.save(entry);
    }

    public ResponseEntity<?> getAllEntries(StatusRequest statusRequest) {
        List<Object[]> results = statusLogRepo.getAllStates(statusRequest.getDate());
        if(results.isEmpty())
        {
            return new ResponseEntity<>("No states within period", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
