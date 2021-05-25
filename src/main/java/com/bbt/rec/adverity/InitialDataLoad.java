package com.bbt.rec.adverity;

import com.bbt.rec.adverity.application.dto.Mapper;
import com.bbt.rec.adverity.domain.AdService;
import com.bbt.rec.adverity.domain.ImportService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class InitialDataLoad implements CommandLineRunner {

    private final AdService adService;
    private final ImportService importService;

    @Override
    public void run(final String... args) throws Exception {
        var resourceFile = Paths.get("src", "main", "resources", "ads_input.csv")
                .toAbsolutePath().toString();
        var entities = importService.importFromCsv(resourceFile)
                .stream()
                .map(Mapper::toEntity)
                .collect(Collectors.toList());
        System.out.println("Initial dataload completed");
        adService.store(entities);
    }
}
