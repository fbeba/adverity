package com.bbt.rec.adverity.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class MetricInTimeDto {

    @JsonProperty("metric")
    String metricType;
    @JsonProperty("values")
    List<DailyMetricsDto> values;
}
