package com.bbt.rec.adverity;

import com.bbt.rec.adverity.application.dto.AdDto;
import com.bbt.rec.adverity.domain.ImportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Paths;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AdverityApplicationTests {

    @Autowired
    private ImportService importService;

    @Test
    void csvFileGetsImported() throws Exception {
        var resourceFile = Paths.get("src", "main", "resources", "ads_input_short.csv")
                .toAbsolutePath().toString();

        var result = importService.importFromCsv(resourceFile);

        assertThat(result)
                .hasSize(3)
                .contains(AdDto.builder()
                        .datasource("Google Ads")
                        .campaign("Adventmarkt Touristik")
                        .daily(LocalDate.of(2019, 11, 12))
                        .clicks(7)
                        .impressions(22425)
                        .build());
    }
}
