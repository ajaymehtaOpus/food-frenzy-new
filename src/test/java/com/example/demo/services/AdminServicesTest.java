package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.example.demo.entities.Admin;
import com.example.demo.repositories.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;

@ExtendWith(MockitoExtension.class)
class AdminServicesTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminServices adminServices;

    private Admin admin1;
    private Admin admin2;

    @BeforeEach
    void setUp() {
        admin1 = new Admin();
        admin1.setAdminId(1);
        admin1.setAdminEmail("admin1@example.com");
        admin1.setAdminPassword("secret1");

        admin2 = new Admin();
        admin2.setAdminId(2);
        admin2.setAdminEmail("admin2@example.com");
        admin2.setAdminPassword("secret2");
    }

    @Test
    void getAllReturnsAdminsFromRepository() {
        doReturn(Arrays.asList(admin1, admin2)).when(adminRepository).findAll();

        List<Admin> result = adminServices.getAll();

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getAdminId());
        verify(adminRepository, times(1)).findAll();
    }

    @Test
    void getAdminReturnsAdminFromOptional() {
        doReturn(Optional.of(admin1)).when(adminRepository).findById(1);

        Admin result = adminServices.getAdmin(1);

        assertEquals(1, result.getAdminId());
        assertEquals("admin1@example.com", result.getAdminEmail());
        verify(adminRepository, times(1)).findById(1);
    }

    @Test
    void updateSavesAdminWhenMatchingIdExists() {
        Admin updated = new Admin();
        updated.setAdminId(1);
        updated.setAdminEmail("updated@example.com");
        updated.setAdminPassword("updated");

        AdminServices spyServices = Mockito.spy(adminServices);
        doReturn(Collections.singletonList(admin1)).when(spyServices).getAll();

        spyServices.update(updated, 1);

        verify(adminRepository, times(1)).save(updated);
        verify(adminRepository, never()).deleteById(any(Integer.class));
        assertEquals(1, updated.getAdminId());
    }

    @Test
    void updateDoesNotSaveWhenNoMatchingIdExists() {
        Admin updated = new Admin();
        updated.setAdminId(99);
        updated.setAdminEmail("updated@example.com");
        updated.setAdminPassword("updated");

        AdminServices spyServices = Mockito.spy(adminServices);
        doReturn(Collections.singletonList(admin1)).when(spyServices).getAll();

        spyServices.update(updated, 99);

        verify(adminRepository, never()).save(any(Admin.class));
        assertEquals(1, admin1.getAdminId());
    }

    @Test
    void deleteDelegatesToRepository() {
        adminServices.delete(7);

        verify(adminRepository, times(1)).deleteById(7);
        assertTrue(true);
    }

    @Test
    void addAdminDelegatesToRepositorySave() {
        adminServices.addAdmin(admin2);

        verify(adminRepository, times(1)).save(admin2);
        assertEquals("admin2@example.com", admin2.getAdminEmail());
    }

    @Test
    void validateAdminCredentialsReturnsTrueForMatchingCredentials() {
        doReturn(admin1).when(adminRepository).findByAdminEmail(eq("admin1@example.com"));

        boolean result = adminServices.validateAdminCredentials("admin1@example.com", "secret1");

        assertTrue(result);
        verify(adminRepository, times(1)).findByAdminEmail("admin1@example.com");
    }

    @Test
    void validateAdminCredentialsReturnsFalseForWrongPassword() {
        doReturn(admin1).when(adminRepository).findByAdminEmail(eq("admin1@example.com"));

        boolean result = adminServices.validateAdminCredentials("admin1@example.com", "wrong");

        assertFalse(result);
        verify(adminRepository, times(1)).findByAdminEmail("admin1@example.com");
    }

    @Test
    void validateAdminCredentialsReturnsFalseWhenAdminNotFound() {
        doReturn(null).when(adminRepository).findByAdminEmail(eq("missing@example.com"));

        boolean result = adminServices.validateAdminCredentials("missing@example.com", "secret");

        assertFalse(result);
        verify(adminRepository, times(1)).findByAdminEmail("missing@example.com");
    }
}