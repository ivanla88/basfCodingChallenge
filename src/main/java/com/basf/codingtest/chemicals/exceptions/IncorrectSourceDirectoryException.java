package com.basf.codingtest.chemicals.exceptions;

public class IncorrectSourceDirectoryException extends RuntimeException{

    public IncorrectSourceDirectoryException(String message) {
        super(message);
    }

    public IncorrectSourceDirectoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
