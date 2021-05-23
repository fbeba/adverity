package com.bbt.rec.adverity.application.dto;

import com.bbt.rec.adverity.util.LocalDateConverter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AdDto {

    @CsvBindByName(column = "Datasource")
    private String datasource;

    @CsvBindByName(column = "Campaign")
    private String campaign;

    @CsvCustomBindByName(column = "Daily", converter = LocalDateConverter.class)
    private LocalDate daily;

    @CsvBindByName(column = "Clicks")
    private int clicks;

    @CsvBindByName(column = "Impressions")
    private int impressions;
}
