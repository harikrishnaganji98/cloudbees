package com.cloudbees.service;

import com.cloudbees.datahelper.DataInitializer;
import com.cloudbees.enums.SeatStatus;
import com.cloudbees.enums.Section;
import com.cloudbees.model.Receipt;
import com.cloudbees.model.Seat;
import com.cloudbees.model.User;
import com.cloudbees.service.impl.SeatAllocationServiceImpl;
import org.junit.Before;
import org.junit.Test;


import java.util.HashMap;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class SeatAllocationServiceTest {

    private UsersService usersService;
    private ReceiptService receiptService;
    private DataInitializer dataInitializer;
    private SeatAllocationServiceImpl seatAllocationService = new SeatAllocationServiceImpl();
    @Before
    public void setUp() {
        receiptService = mock(ReceiptService.class);
        usersService = mock(UsersService.class);
        dataInitializer = mock(DataInitializer.class);
        seatAllocationService.setReceiptService(receiptService);
        seatAllocationService.setUsersService(usersService);
        seatAllocationService.setDataInitializer(dataInitializer);
    }

    @Test
    public void testAllocateSeat() {
        Receipt receipt = new Receipt();
        User user = new User("1", "abc", "def", "abc@gmail.com");
        Seat seat = new Seat(1, SeatStatus.NOT_RESERVED, Section.A, user);
        receipt.setUser(user);
        receipt.setSeat(seat);
        HashMap<Integer, Seat> seats = new HashMap<>();
        seats.put(1, seat);
        when(dataInitializer.getAllSeats()).thenReturn(seats);
        when(usersService.createUser(user, "1", seat)).thenReturn(user);
        assertNotNull(seatAllocationService.allocateSeat(receipt));
    }

}
