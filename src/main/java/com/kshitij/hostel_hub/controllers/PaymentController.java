package com.kshitij.hostel_hub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kshitij.hostel_hub.entities.BankAccount;
import com.kshitij.hostel_hub.entities.Payment;
import com.kshitij.hostel_hub.entities.User;
import com.kshitij.hostel_hub.repo.BankAccountRepo;
import com.kshitij.hostel_hub.services.BankAccountService;
import com.kshitij.hostel_hub.services.DummyPaymentService;
import com.kshitij.hostel_hub.services.PaymentService;
import com.kshitij.hostel_hub.services.UserService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankAccountRepo bankAccountRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DummyPaymentService dummyPaymentService;

    @GetMapping("/payment")
    public String showPaymentPage(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        List<Payment> payments = paymentService.findByUser(user);
        model.addAttribute("payments", payments);
        model.addAttribute("bankAccounts", bankAccountService.getAllBankAccounts());
        return "payment";
    }

    @PostMapping("/payment")
    public String submitPayment(@RequestParam("amount") double amount,
            @RequestParam("date") String date,
            @RequestParam("pin") String pin,
            RedirectAttributes redirectAttribute,
            Authentication authentication) {
        System.out.println("Enter payment controller post mapping*****************");
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        Payment payment = new Payment();
        List<BankAccount> bankAccountList = bankAccountRepo.findByUser(user);
        BankAccount primaryAccount = bankAccountList.stream()
                .filter(BankAccount::isPrimaryAccount)
                .findFirst()
                .orElse(null);

        if (primaryAccount == null) {
            redirectAttribute.addFlashAttribute("error", "no_primary_account");
            return "redirect:/payment";
        }

        boolean pinValid = bankAccountList.stream()
                .filter(BankAccount::isPrimaryAccount)
                .anyMatch(account -> passwordEncoder.matches(pin, account.getPin()));

        if (!pinValid) {
            redirectAttribute.addFlashAttribute("error", "invalid_pin");
            return "redirect:/payment";
        }

        payment.setPaymentDate(date);
        payment.setAmount(amount);
        payment.setUser(user);
        payment.setPaymentId(UUID.randomUUID().toString());
        payment.setStatus("Pending");
        paymentService.save(payment); // Ensure you save the payment

        // Call the dummy payment service
        String paymentResponse = dummyPaymentService.processPayment(amount, payment.getPaymentId());

        // Check payment response and set payment status accordingly
        if (paymentResponse.contains("successfully")) {
            payment.setStatus("Completed");
            redirectAttribute.addFlashAttribute("success", "success_payment");
        } else {
            payment.setStatus("Failed");
            redirectAttribute.addFlashAttribute("error", "payment_failed");
        }

        paymentService.save(payment); // Save the updated payment status
        return "redirect:/payment";
    }
}