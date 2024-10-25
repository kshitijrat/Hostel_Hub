package com.kshitij.hostel_hub.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kshitij.hostel_hub.entities.BankAccount;
import com.kshitij.hostel_hub.entities.User;



public interface BankAccountRepo extends JpaRepository<BankAccount, Integer>{
    List<BankAccount> findByUser(User user);
    BankAccount findByCardNumber(String cardNumber);
}
