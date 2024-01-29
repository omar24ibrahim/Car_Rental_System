package com.carrental.carrental.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {
    @Id
    @SequenceGenerator( // used to generate unique identifiers for the id field in appUser class
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1 //how many values are pre-allocated in single sequence
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;
    @ManyToOne //one appUser can have many confirmation token
    @JoinColumn(                     // Specifies the details of the foreign key column in
            nullable = false,                         // the database that
            name = "userId"      // is used to establish the association between the
    )                                // ConfirmationToken and AppUser entities.
    private User user;

    public ConfirmationToken(String token, LocalDateTime createdAt,
                             LocalDateTime expiresAt,  User user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.user = user;
    }
}
