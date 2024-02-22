package com.cloudbees.service;

import com.cloudbees.datahelper.DataInitializer;
import com.cloudbees.enums.SeatStatus;
import com.cloudbees.enums.Section;
import com.cloudbees.model.Seat;
import com.cloudbees.model.User;
import com.cloudbees.service.impl.UsersServiceImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class UserServiceTest {

    private UsersServiceImpl usersService = new UsersServiceImpl();
    private DataInitializer dataInitializer;

    @Before
    public void setUp() {
        dataInitializer = mock(DataInitializer.class);
    }

    @Test
    public void testCreateUser() {
        User user = new User("1", "abc", "def", "abc@gmail.com");
        Seat seat = new Seat(1, SeatStatus.NOT_RESERVED, Section.A, user);
        assertNotNull(usersService.createUser(user, "1", seat));
    }
}
