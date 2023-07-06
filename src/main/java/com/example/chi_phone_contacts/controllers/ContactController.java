package com.example.chi_phone_contacts.controllers;

import com.example.chi_phone_contacts.entities.Contact;
import com.example.chi_phone_contacts.services.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contacts")
public class ContactController {
    private final ContactService contactService;

//    @PostMapping
//    public Contact createContact(@RequestBody @Valid Contact contact) {
//        return contactService.createContact(contact);
//    }

    @PostMapping
    public ResponseEntity<?> createContact(@RequestBody @Valid Contact contact, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(errors);
        }

        Contact createdContact = contactService.createContact(contact);
        return ResponseEntity.ok(createdContact);
    }

    @GetMapping
    public List<Contact> all(){
        return contactService.getAllContacts();
    }
}
