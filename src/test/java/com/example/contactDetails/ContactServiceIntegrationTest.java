package com.example.contactDetails;

import com.example.contactDetails.entity.Contact;
import com.example.contactDetails.repository.ContactRepository;
import com.example.contactDetails.service.ContactService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("integration")
public class ContactServiceIntegrationTest {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactRepository contactRepository;

    @Test
    void testGetAllContacts_UsesExistingData() {
        List<Contact> contacts = contactService.getAll(0, 10).getContent();

        assertNotNull(contacts);
        assertTrue(contacts.size() >= 5); // because you already have 5
    }

    @Test
    void testGetContactById_FindsExisting() {
        Optional<Contact> contact = contactService.getById(1L);

        assertTrue(contact.isPresent());
        assertEquals("Vishnu", contact.get().getName());
    }

    @Test
    void testCreateContact_AddsNewContact() {
        Contact contact = new Contact("NewUser", "5555555555", "newuser@example.com");

        Contact saved = contactService.create(contact);

        assertNotNull(saved.getId());
        assertEquals("NewUser", saved.getName());
    }

    @Test
    void testUpdateContact_UpdatesExisting() {
        Contact contact = new Contact("TempName", "44444", "temp@example.com");
        Contact saved = contactService.create(contact);

        Contact updateData = new Contact("UpdatedName", "99999", "updated@example.com");
        Contact updated = contactService.update(saved.getId(), updateData);

        assertEquals("UpdatedName", updated.getName());
        assertEquals("99999", updated.getPhone());
    }

    @Test
    void testDeleteContact_RemovesContact() {
        Contact contact = new Contact("DeleteMe", "11111", "deleteme@example.com");
        Contact saved = contactService.create(contact);

        contactService.delete(saved.getId());

        Optional<Contact> found = contactService.getById(saved.getId());
        assertTrue(found.isEmpty());
    }
}
