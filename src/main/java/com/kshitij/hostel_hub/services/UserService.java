package com.kshitij.hostel_hub.services;

import java.util.stream.Collectors;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kshitij.hostel_hub.entities.User;
import com.kshitij.hostel_hub.repo.UserRepo;


@Service
public class UserService {

    @Autowired
    private UserRepo userRepository;

    

    public User findByUsername(String email) {
        return userRepository.findUserByUserEmail(email);
    }

    public User findByEmail(String email) {
        return userRepository.findUserByUserEmail(email);
    }

     public List<User> getOnlyNormalUser(List<User> users){
        return users.stream()
            .filter(user -> "ROLE_USER".equals(user.getRoles())) // Filter by role
            .collect(Collectors.toList()); // Collect the result into a List
    }

    public boolean deleteUserAccount(int id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }
  

}
