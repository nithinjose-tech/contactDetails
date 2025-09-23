package com.example.contactDetails.controller;

import com.example.contactDetails.entity.Contact;
import com.example.contactDetails.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping
    public Contact createContact(@RequestBody Contact contact) {
        return contactService.create(contact);
    }

    @GetMapping("/{id}")
    public Optional<Contact> getContact(@PathVariable Long id) {
        return contactService.getById(id);
    }

    @GetMapping
    public ResponseEntity<Page<Contact>> getAllContacts(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<Contact> contacts = contactService.getAll(pageNo,pageSize);
        return ResponseEntity.ok(contacts);
    }


    @PutMapping("/{id}")
    public Contact updateContact(@PathVariable Long id, @RequestBody Contact contact) {
        return contactService.update(id, contact);
    }

    @DeleteMapping("/{id}")
    public void deleteContact(@PathVariable Long id) {
        contactService.delete(id);
    }

}
