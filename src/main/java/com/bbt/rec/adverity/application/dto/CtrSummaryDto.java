package com.bbt.rec.adverity.application.dto;

import lombok.Value;

import java.util.List;

@Value
public class CtrSummaryDto {
    List<CtrByDimensionSummaryDto> ctrs;

    CtrSummaryDto(final List<CtrByDimensionSummaryDto> ctrs) {
        this.ctrs = ctrs;
    }
}
