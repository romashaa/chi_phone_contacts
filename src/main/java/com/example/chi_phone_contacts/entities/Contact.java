package com.example.chi_phone_contacts.entities;

import com.example.chi_phone_contacts.validation.ValidEmail;
import com.example.chi_phone_contacts.validation.ValidPhoneNumber;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @ElementCollection
    @ValidPhoneNumber
    private Set<String> phoneNumbers;
    @ValidEmail
    @ElementCollection
    private Set<String> emails;

    @ManyToOne
    @JsonBackReference
    private User user;
}