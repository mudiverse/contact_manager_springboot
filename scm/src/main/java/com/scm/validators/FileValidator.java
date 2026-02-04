package com.scm.validators;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile,MultipartFile> {

    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024; // 2 MB 
    
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        //file null naghi honi chaiye

        if(file==null || file.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File is required").addConstraintViolation();
            return false;
        }
        if(file.getSize()>MAX_FILE_SIZE){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File size must be less than 2 MB").addConstraintViolation();
            return false;
        }

        return true;
    }

}
