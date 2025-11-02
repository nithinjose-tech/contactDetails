package com.example.contactDetails.service;

import com.example.contactDetails.entity.Contact;
import com.example.contactDetails.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    // Create a logger instance for this class
    private static final Logger logger = LoggerFactory.getLogger(ContactService.class);
    
    @Autowired
    private ContactRepository contactRepository;

    public Contact create(Contact contact) {
        logger.info("Creating new contact: {}", contact.getName());
        Contact savedContact = contactRepository.save(contact);
        logger.debug("Contact created with ID: {}", savedContact.getId());
        return savedContact;
    }

    public Optional<Contact> getById(Long id) {
        logger.info("Fetching contact with ID: {}", id);
        Optional<Contact> contact = contactRepository.findById(id);
        if (contact.isPresent()) {
            logger.debug("Contact found: {}", contact.get().getName());
        } else {
            logger.warn("Contact not found with ID: {}", id);
        }
        return contact;
    }

    public Page<Contact> getAll(int pageNo, int pageSize) {
        logger.info("Fetching all contacts - page: {}, size: {}", pageNo, pageSize);
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Contact> contacts = contactRepository.findAll(pageable);
        logger.debug("Found {} contacts", contacts.getTotalElements());
        return contacts;
    }

    public Contact update(Long id, Contact updatedContact) {
        logger.info("Updating contact with ID: {}", id);
        return contactRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedContact.getName());
                    existing.setPhone(updatedContact.getPhone());
                    existing.setEmail(updatedContact.getEmail());
                    logger.debug("Contact updated: {}", existing.getName());
                    return contactRepository.save(existing);
                })
                .orElseThrow(() -> {
                    logger.error("Failed to update - contact not found with ID: {}", id);
                    return new RuntimeException("Contact not found");
                });
    }

    public void delete(Long id) {
        logger.info("Deleting contact with ID: {}", id);
        contactRepository.deleteById(id);
        logger.debug("Contact deleted with ID: {}", id);
    }
}
