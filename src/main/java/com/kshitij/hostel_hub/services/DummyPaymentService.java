package com.kshitij.hostel_hub.services;

import org.springframework.stereotype.Service;

@Service
public class DummyPaymentService {
    
    public String processPayment(double amount, String paymentId) {
        boolean paymentSuccess = Math.random() > 0.2; // 80% chance of success

        if (paymentSuccess) {
            // Simulate a successful payment
            return "Payment of $" + amount + " for Payment ID: " + paymentId + " processed successfully.";
        } else {
            // Simulate a failed payment
            return "Payment processing failed for Payment ID: " + paymentId + ". Please try again.";
        }
    }
}
