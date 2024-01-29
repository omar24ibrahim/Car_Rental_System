package com.carrental.carrental.model;

import com.carrental.carrental.model.enums.Branch;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"city", "country", "branch"})})
public class Office implements Serializable {
    @Id
    @SequenceGenerator( // used to generate unique identifiers
            name = "office_sequence",
            sequenceName = "office_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "office_sequence"
    )
    private Integer officeId;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private Branch branch;

    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "office")
    @JsonBackReference
    private List<Car> cars = new ArrayList<Car>();

    //constructor without id because id is auto-generated you shouldn't enter it manually
    // ADDED EMAIL FOR LOGIN
    public Office(String country, String city, Branch branch, String password, String email) {
        this.country = country;
        this.city = city;
        this.branch = branch;
        this.password = password;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Office{"+
                "id="+ officeId +
                ", country="+country+
                ", city="+city+
                ", branch="+branch+
                "}";
    }
}
