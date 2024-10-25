package com.kshitij.hostel_hub.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "changes")
public class Changes {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String notify;
    private String type; // use ke liye ya admin ke liye
    private String date;
    private int userId;
    @Column(nullable = false)
    private boolean isnew = true;

    
}
