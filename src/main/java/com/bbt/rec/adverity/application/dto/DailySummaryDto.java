package com.bbt.rec.adverity.application.dto;

import lombok.Value;

import java.util.List;

@Value
public class DailySummaryDto {

    List<DailyImpressionsDto> impressions;
}
