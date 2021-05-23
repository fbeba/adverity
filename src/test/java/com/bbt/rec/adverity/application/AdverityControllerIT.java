package com.bbt.rec.adverity.application;

import com.bbt.rec.adverity.domain.ImportService;
import com.bbt.rec.adverity.exception.InvalidDimensionTypeException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdverityControllerIT {

    @Autowired
    private AdverityController controller;

    @Autowired
    private ImportService importService;

    @Test
    void findImpressionsInTimeWindow() throws Exception {
        var path = String.join(File.separator, new FileSystemResource("").getFile().getAbsolutePath(), "src", "test", "resources", "ads_input_short.csv");
        var a = importService.importFromCsv(path);

        var impressions = controller.findImpressionsInTimeWindow(null, null);

        Assertions.assertThat(impressions).isNotNull();
    }

    @Test
    void getCtrsForDimension() {
        var exception = assertThrows(InvalidDimensionTypeException.class, () -> controller.getCtrsForDimension("wrongDimension"));

        assertEquals("Handling for dimension wrongDimension not available.", exception.getMessage());
    }
}