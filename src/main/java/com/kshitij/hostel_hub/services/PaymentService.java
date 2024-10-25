package com.kshitij.hostel_hub.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kshitij.hostel_hub.entities.Payment;
import com.kshitij.hostel_hub.entities.User;
import com.kshitij.hostel_hub.repo.PaymentRepo;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepo paymentRepository;

    public List<Payment> findByUser(User user) {
        return paymentRepository.findByUser(user);
    }

    public void save(Payment payment) {
        paymentRepository.save(payment);
    }

    public List<Payment> getPaymentsByUser(User user) {
        return paymentRepository.findByUser(user);
    }
   

}
