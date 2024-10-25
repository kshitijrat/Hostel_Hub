package com.kshitij.hostel_hub.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    private int id;
    private String message;
    private String date;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

}

