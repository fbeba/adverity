package com.bbt.rec.adverity;

import com.bbt.rec.adverity.application.dto.AdDto;
import com.bbt.rec.adverity.domain.ImportService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AdverityApplicationTests {

    @Autowired
    private ImportService importService;

    @Test
    void csvFileGetsImported() throws Exception {
        var path = String.join(File.separator, new FileSystemResource("").getFile().getAbsolutePath(), "src", "test", "resources", "ads_input_short.csv");

        var result = importService.importFromCsv(path);

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
