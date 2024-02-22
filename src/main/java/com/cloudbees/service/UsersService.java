package com.cloudbees.service;

import com.cloudbees.model.Seat;
import com.cloudbees.model.User;

import java.util.HashMap;

public interface UsersService {

    public HashMap<String, User> getAllUsers();

    public User getUserById(String id);

    public User createUser(User user, String userId, Seat seat);

    public void deleteUser(String id);

}
