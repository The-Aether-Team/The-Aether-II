package com.aetherteam.aetherii.recipe.book;

import net.minecraft.util.StringRepresentable;

public enum AlkahestPurifierBookCategory implements StringRepresentable {
    ITEMS("items"),
    BLOCKS("blocks");

    public static final StringRepresentable.EnumCodec<AlkahestPurifierBookCategory> CODEC = StringRepresentable.fromEnum(AlkahestPurifierBookCategory::values);
    private final String name;

    AlkahestPurifierBookCategory(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
