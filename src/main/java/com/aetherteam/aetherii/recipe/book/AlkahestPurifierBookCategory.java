package com.aetherteam.aetherii.recipe.book;

import io.netty.buffer.ByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.function.IntFunction;

public enum AlkahestPurifierBookCategory implements StringRepresentable {
    ITEMS(0, "items"),
    BLOCKS(1, "blocks");

    private static final IntFunction<AlkahestPurifierBookCategory> BY_ID = ByIdMap.continuous((e) -> e.id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    public static final StringRepresentable.EnumCodec<AlkahestPurifierBookCategory> CODEC = StringRepresentable.fromEnum(AlkahestPurifierBookCategory::values);
    public static final StreamCodec<ByteBuf, AlkahestPurifierBookCategory> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, (e) -> e.id);
    private final int id;
    private final String name;

    AlkahestPurifierBookCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
