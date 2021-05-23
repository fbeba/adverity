package com.bbt.rec.adverity.infrastructure;

import com.bbt.rec.adverity.domain.AdEntity;
import com.bbt.rec.adverity.domain.AdRepository;
import com.bbt.rec.adverity.domain.Dimension;
import com.bbt.rec.adverity.exception.InvalidDimensionTypeException;

import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.toSet;

public class InMemoryAdRepository implements AdRepository {

    private final Map<String, List<AdEntity>> byCampaignRepo = new HashMap<>();
    private final Map<String, List<AdEntity>> byDatasourceRepo = new HashMap<>();
    private final NavigableMap<LocalDate, List<AdEntity>> byDateRepo = new TreeMap<>();

    @Override
    public AdEntity store(AdEntity entity) {
        putIntoRepo(byCampaignRepo, entity.getCampaign(), entity);
        putIntoRepo(byDatasourceRepo, entity.getDatasource(), entity);
        putIntoRepo(byDateRepo, entity.getDaily(), entity);
        return entity;
    }

    @Override
    public Set<AdEntity> getAdsBy(final Dimension dimension) {
        switch (dimension.getType()) {
            case CAMPAIGN:
                return dimension.getValues()
                        .stream()
                        .map(byCampaignRepo::get)
                        .flatMap(List::stream)
                        .collect(toSet());
            case DATASOURCE:
                return dimension.getValues()
                        .stream()
                        .map(byDatasourceRepo::get)
                        .flatMap(List::stream)
                        .collect(toSet());
        }
        throw new InvalidDimensionTypeException(dimension.getType().toString());
    }

    @Override
    public Map<String, List<AdEntity>> getRepoBy(final Dimension dimension) {
        switch (dimension.getType()) {
            case CAMPAIGN:
                return byCampaignRepo;
            case DATASOURCE:
                return byDatasourceRepo;
        }
        throw new InvalidDimensionTypeException(dimension.getType().toString());
    }

    @Override
    public Map<LocalDate, List<AdEntity>> getAdsByDates(final LocalDate from, final LocalDate to) {
        return byDateRepo.subMap(from, true, to, true);
    }

    private <T> void putIntoRepo(final Map<T, List<AdEntity>> repo, final T key, final AdEntity value) {
        var list = repo.get(key);
        if (list == null) {
            list = new LinkedList<>();
        }
        list.add(value);
        repo.put(key, list);
    }
}
