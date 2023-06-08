package com.example.Tuan2.validator;

import com.example.Tuan2.entity.User;
import com.example.Tuan2.repository.IUserRepository;
import com.example.Tuan2.validator.annotation.ValidUserId;
import com.example.Tuan2.validator.annotation.ValidUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidUserIdValidator implements ConstraintValidator<ValidUserId, User> {
    @Override
    public boolean isValid(User user, ConstraintValidatorContext context){
        if(user ==null)
            return true;
        return user.getId() !=null;
    }
}
