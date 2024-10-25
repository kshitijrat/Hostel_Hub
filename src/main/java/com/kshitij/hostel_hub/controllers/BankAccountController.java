package com.kshitij.hostel_hub.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kshitij.hostel_hub.entities.BankAccount;
import com.kshitij.hostel_hub.entities.User;
import com.kshitij.hostel_hub.repo.BankAccountRepo;
import com.kshitij.hostel_hub.repo.UserRepo;
import com.kshitij.hostel_hub.services.BankAccountService;


@Controller
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private BankAccountRepo bankAccountRepo;

    @PostMapping("/addbankaccount")
    public String addBankAccount(@ModelAttribute("bankAccount") BankAccount bankAccount, Authentication authentication, RedirectAttributes redirectAttributes) {
        System.out.println("runn post mapping of add bank account*****************************");
        if(bankAccountService.isBankPresent(bankAccount.getCardNumber())){
            redirectAttributes.addFlashAttribute("error", "Card already exists!");
            return "redirect:/bank_account";
        }

        if(bankAccountService.getAllBankAccounts().isEmpty()){
            bankAccount.setPrimaryAccount(true);
        }


        String email = authentication.getName();
        User user = userRepo.findUserByUserEmail(email);
        //check if pin is present
        if (bankAccount.getPin() == null || bankAccount.getPin().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "PIN cannot be null or empty.");
            return "redirect:/bank_account";
        }  
        bankAccount.setExpiryDate(bankAccount.getExpiryDate());
        bankAccount.setAccountType("Saving");      
        bankAccount.setUser(user);
        bankAccount.setPin(passwordEncoder.encode(bankAccount.getPin()));
        bankAccountService.saveBankAccount(bankAccount);
        redirectAttributes.addFlashAttribute("success", "Bank account added successfully!");
        return "redirect:/bank_account";
    }



    @PostMapping("/deletebankaccount/{id}")
    public String deleteBankAccount(@PathVariable int id, RedirectAttributes redirectAttribute) {

        BankAccount bankAccount = bankAccountService.getBankAccountById(id);
        if(bankAccount.isPrimaryAccount()){
            redirectAttribute.addFlashAttribute("error", "delete_primary_account");
            return "redirect:/payment";
        }

        boolean isDeleted = bankAccountService.deleteBankAccount(id);

        if (isDeleted) {
            redirectAttribute.addAttribute("success", "Card Deleted Successfully!");
        } else {
            redirectAttribute.addAttribute("error", "Card Deletion Failed!");
        }

        List<BankAccount> bankAccounts = bankAccountService.getAllBankAccounts();
        redirectAttribute.addAttribute("bankAccounts", bankAccounts);
        redirectAttribute.addFlashAttribute("error", "delete_primary_account");
        return "redirect:/payment";   
    }

    @PostMapping("/updatebankaccount/{id}")
    public String updateBankAccount(@PathVariable int id, RedirectAttributes redirectAttribute, Authentication authentication){
        String email = authentication.getName();
        User user = userRepo.findUserByUserEmail(email);

        List<BankAccount> bankAccountList = bankAccountRepo.findByUser(user);
        BankAccount primaryAccount = bankAccountList.stream()
                .filter(BankAccount::isPrimaryAccount)
                .findFirst()
                .orElse(null);

        if (primaryAccount == null) {
            redirectAttribute.addFlashAttribute("error", "no_primary_account");
            return "redirect:/payment";
        }

        BankAccount bankAccount = bankAccountService.getBankAccountById(id);
        bankAccount.setPrimaryAccount(true);;
        // set prime account to secondary account
        primaryAccount.setPrimaryAccount(false);
        bankAccountService.saveBankAccount(bankAccount);
        bankAccountService.saveBankAccount(primaryAccount);

        redirectAttribute.addFlashAttribute("success", "success_update");
        return "redirect:/payment";

    }

}
