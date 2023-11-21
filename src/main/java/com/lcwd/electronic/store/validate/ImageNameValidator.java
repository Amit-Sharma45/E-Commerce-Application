package com.lcwd.electronic.store.validate;

import com.lcwd.electronic.store.exceptions.GlobalExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageNameValidator implements ConstraintValidator<ImageNameValid, String> {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        logger.info("Message from isValid : {} ", value);

        if (value.isBlank()) {
            return false;
        } else {
            return true;
        }
    }
}
