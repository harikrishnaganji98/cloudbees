package com.cloudbees.controller;


import com.cloudbees.enums.ReceiptStatus;
import com.cloudbees.enums.Section;
import com.cloudbees.exceptions.ValidationException;
import com.cloudbees.model.Receipt;
import com.cloudbees.model.Seat;
import com.cloudbees.model.User;
import com.cloudbees.service.ReceiptService;
import com.cloudbees.service.SeatAllocationService;
import com.cloudbees.service.UsersService;
import dto.ReceiptDto;
import dto.SeatDataBySectionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class MainController {

    private SeatAllocationService seatAllocationService;
    private UsersService usersService;
    private ReceiptService receiptService;

    @RequestMapping(method = RequestMethod.GET, value = "/receipt/{id}")
    public ReceiptDto getReceipt(@PathVariable String id) {
        return convertToReceiptDto(receiptService.getReceiptById(id));
    }


    @RequestMapping(method = RequestMethod.GET, value = "/section/{section}")
    public List<SeatDataBySectionDto> getDetailsBySection(@PathVariable String section) {
        return convertToSeatAllocationDto(seatAllocationService.getAllSeatsBySection(Section.valueOf(section)));
    }


    @RequestMapping(method = RequestMethod.POST, value = "/reservation")
    public Map<String, String> reserveSeat(@RequestBody Receipt payLoad) throws Exception {

        User user = payLoad.getUser();
        if (user == null) {
            throw new ValidationException("User data shouldn't be empty");
        }

        Seat seat = payLoad.getSeat();
        if (seat == null) {
            throw new ValidationException("Seat information shouldn't be empty");
        }

        if (!seatAllocationService.isSeatValid(payLoad.getSeat().getSection(), payLoad.getSeat().getSeatNumber())) {
            throw new ValidationException("Seat is Reserved");
        }

        return Map.of("receiptId", seatAllocationService.allocateSeat(payLoad));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/user/{id}")
    public void deleteUser(@PathVariable String id) throws ValidationException {
        User user = usersService.getUserById(id);
        if (user == null) {
            throw new ValidationException("Couldn't find user by Id:" + id);
        }
        usersService.deleteUser(id);
        receiptService.getReceiptByUserId(id).get().setStatus(ReceiptStatus.CANCELLED);
        seatAllocationService.unReserveSeat(user.getSeat().getSeatNumber(), user.getSeat().getSection());
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/user/{id}")
    public Map<String, String> modifyUserSeat(@PathVariable String id, @RequestBody Receipt payLoad) throws ValidationException {
        User user = usersService.getUserById(id);
        Seat oldSeat = user.getSeat();
        if (user == null) {
            throw new ValidationException("Couldn't find user by Id: " + id);
        }

        Seat seat = payLoad.getSeat();
        if (seat == null) {
            throw new ValidationException("Seat information shouldn't be empty");
        }
        seat = seatAllocationService.getSeatBySectionAndID(seat.getSection(), seat.getSeatNumber());
        if (!seatAllocationService.isSeatValid(payLoad.getSeat().getSection(), payLoad.getSeat().getSeatNumber())) {
            throw new ValidationException("Seat is Reserved");
        }
        Receipt oldReceipt = receiptService.getReceiptByUserId(id).get();
        oldReceipt.setStatus(ReceiptStatus.CANCELLED);
        seatAllocationService.unReserveSeat(oldSeat.getSeatNumber(), oldSeat.getSection());
        return Map.of("receiptId", seatAllocationService.updateSeat(user, seat, payLoad));
    }

    private List<SeatDataBySectionDto> convertToSeatAllocationDto(List<Seat> allSeatsBySection) {
        List<SeatDataBySectionDto> dtos = new ArrayList<>();
        for (Seat seat : allSeatsBySection) {
            SeatDataBySectionDto dto = new SeatDataBySectionDto(seat.getSeatNumber(), seat.getSeatStatus(), seat.getSection());
            dtos.add(dto);
        }
        return dtos;
    }

    private ReceiptDto convertToReceiptDto(Receipt receipt) {
        ReceiptDto dto = new ReceiptDto();
        dto.setId(receipt.getId());
        dto.setSeatNumber(receipt.getSeat().getSeatNumber());
        dto.setSeatStatus(receipt.getSeat().getSeatStatus());
        dto.setFrom(receipt.getFrom());
        dto.setTo(receipt.getTo());
        dto.setFirstName(receipt.getUser().getFirstName());
        dto.setLastName(receipt.getUser().getLastName());
        dto.setEmail(receipt.getUser().getEmail());
        dto.setPricePaid(receipt.getPricePaid());
        dto.setSection(receipt.getSeat().getSection());
        dto.setUserId(receipt.getUser().getId());
        dto.setReceiptStatus(receipt.getStatus());
        return dto;
    }

    @Autowired
    public void setSeatAllocationService(SeatAllocationService seatAllocationService) {
        this.seatAllocationService = seatAllocationService;
    }

    @Autowired
    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    @Autowired
    public void setReceiptService(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }
}
