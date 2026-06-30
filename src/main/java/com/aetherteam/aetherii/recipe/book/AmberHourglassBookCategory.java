package com.aetherteam.aetherii.recipe.book;

import io.netty.buffer.ByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.function.IntFunction;

public enum AmberHourglassBookCategory implements StringRepresentable {
    RESTORATION(0, "restoration"),
    UNCRAFTING(1, "uncrafting");

    private static final IntFunction<AmberHourglassBookCategory> BY_ID = ByIdMap.continuous((e) -> e.id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    public static final StringRepresentable.EnumCodec<AmberHourglassBookCategory> CODEC = StringRepresentable.fromEnum(AmberHourglassBookCategory::values);
    public static final StreamCodec<ByteBuf, AmberHourglassBookCategory> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, (e) -> e.id);
    private final int id;
    private final String name;

    AmberHourglassBookCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
