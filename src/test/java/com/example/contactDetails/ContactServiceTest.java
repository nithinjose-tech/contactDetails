package com.example.contactDetails;

import com.example.contactDetails.entity.Contact;
import com.example.contactDetails.repository.ContactRepository;
import com.example.contactDetails.service.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactService contactService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateContact() {
        Contact contact = new Contact("Makku", "1234567890", "af@gmail.com");
        contact.setId(1L);

        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        Contact created = contactService.create(contact);

        assertNotNull(created);
        assertEquals(1L, created.getId());
        assertEquals("Makku", created.getName());

        verify(contactRepository, times(1)).save(contact);
    }

    @Test
    void testGetById_Found() {
        Contact contact = new Contact("Vishnu", "1234567890", "af@gmail.com");
        contact.setId(1L);

        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));

        Optional<Contact> result = contactService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals("Vishnu", result.get().getName());
        verify(contactRepository, times(1)).findById(1L);
    }

    @Test
    void testGetById_NotFound() {
        when(contactRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Contact> result = contactService.getById(99L);

        assertFalse(result.isPresent());
        verify(contactRepository, times(1)).findById(99L);
    }

    @Test
    void testGetAllContacts() {
        Contact contact1 = new Contact("Raj", "1234567890", "af@gmail.com");
        contact1.setId(2L);
        Contact contact2 = new Contact("Kushal", "1234567890", "af@gmail.com");
        contact2.setId(3L);

        Page<Contact> page = new PageImpl<>(Arrays.asList(contact1, contact2));

        when(contactRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);

        Page<Contact> result = contactService.getAll(0, 10);

        assertEquals(2, result.getContent().size());
        assertEquals("Raj", result.getContent().get(0).getName());

        verify(contactRepository, times(1)).findAll(PageRequest.of(0, 10));
    }

    @Test
    void testUpdateContact() {
        Contact existing = new Contact("Old", "1111111111", "old@gmail.com");
        existing.setId(1L);

        Contact updated = new Contact("Updated", "9876543210", "new@gmail.com");
        updated.setId(1L);

        when(contactRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(contactRepository.save(any(Contact.class))).thenReturn(updated);

        Contact result = contactService.update(1L, updated);

        assertEquals("Updated", result.getName());
        assertEquals("9876543210", result.getPhone());
        assertEquals("new@gmail.com", result.getEmail());

        verify(contactRepository, times(1)).findById(1L);
        verify(contactRepository, times(1)).save(existing);
    }

    @Test
    void testDeleteContact() {
        doNothing().when(contactRepository).deleteById(1L);

        contactService.delete(1L);

        verify(contactRepository, times(1)).deleteById(1L);
    }
}
