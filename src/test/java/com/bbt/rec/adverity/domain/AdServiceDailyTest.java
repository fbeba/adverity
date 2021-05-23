package com.bbt.rec.adverity.domain;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;

class AdServiceDailyTest {

    private final AdService service = new AdService();

    @BeforeEach
    void setup() {
        service.store(new AdEntity("Bing", "CampOne", now().minusDays(1), 50, 100));
        service.store(new AdEntity("Bing", "CampOne", now(), 50, 120));
        service.store(new AdEntity("Google", "CampOne", now(), 5, 100));
        service.store(new AdEntity("Google", "CampTwo", now().plusDays(1), 60, 100));
        service.store(new AdEntity("Carrot", "CampTwo", now().plusDays(1), 8, 10));
    }

    @Test
    void shouldReturnDailyImpressions() {

        var result = service.queryDailyImpressions(LocalDate.MIN, LocalDate.MAX);

        assertThat(result.getImpressions())
                .extracting("date", "impressions")
                .contains(
                        Tuple.tuple(now().minusDays(1), 100),
                        Tuple.tuple(now(), 220),
                        Tuple.tuple(now().plusDays(1), 110)
                );
    }

    @Test
    void shouldReturnDateRestrictedDailyImpressions() {

        var result = service.queryDailyImpressions(LocalDate.now(), LocalDate.now());

        assertThat(result.getImpressions())
                .extracting("date", "impressions")
                .contains(
                        Tuple.tuple(now(), 220)
                );
    }

    @Test
    void shouldReturnNoDailyImpressions() {

        var result = service.queryDailyImpressions(LocalDate.now().plusWeeks(4), LocalDate.now().plusWeeks(5));

        assertThat(result.getImpressions()).isEmpty();
    }
}