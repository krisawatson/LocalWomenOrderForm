/*
 * Kris Watson Copyright (c) 2017.
 */

package com.kricko.service;

import com.kricko.domain.User;
import com.kricko.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepositoryMock;

    private List<User> dummyList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        dummyList = getUserList();
        when(userRepositoryMock.findAll()).thenReturn(dummyList);
    }

    @Test
    public void getUsers() throws Exception {
        List<User> userList = userService.getUsers();

        assertEquals(userList, dummyList);

        // Confirm getUsers method was called
        verify(userRepositoryMock).findAll();
    }

    @Test
    public void getRoles() throws Exception {
    }

    @Test
    public void createUser() throws Exception {
    }

    @Test
    public void updateUser() throws Exception {
    }

    @Test
    public void getUserByUsername() throws Exception {
    }

    private List<User> getUserList() {
        return Arrays.asList(new User(), new User());
    }
}