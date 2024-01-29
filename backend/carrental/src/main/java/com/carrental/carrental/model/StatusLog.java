package com.carrental.carrental.model;

import com.carrental.carrental.model.enums.CarStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class StatusLog implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long statusId;

    private Date date;

    @ManyToOne()
    @JoinColumn(name = "plateId", nullable = false)
    private Car car;

    @Enumerated(EnumType.STRING)
    private CarStatus status;

    public StatusLog(Date date, Car car, CarStatus status) {
        this.date = date;
        this.car = car;
        this.status = status;
    }
}
