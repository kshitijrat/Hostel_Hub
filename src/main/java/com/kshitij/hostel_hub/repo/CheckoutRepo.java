package com.kshitij.hostel_hub.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kshitij.hostel_hub.entities.Checkout;
import com.kshitij.hostel_hub.entities.User;

public interface CheckoutRepo extends JpaRepository<Checkout,Integer>{
    Checkout findByUser(User user);
}
