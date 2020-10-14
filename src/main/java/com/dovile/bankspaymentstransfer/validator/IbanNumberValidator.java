package com.dovile.bankspaymentstransfer.validator;

import com.dovile.bankspaymentstransfer.validator.checkiban_biccodes.CheckIbanValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IbanNumberValidator implements ConstraintValidator<IbanNumber, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return new CheckIbanValidation().isValid(value);
    }
}
