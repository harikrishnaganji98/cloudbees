package com.cloudbees.service.impl;

import com.cloudbees.datahelper.DataInitializer;
import com.cloudbees.enums.ReceiptStatus;
import com.cloudbees.enums.SeatStatus;
import com.cloudbees.enums.Section;
import com.cloudbees.exceptions.ValidationException;
import com.cloudbees.model.Receipt;
import com.cloudbees.model.Seat;
import com.cloudbees.model.User;
import com.cloudbees.service.ReceiptService;
import com.cloudbees.service.SeatAllocationService;
import com.cloudbees.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SeatAllocationServiceImpl implements SeatAllocationService {

    private DataInitializer dataInitializer;
    private ReceiptService receiptService;
    private UsersService usersService;


    @Override
    public boolean isSeatValid(Section section, int seatNumber) throws ValidationException {
        Seat seat = getSeatBySectionAndID(section, seatNumber);
        if (seat == null) {
            throw new ValidationException("Invalid seat");
        }
        if (seat != null && seat.getSeatStatus().equals(SeatStatus.NOT_RESERVED)) {
            return true;
        }
        return false;
    }

    @Override
    public String allocateSeat(Receipt receipt) {
        String userId = String.valueOf(System.currentTimeMillis());
        Seat seat = getSeatBySectionAndID(receipt.getSeat().getSection(), receipt.getSeat().getSeatNumber());
        User user = usersService.createUser(receipt.getUser(), userId, seat);
        seat.setUser(user);
        seat.setSeatStatus(SeatStatus.RESERVED);
        String receiptId = String.valueOf(UUID.randomUUID());
        receipt.setId(receiptId);
        receipt.setSeat(seat);
        receipt.setUser(user);
        receipt.setStatus(ReceiptStatus.SUCCESS);
        receiptService.saveReceipt(receipt);
        return receiptId;
    }

    @Override
    public String updateSeat(User user, Seat seat, Receipt receipt) {
        user.setSeat(seat);
        seat.setSeatStatus(SeatStatus.RESERVED);
        String receiptId = String.valueOf(UUID.randomUUID());
        receipt.setId(receiptId);
        receipt.setSeat(seat);
        receipt.setUser(user);
        receipt.setStatus(ReceiptStatus.SUCCESS);
        receiptService.saveReceipt(receipt);
        seat.setUser(user);
        return receiptId;
    }

    @Override
    public List<Seat> getAllSeatsBySection(Section section) {
        return dataInitializer.getAllSeats().values().stream().
                filter(key -> key.getSection().equals(section))
                .collect(Collectors.toList());
    }

    @Override
    public void unReserveSeat(int seatNumber, Section section) {
        Seat seat = getSeatBySectionAndID(section, seatNumber);
        seat.setUser(null);
        seat.setSeatStatus(SeatStatus.NOT_RESERVED);
    }

    @Override
    public Seat getSeatBySectionAndID(Section section, int seatNumber) {
        if (dataInitializer.getAllSeats().get(seatNumber) != null &&
                dataInitializer.getAllSeats().get(seatNumber).getSection().equals(section)) {
            return dataInitializer.getAllSeats().get(seatNumber);
        }
        return null;
    }

    @Autowired
    public void setDataInitializer(DataInitializer dataInitializer) {
        this.dataInitializer = dataInitializer;
    }

    @Autowired
    public void setReceiptService(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @Autowired
    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }
}
