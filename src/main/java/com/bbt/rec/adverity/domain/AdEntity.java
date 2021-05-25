package com.bbt.rec.adverity.domain;

import java.time.LocalDate;

public class AdEntity {

    private final String datasource;
    private final String campaign;
    private final LocalDate daily;
    private final int clicks;
    private final int impressions;

    public AdEntity(final String datasource, final String campaign, final LocalDate daily, final int clicks, final int impressions) {
        this.datasource = datasource;
        this.campaign = campaign;
        this.daily = daily;
        this.clicks = clicks;
        this.impressions = impressions;
    }

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
