package com.bbt.rec.adverity.domain;

import java.util.ArrayList;
import java.util.List;

public class Dimension {

    private final DimensionType type;
    private final List<String> values;

    private Dimension(final DimensionType type, final List<String> values) {
        this.type = type;
        this.values = values;
    }

    public enum DimensionType {
        CAMPAIGN, DATASOURCE
    }

    public DimensionType getType() {
        return type;
    }

    public List<String> getValues() {
        return values;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final List<String> values = new ArrayList<>();
        private DimensionType type;

        public Builder ofType(DimensionType type) {
            this.type = type;
            return this;
        }

        public Builder andValue(String value) {
            this.values.add(value);
            return this;
        }

        public Dimension build() {
            return new Dimension(type, values);
        }
    }
}