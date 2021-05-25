package com.bbt.rec.adverity.application;

import com.bbt.rec.adverity.exception.InvalidDimensionTypeException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AdverityControllerTest {

    @Autowired
    private AdverityController controller;

    @Test
    void getCtrsForDimension() {
        var exception = assertThrows(InvalidDimensionTypeException.class,
                () -> controller.getCtrsForDimension("wrongDimension"));

        assertEquals("Handling for dimension wrongDimension not available.", exception.getMessage());
    }
}
