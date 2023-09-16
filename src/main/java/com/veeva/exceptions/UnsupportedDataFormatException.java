package com.veeva.exceptions;

public class UnsupportedDataFormatException extends Exception {

    public UnsupportedDataFormatException(String message) {
        super(message);
    }

    public UnsupportedDataFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
