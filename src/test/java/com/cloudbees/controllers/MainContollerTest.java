package com.cloudbees.controllers;

import com.cloudbees.controller.MainController;
import com.cloudbees.enums.SeatStatus;
import com.cloudbees.enums.Section;
import com.cloudbees.model.Receipt;
import com.cloudbees.model.Seat;
import com.cloudbees.model.User;
import com.cloudbees.service.ReceiptService;
import com.cloudbees.service.SeatAllocationService;
import com.cloudbees.service.UsersService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MainContollerTest {
    private SeatAllocationService seatAllocationService;
    private UsersService usersService;
    private ReceiptService receiptService;
    private MainController mainController = new MainController();

    @Before
    public void setUp() {
        seatAllocationService = mock(SeatAllocationService.class);
        usersService = mock(UsersService.class);
        receiptService = mock(ReceiptService.class);
        mainController.setReceiptService(receiptService);
        mainController.setUsersService(usersService);
        mainController.setSeatAllocationService(seatAllocationService);
    }

    @Test
    public void testGetReceipt() {
        Receipt receipt = new Receipt();
        User user = new User("1", "abc", "def", "abc@gmail.com");
        Seat seat = new Seat(1, SeatStatus.NOT_RESERVED, Section.A, user);
        receipt.setId("12345");
        receipt.setSeat(seat);
        receipt.setUser(user);
        when(receiptService.getReceiptById("12345")).thenReturn(receipt);
        assertNotNull(mainController.getReceipt("12345"));
    }
}
