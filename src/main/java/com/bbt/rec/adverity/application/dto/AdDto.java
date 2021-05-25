package com.bbt.rec.adverity.application.dto;

import com.bbt.rec.adverity.util.LocalDateConverter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
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
