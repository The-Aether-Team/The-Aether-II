package com.aetherteam.aetherii.recipe.book;

import net.minecraft.util.StringRepresentable;

public enum AmberHourglassBookCategory implements StringRepresentable {
    ITEMS("items"),
    BLOCKS("blocks"),
    UNCRAFTING("uncrafting");

    public static final StringRepresentable.EnumCodec<AmberHourglassBookCategory> CODEC = StringRepresentable.fromEnum(AmberHourglassBookCategory::values);
    private final String name;

    AmberHourglassBookCategory(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
