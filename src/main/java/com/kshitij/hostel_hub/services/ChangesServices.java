package com.kshitij.hostel_hub.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kshitij.hostel_hub.entities.Changes;
import com.kshitij.hostel_hub.repo.ChangesRepo;

@Service
public class ChangesServices {

    @Autowired
    private ChangesRepo changesRepo;


    public boolean isNewUpdateForUser(int userId) {
        List<Changes> list = changesRepo.findAll();
        if (list.isEmpty())
            return false;
        for (Changes changes : list) {
            if (changes.isIsnew() && changes.getType().equalsIgnoreCase("User") && changes.getUserId() == userId) {
                return true;
            }
        }
        return false;
    }
    public boolean isNewUpdateForAdmin() {
        List<Changes> list = changesRepo.findAll();
        if (list.isEmpty())
            return false;
        for (Changes changes : list) {
            if (changes.isIsnew() && changes.getType().equalsIgnoreCase("Admin")) {
                return true;
            }
        }
        return false;
    }

    public List<Changes> getUserNewChanges(int userId) {
        List<Changes> list = changesRepo.findAll();
        List<Changes> ans = new ArrayList<>();
        for (Changes changes : list) {
            if (changes.isIsnew() && changes.getType().equalsIgnoreCase("User") && changes.getUserId() == userId) {
                ans.add(changes);
            }
        }
        return ans;
    }

    public List<Changes> getLastFiveUserChanges(int userId) {
        List<Changes> list = changesRepo.findAll();
        List<Changes> ans = new ArrayList<>();
        Changes changes = new Changes();

        int i = list.size() - 1;
        int cnt = 0;
        while (i >= 0 && cnt < 5) {
            changes = list.get(i);
            if (changes.getType().equalsIgnoreCase("User") && changes.getUserId() == userId) {
                ans.add(changes);
                cnt++;
            }
            i--;
        }
        return ans;
    }

    public List<Changes> getAllAdminChanges() {
        List<Changes> list = changesRepo.findAll();
        List<Changes> ans = new ArrayList<>();
        int i = list.size()-1;
        Changes changes = new Changes();
        while (i >= 0) {
            changes = list.get(i);
            if (changes.getType().equalsIgnoreCase("Admin")) {
                ans.add(changes);
            }
            i--;
        }
        return ans;
    }

}
