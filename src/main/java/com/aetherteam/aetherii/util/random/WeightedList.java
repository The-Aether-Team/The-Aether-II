package com.aetherteam.aetherii.util.random;

import net.minecraft.util.RandomSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WeightedList<T> {
    private final List<Weighted<T>> entries;

    private WeightedList(List<Weighted<T>> entries) {
        this.entries = List.copyOf(entries);
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public List<Weighted<T>> unwrap() {
        return this.entries;
    }

    public boolean isEmpty() {
        return this.entries.isEmpty();
    }

    public T getRandomOrThrow(RandomSource random) {
        if (this.entries.isEmpty()) {
            throw new IllegalStateException("No weighted entries");
        }
        int totalWeight = this.entries.stream().mapToInt(Weighted::weight).sum();
        int selected = random.nextInt(Math.max(totalWeight, 1));
        for (Weighted<T> entry : this.entries) {
            selected -= entry.weight();
            if (selected < 0) {
                return entry.value();
            }
        }
        return this.entries.get(this.entries.size() - 1).value();
    }

    public Optional<T> getRandom(RandomSource random) {
        return this.entries.isEmpty() ? Optional.empty() : Optional.of(this.getRandomOrThrow(random));
    }

    public static class Builder<T> {
        private final List<Weighted<T>> entries = new ArrayList<>();

        public Builder<T> add(T value, int weight) {
            this.entries.add(new Weighted<>(value, weight));
            return this;
        }

        public WeightedList<T> build() {
            return new WeightedList<>(this.entries);
        }
    }
}
