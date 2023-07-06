package com.example.chi_phone_contacts.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<ValidEmail, Set<String>> {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    @Override
    public boolean isValid(Set<String> emails, ConstraintValidatorContext context) {
        if (emails.isEmpty()){
            return false;
        }
        for (String phoneNumber: emails) {
            if(!EMAIL_PATTERN.matcher(phoneNumber).matches()){
                return false;
            }
        }
        return true;
    }
}

