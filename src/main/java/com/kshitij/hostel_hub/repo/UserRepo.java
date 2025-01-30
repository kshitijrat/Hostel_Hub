package com.kshitij.hostel_hub.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kshitij.hostel_hub.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findUserByUserEmail(String email);
    List<User> findUserByRoles(String roles);
    User findById(int id);
    List<User> findUserByHostelNumber(int hostelNumber);
}

