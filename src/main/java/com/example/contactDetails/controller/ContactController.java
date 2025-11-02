package com.example.contactDetails.controller;

import com.example.contactDetails.entity.Contact;
import com.example.contactDetails.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/contacts")
public class ContactController {
    // Create a logger instance for this class
    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @PostMapping
    public Contact createContact(@RequestBody Contact contact) {
        logger.info("REST request to create a new contact");
        return contactService.create(contact);
    }

    @GetMapping("/{id}")
    public Optional<Contact> getContact(@PathVariable Long id) {
        logger.info("REST request to get contact with ID: {}", id);
        return contactService.getById(id);
    }

    @GetMapping
    public ResponseEntity<Page<Contact>> getAllContacts(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        logger.info("REST request to get all contacts - page: {}, size: {}", pageNo, pageSize);
        Page<Contact> contacts = contactService.getAll(pageNo, pageSize);
        return ResponseEntity.ok(contacts);
    }

    @PutMapping("/{id}")
    public Contact updateContact(@PathVariable Long id, @RequestBody Contact contact) {
        logger.info("REST request to update contact with ID: {}", id);
        return contactService.update(id, contact);
    }

    @DeleteMapping("/{id}")
    public void deleteContact(@PathVariable Long id) {
        logger.info("REST request to delete contact with ID: {}", id);
        contactService.delete(id);
    }
}
