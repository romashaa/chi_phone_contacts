package com.example.chi_phone_contacts.services;

import com.example.chi_phone_contacts.entities.Contact;
import com.example.chi_phone_contacts.repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;

//    public Contact createContact(Contact contact) {
//        return contactRepository.save(contact);
//    }

    public Contact createContact(Contact contact) {
        // Perform uniqueness validation
        if (!isPhoneNumbersUnique(contact.getPhoneNumbers())) {
            throw new IllegalArgumentException("Duplicate phone numbers are not allowed");
        }
        return contactRepository.save(contact);
    }


    public Contact updateContact(Long id, Contact updatedContact) {
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Contact not found with ID: " + id));

        existingContact.setName(updatedContact.getName());
        existingContact.setPhoneNumbers(updatedContact.getPhoneNumbers());

        return contactRepository.save(existingContact);
    }

    private boolean isPhoneNumbersUnique(Set<String> phoneNumbers) {
        if (phoneNumbers == null || phoneNumbers.isEmpty()) {
            return true;
        }

        Set<String> uniquePhoneNumbers = new HashSet<>(phoneNumbers);
        return phoneNumbers.size() == uniquePhoneNumbers.size();
    }


    public List<Contact> getAllContacts(){
        return contactRepository.findAll();
    }
}
