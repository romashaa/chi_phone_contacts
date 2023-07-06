package com.example.chi_phone_contacts.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, Set<String>> {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("[+]{1}380[0-9]{9}");


//    @Override
//    public void initialize(ValidPhoneNumber constraintAnnotation) {
//        // Initialization logic, if needed
//    }

    @Override
    public boolean isValid(Set<String> phoneNumbers, ConstraintValidatorContext context) {
        if (phoneNumbers.isEmpty()){
            return false;
        }
        for (String phoneNumber: phoneNumbers) {
            if(!NUMBER_PATTERN.matcher(phoneNumber).matches()){
                return false;
            }
        }
        return true;
    }
}