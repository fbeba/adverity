package com.bbt.rec.adverity.application.dto;

import lombok.Value;

@Value
class CtrByDatasourceSummaryDtoImpl implements CtrByDimensionSummaryDto {

    String datasource;
    double ctr;

    CtrByDatasourceSummaryDtoImpl(final String datasource, final double ctr) {
        this.datasource = datasource;
        this.ctr = ctr;
    }
}
