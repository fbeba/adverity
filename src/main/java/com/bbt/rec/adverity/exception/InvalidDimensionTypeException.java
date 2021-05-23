package com.bbt.rec.adverity.exception;

public class InvalidDimensionTypeException extends RuntimeException {

    public InvalidDimensionTypeException(final String invalid) {
        super(String.format("Handling for dimension %s not available.", invalid));
    }
}
