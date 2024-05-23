package com.aetherteam.aetherii.data.resources;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.Table;
import net.minecraft.world.level.biome.Climate;

import java.util.List;
import java.util.stream.IntStream;

public final class ClimateGrid<T> {
    private final List<Climate.Parameter> xSpan;
    private final List<Climate.Parameter> ySpan;
    private final Table<Integer, Integer, ClimateHolder<T>> table;

    public ClimateGrid(List<Climate.Parameter> xSpan, List<Climate.Parameter> ySpan) {
        this.xSpan = xSpan;
        this.ySpan = ySpan;
        this.table = ArrayTable.create(IntStream.range(0, this.xSpan.size()).boxed().toList(), IntStream.range(0, this.ySpan.size()).boxed().toList());;
    }

    public void put(int x, int y, T element) {
        this.table.put(x, y, new ClimateHolder<T>(element, this.xSpan.get(x), this.ySpan.get(y)));
    }

    public Table<Integer, Integer, ClimateHolder<T>> getTable() {
        return this.table;
    }

    public record ClimateHolder<T>(T element, Climate.Parameter x, Climate.Parameter y) { }
}
