package com.bbt.rec.adverity.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static com.bbt.rec.adverity.domain.Dimension.DimensionType.CAMPAIGN;
import static com.bbt.rec.adverity.domain.Dimension.DimensionType.DATASOURCE;
import static com.bbt.rec.adverity.domain.Metric.CLICKS;
import static com.bbt.rec.adverity.domain.Metric.IMPRESSIONS;
import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;


class AdServiceTotalClicksTest {

    private final AdService service = new AdService();

    @BeforeEach
    void setup() {
        service.store(List.of(
                new AdEntity("Bing", "CampOne", now().minusDays(1), 50, 70),
                new AdEntity("Google", "CampOne", now(), 5, 100),
                new AdEntity("Google", "CampTwo", now().plusDays(1), 6, 100),
                new AdEntity("Carrot", "CampTwo", now().plusDays(1), 8, 500)));
    }

    @Test
    void shouldReturnSummedClicksForDatasource() {
        var datasource = Dimension.builder().ofType(DATASOURCE).andValue("Google").build();

        var result = service.querySummarizingMetric(CLICKS, datasource, LocalDate.MIN, LocalDate.MAX);

        assertThat(result).isEqualTo(11);
    }

    @Test
    void shouldReturnSummedImpressionsForDatasource() {
        var datasource = Dimension.builder().ofType(DATASOURCE).andValue("Google").build();

        var result = service.querySummarizingMetric(IMPRESSIONS, datasource, LocalDate.MIN, LocalDate.MAX);

        assertThat(result).isEqualTo(200);
    }

    @Test
    void shouldReturnSummedImpressionsForCampaign() {
        var campaign = Dimension.builder().ofType(CAMPAIGN).andValue("CampOne").build();

        var result = service.querySummarizingMetric(IMPRESSIONS, campaign, LocalDate.MIN, LocalDate.MAX);

        assertThat(result).isEqualTo(170);
    }

    @Test
    void shouldReturnSummedImpressionsForCampaignAndTimeWindow() {
        var campaign = Dimension.builder().ofType(CAMPAIGN).andValue("CampOne").build();

        var result = service.querySummarizingMetric(IMPRESSIONS, campaign, now(), LocalDate.MAX);

        assertThat(result).isEqualTo(100);
    }

    @Test
    void shouldReturnSummedImpressionsForTwoCampaigns() {
        var dimension = Dimension.builder().ofType(CAMPAIGN).andValue("CampOne").andValue("CampTwo").build();

        var result = service.querySummarizingMetric(IMPRESSIONS, dimension, LocalDate.MIN, LocalDate.MAX);

        assertThat(result).isEqualTo(770);
    }

    @Test
    void shouldReportZeroForNoDataForExistingSeriesInGivenWindow() {
        var dimension = Dimension.builder().ofType(CAMPAIGN).andValue("CampOne").build();

        var result = service.querySummarizingMetric(IMPRESSIONS, dimension, now().plusWeeks(1), LocalDate.MAX);

        assertThat(result).isEqualTo(0);
    }

    @Test
    void shouldReportZeroForNonExistingSeries() {
        var dimension = Dimension.builder().ofType(CAMPAIGN).andValue("dummy").build();

        var result = service.querySummarizingMetric(IMPRESSIONS, dimension, LocalDate.MIN, LocalDate.MAX);

        assertThat(result).isEqualTo(0);
    }
}