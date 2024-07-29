package com.aetherteam.aetherii.recipe.builder.book;

import net.minecraft.util.StringRepresentable;

public enum AltarBookCategory implements StringRepresentable {
    FOOD("food"),
    BLOCKS("blocks"),
    REPAIRING("repairing"),
    MISC("misc");

    public static final StringRepresentable.EnumCodec<AltarBookCategory> CODEC = StringRepresentable.fromEnum(AltarBookCategory::values);
    private final String name;

    AltarBookCategory(String pName) {
        this.name = pName;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
