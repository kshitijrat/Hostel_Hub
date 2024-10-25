package com.kshitij.hostel_hub.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String paymentId;

    @Column(nullable = false)
    // @Temporal(TemporalType.DATE)
    private String paymentDate;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String status;

    // Additional fields can be added here if needed

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

  

}
