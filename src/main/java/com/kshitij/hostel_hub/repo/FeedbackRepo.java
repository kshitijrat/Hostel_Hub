package com.kshitij.hostel_hub.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kshitij.hostel_hub.entities.Feedback;
import com.kshitij.hostel_hub.entities.User;


public interface FeedbackRepo extends JpaRepository<Feedback, Integer>{
    Feedback findByUser(User user);
}
