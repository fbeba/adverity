package com.bbt.rec.adverity.application.dto;

import lombok.Value;

@Value
class CtrByDatasourceSummaryDto implements CtrByDimensionSummaryDto {

    String datasource;
    double ctr;

    CtrByDatasourceSummaryDto(final String datasource, final double ctr) {
        this.datasource = datasource;
        this.ctr = ctr;
    }
}
