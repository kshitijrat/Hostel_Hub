package com.kshitij.hostel_hub.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kshitij.hostel_hub.entities.Complaint;
import com.kshitij.hostel_hub.entities.User;

public interface ComplaintRepo extends JpaRepository<Complaint,Integer>{
    Complaint findComplaintByUser(User user);
    Complaint findComplaintById(int id);
}
