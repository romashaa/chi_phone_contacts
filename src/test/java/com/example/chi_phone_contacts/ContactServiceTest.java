package com.example.chi_phone_contacts;

import com.example.chi_phone_contacts.entities.Contact;
import com.example.chi_phone_contacts.entities.User;
import com.example.chi_phone_contacts.repositories.ContactRepository;
import com.example.chi_phone_contacts.repositories.UserRepository;
import com.example.chi_phone_contacts.services.ContactService;
import com.example.chi_phone_contacts.services.DuplicateContactException;
//import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.Optional;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class ContactServiceTest {
    @Mock
    private ContactRepository contactRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ContactService contactService;

    @Test
    public void testCreateContact_Success() throws DuplicateContactException {
        // Arrange
        Long userId = 1L;
        User user = new User(userId, "Masha", "abcd");
        Contact contact = new Contact();
        contact.setName("Mum");
        contact.setPhoneNumbers(Set.of("+380111111111"));
        contact.setEmails(Set.of("mum@example.com"));
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(contactRepository.existsByPhoneNumbersAndUser(Mockito.anyString(), Mockito.any(User.class))).thenReturn(false);
        Mockito.when(contactRepository.existsByEmailsAndUser(Mockito.anyString(), Mockito.any(User.class))).thenReturn(false);
        Mockito.when(contactRepository.existsByNameAndUser(Mockito.anyString(), Mockito.any(User.class))).thenReturn(false);
        Mockito.when(contactRepository.save(Mockito.any(Contact.class))).thenReturn(contact);

        // Act
        Contact createdContact = contactService.createContact(contact, userId);

        // Assert
        Assert.assertEquals("Mum", createdContact.getName());
        Assert.assertEquals(user, createdContact.getUser());
        Assert.assertEquals(1, createdContact.getPhoneNumbers().size());
        Assert.assertEquals(1, createdContact.getEmails().size());

        Mockito.verify(contactRepository, Mockito.times(1)).save(Mockito.any(Contact.class));
    }

    @Test(expected = DuplicateContactException.class)
    public void testCreateContact_DuplicatePhoneNumber() throws DuplicateContactException {
        Long userId = 1L;
        User user = new User(userId, "Masha", "abcd");
        userRepository.save(user);
        Contact contact = new Contact();
        contact.setName("Dad");
        contact.setPhoneNumbers(Set.of("+380111111111"));
        contact.setEmails(Set.of("dad@example.com"));
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(contactRepository.existsByPhoneNumbersAndUser(Mockito.anyString(), Mockito.any(User.class))).thenReturn(true);
        contactService.createContact(contact, userId);
    }

    @Test
    public void testUpdateContact_Success() {
        Long contactId = 1L;
        Contact existingContact = new Contact();
        existingContact.setId(contactId);
        existingContact.setName("Sister");
        existingContact.setPhoneNumbers(Set.of("+380111111111"));

        Contact updatedContact = new Contact();
        updatedContact.setId(contactId);
        updatedContact.setName("Sister Ann");
        updatedContact.setPhoneNumbers(Set.of("+380222222222"));

        Mockito.when(contactRepository.findById(contactId)).thenReturn(Optional.of(existingContact));
        Mockito.when(contactRepository.save(Mockito.any(Contact.class))).thenReturn(updatedContact);

        Contact result = contactService.updateContact(contactId, updatedContact);

        Assert.assertEquals(updatedContact.getName(), result.getName());
        Assert.assertEquals(updatedContact.getPhoneNumbers(), result.getPhoneNumbers());

        Mockito.verify(contactRepository, Mockito.times(1)).save(Mockito.any(Contact.class));
    }
}
