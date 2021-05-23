package com.bbt.rec.adverity.application.dto;

import com.bbt.rec.adverity.domain.Dimension;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

public class Mapper {

    public static CtrSummaryDto toCtrDto(final Map<String, Double> ctrByDimension, final Dimension dimension) {
        var factory = DimensionSummaryFactory.forDimension(dimension);
        var ctrs = ctrByDimension
                .entrySet().stream()
                .map(factory::createDimensionSummaryDto)
                .collect(Collectors.toList());
        return new CtrSummaryDto(ctrs);
    }

    public static DailySummaryDto toDailyDto(final Map<LocalDate, Integer> impressionsDaily) {
        var impressions = impressionsDaily
                .entrySet().stream()
                .map(entry -> new DailyImpressionsDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        return new DailySummaryDto(impressions);
    }
}
