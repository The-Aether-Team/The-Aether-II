package com.aetherteam.aetherii.recipe.book;

import io.netty.buffer.ByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.function.IntFunction;

public enum AltarBookCategory implements StringRepresentable {
    FOOD(0, "food"),
    BLOCKS(1, "blocks"),
    REPAIRING(2, "repairing"),
    MISC(3, "misc");

    private static final IntFunction<AltarBookCategory> BY_ID = ByIdMap.continuous((e) -> e.id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    public static final StringRepresentable.EnumCodec<AltarBookCategory> CODEC = StringRepresentable.fromEnum(AltarBookCategory::values);
    public static final StreamCodec<ByteBuf, AltarBookCategory> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, (e) -> e.id);
    private final int id;
    private final String name;

    AltarBookCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
