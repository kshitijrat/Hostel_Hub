package com.kshitij.hostel_hub.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kshitij.hostel_hub.entities.Review;
import com.kshitij.hostel_hub.repo.ReviewRepo;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepo reviewRepo;

    public int getReviewCount() {
        List<Review> list = reviewRepo.findAll();
        int n = list.size();
        return list.isEmpty() || list == null ? 0 : n;
    }

    public float getAvgRating() {
        int cnt = 0;
        List<Review> list = reviewRepo.findAll();
        for (Review review : list) {
            cnt += review.getRating();
        }
        String res = String.format("%.1f", (float) cnt / list.size());
        return Float.parseFloat(res);
    }

    public String getLastReview() {
        List<Review> list = reviewRepo.findAll();
        int n = list.size();
        if(n<=0)return "";
        Review review = list.get(n - 1);
        String msg = review.getMessage();
        return n > 18 ? msg.substring(0, 35) : msg;
    }

    public String getLastDate(){
        List<Review> list = reviewRepo.findAll();
        int n = list.size();
        if(n<=0)return "";
        Review review = list.get(n - 1);
        String date = review.getDate();
        return date;
    }

}
