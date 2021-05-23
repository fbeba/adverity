package com.bbt.rec.adverity.util;

import com.opencsv.bean.AbstractBeanField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateConverter extends AbstractBeanField<LocalDate> {

    @Override
    protected Object convert(String s) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        return LocalDate.parse(s, formatter);
    }
}
