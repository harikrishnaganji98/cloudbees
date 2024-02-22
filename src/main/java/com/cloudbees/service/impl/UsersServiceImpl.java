package com.cloudbees.service.impl;

import com.cloudbees.datahelper.DataInitializer;
import com.cloudbees.datahelper.Storage;
import com.cloudbees.model.Seat;
import com.cloudbees.model.User;
import com.cloudbees.service.UsersService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UsersServiceImpl implements UsersService {
    private DataInitializer dataInitializer;
    Logger logger = LogManager.getLogger(UsersServiceImpl.class);

    @Override
    public HashMap<String, User> getAllUsers() {
        return dataInitializer.getAllUsers();
    }

    @Override
    public User getUserById(String id) {
        return dataInitializer.getAllUsers().get(id);
    }

    @Override
    public User createUser(User user, String userId, Seat seat) {
        logger.info("Created user with id: " + userId);
        User newUser = new User(userId, user.getFirstName(),
                user.getLastName(), user.getEmail());
        newUser.setSeat(seat);
        Storage.getUsers().put(userId, newUser);
        return newUser;
    }

    @Override
    public void deleteUser(String id) {
        dataInitializer.getAllUsers().remove(id);
    }

    @Autowired
    public void setDataInitializer(DataInitializer dataInitializer) {
        this.dataInitializer = dataInitializer;
    }
}
