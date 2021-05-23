package com.bbt.rec.adverity.domain;

import com.bbt.rec.adverity.application.dto.AdDto;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.List;

@Service
public class ImportService {

    public List<AdDto> importFromCsv(final String path) throws Exception {
        var cvsToBean = new CsvToBeanBuilder<AdDto>(new FileReader(path))
                .withType(AdDto.class)
                .build();
        return cvsToBean.parse();
    }
}
