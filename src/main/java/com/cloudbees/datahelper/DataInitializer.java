package com.cloudbees.datahelper;

import com.cloudbees.model.Seat;
import com.cloudbees.model.User;
import com.cloudbees.enums.SeatStatus;
import com.cloudbees.enums.Section;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class DataInitializer implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        generateSeatsData();
    }

    public void generateSeatsData() {
        for (int i = 0; i < 10; i ++) {
            Seat seat = new Seat(i + 1, SeatStatus.NOT_RESERVED, Section.A, null);
            Storage.seats.put(i + 1, seat);
        }

        for (int i = 11; i < 21; i ++) {
            Seat seat = new Seat(i, SeatStatus.NOT_RESERVED, Section.B, null);
            Storage.seats.put(i, seat);
        }
    }


    public HashMap<String, User> getAllUsers() {
        return Storage.users;
    }

    public HashMap<Integer, Seat> getAllSeats() {
        return Storage.getSeats();
    }
}
