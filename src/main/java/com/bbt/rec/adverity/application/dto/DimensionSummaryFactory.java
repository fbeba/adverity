package com.bbt.rec.adverity.application.dto;

import com.bbt.rec.adverity.domain.Dimension;
import com.bbt.rec.adverity.exception.InvalidDimensionTypeException;

import java.util.Map;

class DimensionSummaryFactory {

    private final Dimension.DimensionType type;

    DimensionSummaryFactory(final Dimension dimension) {
        this.type = dimension.getType();
    }

    static DimensionSummaryFactory forDimension(Dimension dimension) {
        return new DimensionSummaryFactory(dimension);
    }

    CtrByDimensionSummaryDto createDimensionSummaryDto(final Map.Entry<String, Double> entry) {
        switch (type) {
            case DATASOURCE:
                return new CtrByDatasourceSummaryDtoImpl(entry.getKey(), entry.getValue());
            case CAMPAIGN:
                return new CtrByCampaignSummaryDtoImpl(entry.getKey(), entry.getValue());
        }
        throw new InvalidDimensionTypeException(type.toString());
    }
}
