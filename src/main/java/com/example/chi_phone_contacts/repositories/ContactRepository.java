package com.example.chi_phone_contacts.repositories;

import com.example.chi_phone_contacts.entities.Contact;
import com.example.chi_phone_contacts.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Boolean existsByPhoneNumbersAndUser(String phoneNumber, User user);
    Boolean existsByEmailsAndUser(String email, User user);
    Boolean existsByNameAndUser(String name, User user);
    List<Contact> findByUser(User user);
}
