package com.muschart.exception;

public class ValidationException extends Exception {

    private static final long serialVersionUID = -1313677835576506079L;

    public ValidationException(String message) {
        super(message);
    }

}