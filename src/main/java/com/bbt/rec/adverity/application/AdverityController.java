package com.bbt.rec.adverity.application;

import com.bbt.rec.adverity.application.dto.CtrSummaryDto;
import com.bbt.rec.adverity.application.dto.Mapper;
import com.bbt.rec.adverity.application.dto.MetricInTimeDto;
import com.bbt.rec.adverity.domain.AdEntity;
import com.bbt.rec.adverity.domain.AdService;
import com.bbt.rec.adverity.domain.Dimension;
import com.bbt.rec.adverity.domain.ImportService;
import com.bbt.rec.adverity.domain.Metric;
import com.bbt.rec.adverity.exception.InvalidDimensionTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
@RequiredArgsConstructor
class AdverityController {

    private final AdService service;
    private final ImportService importService;

    @GetMapping("/impressions")
    ResponseEntity<MetricInTimeDto> findImpressionsInTimeWindow(@RequestParam(value = "from", required = false)
                                                                @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate requestedFrom,
                                                                @RequestParam(value = "to", required = false)
                                                                @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate requestedTo) {
        var from = Optional.ofNullable(requestedFrom).orElse(LocalDate.MIN);
        var to = Optional.ofNullable(requestedTo).orElse(LocalDate.now());
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.queryDailyMetrics(Metric.IMPRESSIONS, from, to));
    }

    @GetMapping("/clicks")
    ResponseEntity<MetricInTimeDto> findClicksInTimeWindow(@RequestParam(value = "from", required = false)
                                                           @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate requestedFrom,
                                                           @RequestParam(value = "to", required = false)
                                                           @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate requestedTo) {
        var from = Optional.ofNullable(requestedFrom).orElse(LocalDate.MIN);
        var to = Optional.ofNullable(requestedTo).orElse(LocalDate.now());
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.queryDailyMetrics(Metric.CLICKS, from, to));
    }

    @GetMapping("/ctr")
    ResponseEntity<CtrSummaryDto> getCtrsForDimension(@RequestParam(value = "dimension") final String requestedDimension) {
        var dimension = validateDimension(requestedDimension);
        return ResponseEntity
                .ok()
                .body(service.queryCtr(dimension));
    }

    @GetMapping("/metric/{metric}/byDatasource/{datasource}")
    ResponseEntity<Integer> getMetricByDatasource(@PathVariable(value = "metric") final String requestedMetric,
                                                  @PathVariable(value = "datasource") final String datasource,
                                                  @RequestParam(value = "from", required = false)
                                                  @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate requestedFrom,
                                                  @RequestParam(value = "to", required = false)
                                                  @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate requestedTo) {
        var metric = validateMetric(requestedMetric);
        var dimension = Dimension.builder()
                .ofType(Dimension.DimensionType.DATASOURCE)
                .andValue(datasource)
                .build();
        var from = Optional.ofNullable(requestedFrom).orElse(LocalDate.MIN);
        var to = Optional.ofNullable(requestedTo).orElse(LocalDate.now());

        return ResponseEntity
                .ok()
                .body(service.querySummarizingMetric(metric, dimension, from, to));
    }

    @GetMapping("/metric/{metric}/byCampaign/{campaign}")
    ResponseEntity<Integer> getMetricByCampaign(@PathVariable(value = "metric") final String requestedMetric,
                                                @PathVariable(value = "campaign") final String campaign,
                                                @RequestParam(value = "from", required = false)
                                                @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate requestedFrom,
                                                @RequestParam(value = "to", required = false)
                                                @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate requestedTo) {
        var metric = validateMetric(requestedMetric);
        var dimension = Dimension.builder()
                .ofType(Dimension.DimensionType.CAMPAIGN)
                .andValue(campaign)
                .build();
        var from = Optional.ofNullable(requestedFrom).orElse(LocalDate.MIN);
        var to = Optional.ofNullable(requestedTo).orElse(LocalDate.now());

        return ResponseEntity
                .ok()
                .body(service.querySummarizingMetric(metric, dimension, from, to));
    }

    @PostMapping("/add")
    ResponseEntity<Void> submitAdData(@RequestParam("file") MultipartFile file) {
        importInBackground(file);
        System.out.println("Import in progress");
        return ResponseEntity
                .status(ACCEPTED)
                .build();
    }

    private void importInBackground(MultipartFile file) {
        Runnable runnable = () -> {
            List<AdEntity> entities = null;
            try {
                entities = importService.importFromCsv(file).stream()
                        .map(Mapper::toEntity)
                        .collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Optional.ofNullable(entities).ifPresent(service::store);
            System.out.println("Import finished!");
        };
        new Thread(runnable).start();
    }

    private Dimension validateDimension(String requested) {
        for (var dim : Dimension.DimensionType.values()) {
            if (dim.toString().toLowerCase().startsWith(requested.toLowerCase())) {
                return Dimension.builder().ofType(dim).build();
            }
        }
        throw new InvalidDimensionTypeException(requested);
    }

    private Metric validateMetric(String requested) {
        for (var metric : Metric.values()) {
            if (metric.toString().toLowerCase().startsWith(requested.toLowerCase())) {
                return metric;
            }
        }
        throw new IllegalArgumentException(requested);
    }
}
