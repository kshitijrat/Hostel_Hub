package com.kshitij.hostel_hub.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kshitij.hostel_hub.entities.MyRoom;
import com.kshitij.hostel_hub.entities.User;
import com.kshitij.hostel_hub.repo.MyRoomRepo;



@Service
public class MyRoomService {
    
    @Autowired
    private MyRoomRepo myRoomRepo;

   

     public List<MyRoom> getAllRooms() {
        return myRoomRepo.findAll();
    }

    public MyRoom getRoomById(int id) {
        return myRoomRepo.findById(id);
    }

    public MyRoom saveRoom(MyRoom room) {
        return myRoomRepo.save(room);
    }

    public void deleteRoom(int id) {
        myRoomRepo.deleteById(id);
    }

    public List<User> getPendingRequestUser(){
        List<MyRoom> myRoomsList = myRoomRepo.findAll();
        List<User> userList = new ArrayList<>();
        for(MyRoom mr : myRoomsList){
            if(mr.getStatus().equals("Pending")){
                User user = mr.getUser();
                userList.add(user);
            }
        }
        return userList;
    }

    public boolean isValidRoom(MyRoom myRoom){
        String roomNumber = myRoom.getRoomNumber();
        List<MyRoom> list = myRoomRepo.findAll();
        for(MyRoom mr : list){
            if(mr.getId() == myRoom.getId())continue;
            if(mr.getRoomNumber().equals(roomNumber))return false;
        }
        return true;
    }

    public int maxRoomNo(){
        List<MyRoom> list = myRoomRepo.findAll();
        // MyRoom myRoom = list.get(list.size()-1);
        return list.isEmpty() ? 0 : list.get(list.size()-1).getId();
    }

}
