package com.bbt.rec.adverity.application.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
class DailyMetricsDto {

    LocalDate date;
    int count;

}
