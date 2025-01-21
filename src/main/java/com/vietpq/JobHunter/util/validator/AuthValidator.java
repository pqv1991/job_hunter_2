package com.vietpq.JobHunter.util.validator;

import com.vietpq.JobHunter.dto.login.LoginDTO;
import com.vietpq.JobHunter.exception.InvalidException;
import com.vietpq.JobHunter.exception.NotNullException;
import com.vietpq.JobHunter.exception.message.AuthMessage;

public class AuthValidator {
    private static int MIN_LENGTH_PASSWORD = 8;
    public static  void notNullEmail(String email) throws NotNullException{
    if(email == null || email.trim().isEmpty())
        throw  new NotNullException(AuthMessage.EMAIL_REQUIRED);
    }

    public static  void notNullPassword(String password) throws NotNullException{
        if(password == null || password.trim().isEmpty())
            throw  new NotNullException(AuthMessage.PASSWORD_REQUIRED);
    }
    public static void inValidEmail(String email) throws InvalidException{
        if(!ValidUser.isValidEmail(email))
            throw new InvalidException(AuthMessage.EMAIL_INVALID);
    }

    public static void inValidPassword(String password) throws InvalidException{
        if(password.length()<MIN_LENGTH_PASSWORD)
            throw new InvalidException(AuthMessage.PASSWORD_INVALID);
    }

    public static void validatorLoginDTO(LoginDTO loginDTO) throws InvalidException{
        AuthValidator.notNullEmail(loginDTO.getUsername());
        AuthValidator.notNullPassword(loginDTO.getPassword());
        AuthValidator.inValidEmail(loginDTO.getUsername());
        AuthValidator.inValidPassword(loginDTO.getPassword());
    }


}
