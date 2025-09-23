package com.example.contactDetails.service;

import com.example.contactDetails.entity.Contact;
import com.example.contactDetails.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    public Contact create(Contact contact) {
        return contactRepository.save(contact);
    }

    public Optional<Contact> getById(Long id) {
        return contactRepository.findById(id);
    }

    public Page<Contact> getAll(int pageNo,int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        return contactRepository.findAll(pageable);
    }

    public Contact update(Long id, Contact updatedContact) {
        return contactRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedContact.getName());
                    existing.setPhone(updatedContact.getPhone());
                    existing.setEmail(updatedContact.getEmail());
                    return contactRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Contact not found"));
    }

    public void delete(Long id) {
        contactRepository.deleteById(id);
    }
}
