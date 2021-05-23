package com.bbt.rec.adverity.application.dto;

import lombok.Value;

@Value
class CtrByCampaignSummaryDtoImpl implements CtrByDimensionSummaryDto {

    String campaign;
    double ctr;

    CtrByCampaignSummaryDtoImpl(final String campaign, final double ctr) {
        this.campaign = campaign;
        this.ctr = ctr;
    }
}
