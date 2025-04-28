package com.banking.auth.service;

import com.banking.auth.model.User;
import com.banking.auth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
    }

    @Test
    public void testRegisterUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        User registeredUser = authService.registerUser(user);
        assertEquals("testuser", registeredUser.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testFindUserByUsername() {
        when(userRepository.findByUsername("testuser")).thenReturn(user);
        User foundUser = authService.findUserByUsername("testuser");
        assertEquals("testuser", foundUser.getUsername());
        verify(userRepository, times(1)).findByUsername("testuser");
    }
}