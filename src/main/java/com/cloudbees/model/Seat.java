package com.cloudbees.model;

import com.cloudbees.enums.SeatStatus;
import com.cloudbees.enums.Section;

public class Seat {

    private int seatNumber;
    private SeatStatus seatStatus;
    private Section section;
    private User user;

    public Seat(int seatNumber, SeatStatus seatStatus, Section section, User user) {
        this.seatNumber = seatNumber;
        this.seatStatus = seatStatus;
        this.section = section;
        this.user = user;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatStatus getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
