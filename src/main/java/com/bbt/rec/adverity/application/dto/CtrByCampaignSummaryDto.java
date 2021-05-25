package com.bbt.rec.adverity.application.dto;

import lombok.Value;

@Value
class CtrByCampaignSummaryDto implements CtrByDimensionSummaryDto {

    String campaign;
    double ctr;

    CtrByCampaignSummaryDto(final String campaign, final double ctr) {
        this.campaign = campaign;
        this.ctr = ctr;
    }
}
