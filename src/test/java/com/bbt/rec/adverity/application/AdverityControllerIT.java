package com.bbt.rec.adverity.application;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AdverityControllerIT {

    @Test
    void findImpressions() {
        var response = new RestTemplate().getForObject("http://localhost:8080/impressions", String.class);

        assertThat(response).contains("\"metric\":\"IMPRESSIONS\"");
    }

    @Test
    void findImpressionsInTimeWindow() {
        var response = new RestTemplate().getForObject("http://localhost:8080/impressions?from=2020-10-01&to=2021-05-21", String.class);

        assertThat(response).contains("\"metric\":\"IMPRESSIONS\"");
    }

    @Test
    void findNoClicksForInvalid() {
        var response = new RestTemplate().getForObject("http://localhost:8080/metric/clicks/non-existent", String.class);

        assertThat(response).isEqualTo("0");
    }

    @Test
    void shouldRejectRequestWithInvalidDimension() {
        var exception = assertThrows(HttpClientErrorException.class,
                () -> new RestTemplate().getForObject("http://localhost:8080/ctr?dimension=wrongOne", String.class));

        assertThat(exception.getMessage()).isEqualTo("400 : [Invalid dimension specified.]");
    }

    @Test
    void shouldRequestWithInvalidMetric() {
        var result = new RestTemplate().getForObject("http://localhost:8080/metric/clicks/Google Ads", String.class);

        assertThat(result).isEqualTo("170");
    }

    @Test
    void shouldRejectRequestWithInvalidMetric() {
        var exception = assertThrows(HttpClientErrorException.class,
                () -> new RestTemplate().getForObject("http://localhost:8080/metric/a/b", String.class));

        assertThat(exception.getMessage()).isEqualTo("400 : [Invalid parameter specified.]");
    }
}
