package com.bbt.rec.adverity.exception;

import com.bbt.rec.adverity.domain.Dimension;

public class InvalidDimensionTypeException extends RuntimeException {

    public InvalidDimensionTypeException(final Dimension.DimensionType s) {
        super(String.format("Handling for dimension %s not available.", s));
    }
}
