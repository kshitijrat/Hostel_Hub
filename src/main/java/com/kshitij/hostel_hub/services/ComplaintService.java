package com.kshitij.hostel_hub.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kshitij.hostel_hub.entities.Complaint;
import com.kshitij.hostel_hub.repo.ComplaintRepo;

@Service
public class ComplaintService {
    
    @Autowired
    private ComplaintRepo complaintRepo;

    public boolean isNewComplaint(){
        List<Complaint> list = complaintRepo.findAll();
        Collections.reverse(list);
        for(Complaint complaint : list){
            if(complaint.getStatus().equalsIgnoreCase("Pending")){
                return true;
            }
        }
        return false;
    }


    public List<Complaint> getPendingComplaint(){
        List<Complaint> list = complaintRepo.findAll();
        List<Complaint> ans = new ArrayList<>();
        for(Complaint complaint: list){
            if(complaint.getStatus().equalsIgnoreCase("Pending")){
                ans.add(complaint);
            }
        }
        Collections.reverse(ans);
        return ans;
    }

}
