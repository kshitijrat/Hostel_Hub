package com.kshitij.hostel_hub.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kshitij.hostel_hub.entities.Admin;


@Repository
public interface AdminRepo extends JpaRepository<Admin, Integer> {
    Admin findAdminByAdminEmail(String email);
}
