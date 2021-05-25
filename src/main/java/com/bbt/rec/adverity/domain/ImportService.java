package com.bbt.rec.adverity.domain;

import com.bbt.rec.adverity.application.dto.AdDto;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ImportService {

    public List<AdDto> importFromCsv(final String path) throws Exception {
        return convert(new FileReader(path));
    }

    public List<AdDto> importFromCsv(final MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("csvimport" + LocalDateTime.now().getNano(), null, null);
        FileOutputStream fos = new FileOutputStream(tempFile);
        fos.write(file.getBytes());
        fos.close();
        return convert(new FileReader(tempFile));
    }

    private List<AdDto> convert(final FileReader fileReader) {
        var cvsToBean = new CsvToBeanBuilder<AdDto>(fileReader)
                .withType(AdDto.class)
                .build();
        return cvsToBean.parse();
    }
}
