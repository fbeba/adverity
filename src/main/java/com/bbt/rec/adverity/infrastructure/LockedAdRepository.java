package com.bbt.rec.adverity.infrastructure;

import com.bbt.rec.adverity.domain.AdEntity;
import com.bbt.rec.adverity.domain.AdRepository;
import com.bbt.rec.adverity.domain.Dimension;
import com.bbt.rec.adverity.exception.RepositoryLockedException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LockedAdRepository implements AdRepository {

    @Override
    public AdEntity store(final AdEntity entity) {
        throw new RepositoryLockedException();
    }

    @Override
    public Set<AdEntity> getAdsBy(final Dimension dimension) {
        throw new RepositoryLockedException();
    }

    @Override
    public Map<String, List<AdEntity>> getRepoBy(final Dimension dimension) {
        throw new RepositoryLockedException();
    }

    @Override
    public Map<LocalDate, List<AdEntity>> getAdsByDates(final LocalDate from, final LocalDate to) {
        throw new RepositoryLockedException();
    }
}
