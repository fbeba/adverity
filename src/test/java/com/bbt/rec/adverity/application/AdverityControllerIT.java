package com.bbt.rec.adverity.application;

import com.bbt.rec.adverity.exception.InvalidDimensionTypeException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AdverityControllerIT {

    @Autowired
    private AdverityController controller;

    @Test
    void findImpressionsInTimeWindow() {
        // Given
//        var request = new HttpGet( "http://localhost:8080/impressions" );

        // When
//        var response = HttpClientBuilder.create().build().execute(request);
        var response = new RestTemplate().getForObject("http://localhost:8080/impressions", String.class);

        assertThat(response).contains("\"metric\":\"IMPRESSIONS\"");
    }

    @Test
    void getCtrsForDimension() {
        var exception = assertThrows(InvalidDimensionTypeException.class, () -> controller.getCtrsForDimension("wrongDimension"));

        assertEquals("Handling for dimension wrongDimension not available.", exception.getMessage());
    }
}