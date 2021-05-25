package com.bbt.rec.adverity.domain;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public class AdEntity {

    private final String datasource;
    private final String campaign;
    private final LocalDate daily;
    private final int clicks;
    private final int impressions;

    public String getDatasource() {
        return datasource;
    }

    public String getCampaign() {
        return campaign;
    }

    public LocalDate getDaily() {
        return daily;
    }

    int getClicks() {
        return clicks;
    }

    int getImpressions() {
        return impressions;
    }

    Double computeCtr() {
        return (double) clicks / impressions;
    }

    boolean inDateRange(final LocalDate from, final LocalDate to) {
        return !(daily.isAfter(to) || daily.isBefore(from));
    }
}
