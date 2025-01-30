package com.kshitij.hostel_hub.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kshitij.hostel_hub.entities.Review;
import com.kshitij.hostel_hub.entities.User;

public interface ReviewRepo extends JpaRepository<Review , Integer>{
    Review findReviewByUser(User user);
}
