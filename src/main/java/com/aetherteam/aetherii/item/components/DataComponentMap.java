package com.aetherteam.aetherii.item.components;

public class DataComponentMap implements DataComponentGetter {
    public static final DataComponentMap EMPTY = new DataComponentMap();

    public static Builder builder() {
        return new Builder();
    }

    public boolean has(DataComponentType<?> type) {
        return false;
    }

    public static class Builder {
        public Builder addAll(DataComponentMap map) {
            return this;
        }

        public <T> Builder set(DataComponentType<T> type, T value) {
            return this;
        }

        public DataComponentMap build() {
            return EMPTY;
        }
    }
}
