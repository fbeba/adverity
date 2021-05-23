package com.bbt.rec.adverity.domain;

import com.bbt.rec.adverity.application.dto.CtrSummaryDto;
import com.bbt.rec.adverity.application.dto.DailySummaryDto;
import com.bbt.rec.adverity.application.dto.Mapper;
import com.bbt.rec.adverity.infrastructure.InMemoryAdRepository;
import com.bbt.rec.adverity.infrastructure.LockedAdRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AdService {

    private final AdRepository activeRepository = new InMemoryAdRepository();
    private final AdRepository lockedRepository = new LockedAdRepository();
    private AdRepository repository = activeRepository;

    public List<AdEntity> store(final List<AdEntity> entities) {
        lockRepository();
        entities.forEach(activeRepository::store);
        unlockRepository();
        return entities;
    }

    public int querySummarizingMetric(final Metric metric, final Dimension dimension, final LocalDate from, final LocalDate to) {
        return repository.getAdsBy(dimension)
                .stream()
                .filter(ad -> ad.inDateRange(from, to))
                .map(extractSummarizingMetric(metric))
                .reduce(Integer::sum)
                .orElse(0);
    }

    public CtrSummaryDto queryCtr(final Dimension dimension) {
        var adsByDimension = repository.getRepoBy(dimension)
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> computeCtr(entry.getValue())));
        return Mapper.toCtrDto(adsByDimension, dimension);
    }

    public DailySummaryDto queryDailyImpressions(LocalDate from, LocalDate to) {
        var impressionsDaily = repository.getAdsByDates(from, to)
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> sum(entry.getValue(), AdEntity::getImpressions)
                ));
        return Mapper.toDailyDto(impressionsDaily);
    }

    private <T> int sum(final List<T> value, final Function<T, Integer> extractor) {
        return value.stream()
                .map(extractor)
                .reduce(0, Integer::sum);
    }

    private double computeCtr(final List<AdEntity> ads) {
        var precise = ads.stream()
                .mapToDouble(AdEntity::computeCtr)
                .average()
                .orElse(0d);
        return Math.round(precise * 10000d) / 100d;
    }

    private Function<AdEntity, Integer> extractSummarizingMetric(Metric metricType) {
        switch (metricType) {
            case CLICKS:
                return AdEntity::getClicks;
            case IMPRESSIONS:
                return AdEntity::getImpressions;
        }
        throw new IllegalArgumentException("Summarization not possible for CTR.");
    }

    private void unlockRepository() {
        repository = activeRepository;
    }

    private void lockRepository() {
        repository = lockedRepository;
    }
}
