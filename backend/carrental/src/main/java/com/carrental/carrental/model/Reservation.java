package com.carrental.carrental.model;

import com.carrental.carrental.model.enums.BillingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Reservation implements Serializable {
    @Id
    @SequenceGenerator( // used to generate unique identifiers
            name = "reservation_sequence",
            sequenceName = "reservation_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "reservation_sequence"
    )
    private Integer reservationId;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate; //user has to enter endDate

    @ManyToOne()
    @JoinColumn(name = "plateId", nullable = false)
    private Car car;

    @ManyToOne()  //--------------------
    @JoinColumn(name = "id", nullable = false)
    private User user;
    //-------------------------------

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BillingStatus status;

    @Transient
    private float bill;

    public Reservation( Date startDate, Date endDate, Car car, User user) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.car = car;
        this.user = user;
        this.status = BillingStatus.PENDING; //initially pending might remove this
    }



    @Override
    public String toString() {
        return "Reservation{"+
                "id="+reservationId+
                ", startDate="+startDate+
                ", endDate="+endDate+
                ", car_plate_id="+this.car.getPlateId()+
                ", status=" + status +
                "}";
    }

    public void calculateBill(Car car){
        LocalDateTime startDateTime = this.startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endDateTime = this.endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Duration duration = Duration.between(startDateTime, endDateTime);
        long days = duration.toDays();
        float bill = days*car.getRate();
        this.setBill(bill);
    }
}
