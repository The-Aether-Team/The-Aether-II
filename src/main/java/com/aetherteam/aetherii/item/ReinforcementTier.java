package com.aetherteam.aetherii.item;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.function.IntFunction;

public enum ReinforcementTier implements StringRepresentable {
    FIRST(1, 50, 0),
    SECOND(2, 100, 0),
    THIRD(3, 150, 2);

    private final int tier;
    private final int extraDurability;
    private final int charmSlots;

    ReinforcementTier(int tier, int extraDurability, int charmSlots) { //todo these stats may have to be changed to multiplier factors if they will vary by item tier.
        this.tier = tier;
        this.extraDurability = extraDurability;
        this.charmSlots = charmSlots;
    }

    private static final IntFunction<ReinforcementTier> BY_ID = ByIdMap.continuous(ReinforcementTier::id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    public static final StringRepresentable.EnumCodec<ReinforcementTier> CODEC = StringRepresentable.fromEnum(ReinforcementTier::values);
    public static final StreamCodec<ByteBuf, ReinforcementTier> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, ReinforcementTier::id);

    public int getTier() {
        return this.tier;
    }

    public int getExtraDurability() {
        return this.extraDurability;
    }

    public int getCharmSlots() {
        return this.charmSlots;
    }

    public int id() {
        return this.tier - 1;
    }

    @Override
    public String getSerializedName() {
        return this.name();
    }
}
