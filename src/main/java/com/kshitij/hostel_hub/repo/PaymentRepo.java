package com.kshitij.hostel_hub.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kshitij.hostel_hub.entities.Payment;
import com.kshitij.hostel_hub.entities.User;



public interface PaymentRepo extends JpaRepository<Payment,Integer>{

    // List<Payment> findByUser(int i);
    List<Payment> findByUser(User user);
    
}
