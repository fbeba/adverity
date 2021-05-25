package com.bbt.rec.adverity.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class MetricInTimeDto {

    @JsonProperty("metrics")
    String metricType;
    @JsonProperty("metrics")
    List<DailyMetricsDto> values;
}
