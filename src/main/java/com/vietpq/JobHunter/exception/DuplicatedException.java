package com.vietpq.JobHunter.exception;

public class DuplicatedException extends  RuntimeException{
    public DuplicatedException(String message) {
        super(message);
    }
}
