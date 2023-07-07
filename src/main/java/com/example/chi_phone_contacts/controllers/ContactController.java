package com.example.chi_phone_contacts.controllers;

import com.example.chi_phone_contacts.entities.Contact;
import com.example.chi_phone_contacts.services.ContactService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contacts")
public class ContactController {
    private final ContactService contactService;


    //3.     Add new contact
    @PostMapping
    public ResponseEntity<?> createContact(@RequestBody @Valid Contact contact, @RequestParam Long userId) {
        Contact createdContact = contactService.createContact(contact, userId);
        return ResponseEntity.ok(createdContact);
    }

    //4.     Edit existing contact
    @PutMapping("/{id}")
    public Contact updateContact(@PathVariable Long id, @RequestBody Contact updatedContact) {
        return contactService.updateContact(id, updatedContact);
    }

    //5.     Delete existing contact
    @DeleteMapping("/{id}")
    public void deleteContact(@PathVariable Long id){
        contactService.deleteContact(id);
    }

    //6.     Get list of existing user's contacts
    @GetMapping
    public List<Contact> getUserContacts(@RequestParam Long userId){
        return contactService.getAllContactsOfUser(userId);
    }



}
