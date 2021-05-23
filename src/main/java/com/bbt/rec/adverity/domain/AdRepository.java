package com.bbt.rec.adverity.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AdRepository {
    AdEntity store(AdEntity entity);

    Set<AdEntity> getAdsBy(Dimension dimension);

    Map<String, List<AdEntity>> getRepoBy(Dimension dimension);

    Map<LocalDate, List<AdEntity>> getAdsByDates(LocalDate from, LocalDate to);
}
