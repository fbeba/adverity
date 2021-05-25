package com.bbt.rec.adverity;

import com.bbt.rec.adverity.application.dto.Mapper;
import com.bbt.rec.adverity.domain.AdService;
import com.bbt.rec.adverity.domain.ImportService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class InitialDataLoad implements CommandLineRunner {

    private final AdService adService;
    private final ImportService importService;

    @Override
    public void run(final String... args) throws Exception {
        var path = String.join(File.separator, new FileSystemResource("").getFile().getAbsolutePath(), "src", "test", "resources", "ads_input_short.csv");
        var entities = importService.importFromCsv(path)
                .stream()
                .map(Mapper::toEntity)
                .collect(Collectors.toList());
        adService.store(entities);
    }
}
