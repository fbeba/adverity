package com.bbt.rec.adverity.application.dto;

import com.bbt.rec.adverity.domain.AdEntity;
import com.bbt.rec.adverity.domain.Dimension;
import com.bbt.rec.adverity.domain.Metric;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

public class Mapper {

    public static CtrSummaryDto toCtrDto(final Map<String, Double> ctrByDimension, final Dimension dimension) {
        var factory = DimensionSummaryFactory.forDimension(dimension);
        var ctrs = ctrByDimension
                .entrySet().stream()
                .map(factory::toSummaryDto)
                .collect(Collectors.toList());
        return new CtrSummaryDto(ctrs);
    }

    public static MetricInTimeDto toDailyDto(final Metric metric, final Map<LocalDate, Integer> impressionsDaily) {
        var impressions = impressionsDaily
                .entrySet().stream()
                .map(entry -> new DailyMetricsDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        return new MetricInTimeDto(metric.toString(), impressions);
    }

    public static AdEntity toEntity(final AdDto dto) {
        return AdEntity.builder()
                .datasource(dto.getDatasource())
                .campaign(dto.getCampaign())
                .daily(dto.getDaily())
                .clicks(dto.getClicks())
                .impressions(dto.getImpressions())
                .build();
    }
}
