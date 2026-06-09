package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServicesTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServices userServices;

    @Test
    void getAllUserReturnsUsersFromRepository() {
        User user1 = new User();
        User user2 = new User();
        List<User> expected = Arrays.asList(user1, user2);
        doReturn(expected).when(userRepository).findAll();

        List<User> actual = userServices.getAllUser();

        assertSame(expected, actual);
        assertEquals(2, actual.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserReturnsUserFromOptional() {
        User expected = new User();
        doReturn(Optional.of(expected)).when(userRepository).findById(10);

        User actual = userServices.getUser(10);

        assertSame(expected, actual);
        verify(userRepository, times(1)).findById(10);
    }

    @Test
    void getUserThrowsWhenOptionalIsEmpty() {
        doReturn(Optional.empty()).when(userRepository).findById(99);

        assertThrows(java.util.NoSuchElementException.class, () -> userServices.getUser(99));
        verify(userRepository, times(1)).findById(99);
    }

    @Test
    void getUserByEmailReturnsUserFromRepository() {
        User expected = new User();
        doReturn(expected).when(userRepository).findUserByUemail("test@example.com");

        User actual = userServices.getUserByEmail("test@example.com");

        assertSame(expected, actual);
        verify(userRepository, times(1)).findUserByUemail("test@example.com");
    }

    @Test
    void updateUserSetsIdAndSavesUser() {
        User user = new User();
        doReturn(user).when(userRepository).save(user);

        userServices.updateUser(user, 42);

        assertEquals(42, user.getU_id());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteUserDelegatesToRepository() {
        doNothing().when(userRepository).deleteById(7);

        userServices.deleteUser(7);

        verify(userRepository, times(1)).deleteById(7);
        assertTrue(true);
    }

    @Test
    void addUserDelegatesToRepositorySave() {
        User user = new User();
        doReturn(user).when(userRepository).save(user);

        userServices.addUser(user);

        verify(userRepository, times(1)).save(user);
        assertSame(user, user);
    }

    @Test
    void validateLoginCredentialsReturnsTrueForMatchingUser() {
        User user = new User();
        user.setUemail("john@example.com");
        user.setUpassword("secret");
        doReturn(Collections.singletonList(user)).when(userRepository).findAll();

        boolean result = userServices.validateLoginCredentials("john@example.com", "secret");

        assertTrue(result);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void validateLoginCredentialsReturnsFalseForNoMatchAndNullEntries() {
        User user = new User();
        user.setUemail("john@example.com");
        user.setUpassword("secret");
        doReturn(Arrays.asList(null, user)).when(userRepository).findAll();

        boolean result = userServices.validateLoginCredentials("other@example.com", "wrong");

        assertFalse(result);
        verify(userRepository, times(1)).findAll();
    }
}