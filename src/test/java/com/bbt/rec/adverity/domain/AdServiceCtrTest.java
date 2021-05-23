package com.bbt.rec.adverity.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.bbt.rec.adverity.domain.Dimension.DimensionType.CAMPAIGN;
import static com.bbt.rec.adverity.domain.Dimension.DimensionType.DATASOURCE;
import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

class AdServiceCtrTest {

    private final AdService service = new AdService();

    @BeforeEach
    void setup() {
        service.store(List.of(
                new AdEntity("Bing", "CampOne", now().minusDays(1), 50, 100),
                new AdEntity("Google", "CampOne", now(), 5, 100),
                new AdEntity("Google", "CampTwo", now().plusDays(1), 60, 100),
                new AdEntity("Carrot", "CampTwo", now().plusDays(1), 8, 100)));
    }

    @Test
    void shouldReturnCtrByDatasource() {
        var datasource = Dimension.builder().ofType(DATASOURCE).build();

        var result = service.queryCtr(datasource);

        assertThat(result.getCtrs())
                .extracting("datasource", "ctr")
                .containsExactlyInAnyOrder(
                        tuple("Google", 32.5),
                        tuple("Carrot", 8.0),
                        tuple("Bing", 50.0));
    }

    @Test
    void shouldReturnCtrByCampaign() {
        var datasource = Dimension.builder().ofType(CAMPAIGN).build();

        var result = service.queryCtr(datasource);

        assertThat(result.getCtrs())
                .extracting("campaign", "ctr")
                .containsExactlyInAnyOrder(
                        tuple("CampOne", 27.5),
                        tuple("CampTwo", 34.0));
    }
}
