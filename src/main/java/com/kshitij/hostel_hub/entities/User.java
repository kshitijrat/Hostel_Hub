package com.kshitij.hostel_hub.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name="user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_email", unique =  true)
    private String userEmail;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_phone")
    private String userPhone;

    @Column(name = "role")
    private String roles;

    @Column(name = "room_number")
    private String roomNumber;

    @Column(name = "room_type")
    private String roomType;

    private int hostelNumber;

    private String gender;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "my_room_id")
    private MyRoom myRoom;
    

    @Transient
    private String upcomingBookingId;

    @Transient
    private String upcomingBookingDate;

    @Transient
    private String activityDate1;

    @Transient
    private String activityDate2;

    @Transient
    private String activityDate3;

    @Transient
    private int totalStudents;

    @Transient
    private int pendingRequests;
}
