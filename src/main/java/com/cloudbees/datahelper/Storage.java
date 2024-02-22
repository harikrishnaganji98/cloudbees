package com.cloudbees.datahelper;

import com.cloudbees.model.Receipt;
import com.cloudbees.model.Seat;
import com.cloudbees.model.User;

import java.util.HashMap;

public class Storage {
    public static HashMap<String, Receipt> receipts = new HashMap<>();
    public static HashMap<Integer, Seat> seats = new HashMap<>();
    public static HashMap<String, User> users = new HashMap<>();

    public HashMap<String, Receipt> getReceipts() {
        return receipts;
    }

    public static HashMap<Integer, Seat> getSeats() {
        return seats;
    }

    public static HashMap<String, User> getUsers() {
        return users;
    }
}