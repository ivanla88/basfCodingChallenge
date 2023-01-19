package com.basf.codingtest.chemicals.exceptions;

public class IncorrectInputFileException extends RuntimeException{

    public IncorrectInputFileException(String message) {
        super(message);
    }

    public IncorrectInputFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
