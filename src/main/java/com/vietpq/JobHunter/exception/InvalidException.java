package com.vietpq.JobHunter.exception;

public class InvalidException extends  Exception{
    public InvalidException(String message) {
        super(message);
    }

    public static class NotFoundException {
    }
}
