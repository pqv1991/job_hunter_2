package com.vietpq.JobHunter.util.validator;

import com.vietpq.JobHunter.exception.NotNullException;
import com.vietpq.JobHunter.exception.message.CompanyMessage;

public class  CompanyValidator {
    public static void notNullName(String name){
        if (name == null || name.trim().isEmpty()){
            throw new NotNullException(CompanyMessage.NAME_REQUIRED);
        }
    }
}
