package com.vietpq.JobHunter.util.validator;

import com.vietpq.JobHunter.dto.login.LoginDTO;
import com.vietpq.JobHunter.entity.User;
import com.vietpq.JobHunter.exception.InvalidEmailException;
import com.vietpq.JobHunter.exception.InvalidPasswordException;
import com.vietpq.JobHunter.exception.NotNullException;
import com.vietpq.JobHunter.exception.message.AuthMessage;

import java.util.regex.Pattern;

public class AuthValidator {
    private static int MIN_LENGTH_PASSWORD = 8;
    public final static Pattern EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+");
    public final static Pattern PHONE_PATTERN = Pattern.compile( "0(3[2-9]|5[689]|7[06-9]|8[1-68-9]|9[0-46-9])[0-9]{7}");

    public static boolean isValidEmail(String email) {
       return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    public static  void ensureNotNullEmail(String email) throws NotNullException{
    if(email == null || email.trim().isEmpty())
        throw  new NotNullException(AuthMessage.EMAIL_REQUIRED);
    }

    public static  void ensurenotNullPassword(String password) throws NotNullException{
        if(password == null || password.trim().isEmpty())
            throw  new NotNullException(AuthMessage.PASSWORD_REQUIRED);
    }
    public static void validateEmail(String email) throws InvalidEmailException {
        if(!isValidEmail(email))
            throw new InvalidEmailException(AuthMessage.EMAIL_INVALID);
    }

    public static void validatePassword(String password) throws InvalidPasswordException{
        if(password == null || password.length()<MIN_LENGTH_PASSWORD)
            throw new InvalidPasswordException(AuthMessage.PASSWORD_INVALID);
    }


    public static void validateLoginDTO(LoginDTO loginDTO) throws InvalidEmailException, InvalidPasswordException  {
        AuthValidator.ensureNotNullEmail(loginDTO.getUsername());
        AuthValidator.ensurenotNullPassword(loginDTO.getPassword());
        AuthValidator.validateEmail(loginDTO.getUsername());
        AuthValidator.validatePassword(loginDTO.getPassword());
    }

    public static void validateUserUpdate(User user) throws InvalidEmailException, InvalidPasswordException {
        AuthValidator.ensureNotNullEmail(user.getEmail());
        AuthValidator.ensurenotNullPassword(user.getPassword());
        AuthValidator.validateEmail(user.getEmail());
        AuthValidator.validatePassword(user.getPassword());
    }


}
