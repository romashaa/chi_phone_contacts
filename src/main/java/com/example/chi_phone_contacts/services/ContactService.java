package com.example.chi_phone_contacts.services;

import com.example.chi_phone_contacts.entities.Contact;;
import com.example.chi_phone_contacts.entities.User;
import com.example.chi_phone_contacts.repositories.ContactRepository;
import com.example.chi_phone_contacts.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;
    private final UserRepository userRepository;


    public Contact createContact(Contact contact, Long userId) throws DuplicateContactException {
        User user = userRepository.findById(userId).get();
        for (String phoneNumber : contact.getPhoneNumbers()) {
            if (contactRepository.existsByPhoneNumbersAndUser(phoneNumber, user)) {
                throw new DuplicateContactException("Phone number already exists: " + phoneNumber);
            }
        }
        for (String email : contact.getEmails()) {
            if (contactRepository.existsByEmailsAndUser(email, user)) {
                throw new DuplicateContactException("Email already exists: " + email);
            }
        }
        if (contactRepository.existsByNameAndUser(contact.getName(), user)) {
            throw new DuplicateContactException("Contact name already exists: " + contact.getName());
        }
        contact.setUser(user);
        return contactRepository.save(contact);
    }


    public Contact updateContact(Long id, Contact updatedContact) {
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Contact not found with ID: " + id));
        existingContact.setName(updatedContact.getName());
        existingContact.setPhoneNumbers(updatedContact.getPhoneNumbers());
        return contactRepository.save(existingContact);
    }

    public List<Contact> getAllContactsOfUser(Long id){
    User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
        return contactRepository.findByUser(user);
    }

    public void deleteContact(Long id){
        Contact toBeDeleted = contactRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Contact not found with ID: " + id));
        contactRepository.delete(toBeDeleted);
    }
}

class DuplicateContactException extends RuntimeException {
    public DuplicateContactException(String message) {
        super(message);
    }
}