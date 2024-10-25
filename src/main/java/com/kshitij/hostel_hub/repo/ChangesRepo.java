package com.kshitij.hostel_hub.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kshitij.hostel_hub.entities.Changes;

public interface ChangesRepo extends JpaRepository<Changes,Integer>{
        
}
