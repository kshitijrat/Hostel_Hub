package com.kshitij.hostel_hub.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kshitij.hostel_hub.entities.BankAccount;
import com.kshitij.hostel_hub.repo.BankAccountRepo;



@Service
public class BankAccountService {
     @Autowired
    private BankAccountRepo bankAccountRepository;

    public BankAccount saveBankAccount(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }

    public BankAccount getBankAccountById(int id) {
        return bankAccountRepository.findById(id).orElse(null);
    }

    public boolean deleteBankAccount(int id) {
        try {
            bankAccountRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isBankPresent(String cardNumber){
        BankAccount bankAccount = bankAccountRepository.findByCardNumber(cardNumber);
        if(bankAccount == null)return false;
        return true;
    }
}
