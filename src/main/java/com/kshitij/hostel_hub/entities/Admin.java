package com.kshitij.hostel_hub.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="admin")
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "admin_name")
    private String adminName;

    @Column(name = "admin_email")
    private String adminEmail;

    @Column(name = "admin_password")
    private String adminPassword;

    @Column(name = "admin_phone")
    private String adminPhone;

    private String gender;

    @Column(name = "role")
    private String role; // This can be set to "ROLE_ADMIN" by default

    @Column(unique = true)
    private int adminId;

    // Add any additional fields specific to the admin here if needed
}
