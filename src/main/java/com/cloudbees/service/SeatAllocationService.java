package com.cloudbees.service;

import com.cloudbees.enums.Section;
import com.cloudbees.exceptions.ValidationException;
import com.cloudbees.model.Receipt;
import com.cloudbees.model.Seat;

import java.util.List;

public interface SeatAllocationService {

    public boolean isSeatValid(Section section, int seatNumber) throws ValidationException;

    public String allocateSeat(Receipt receipt);

    public List<Seat> getAllSeatsBySection(Section section);

    public void unReserveSeat(int seatNumber, Section section);

    public Seat getSeatBySectionAndID(Section section, int seatNumber);
}
