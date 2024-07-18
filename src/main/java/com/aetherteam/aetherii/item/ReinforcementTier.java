package com.aetherteam.aetherii.item;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.function.Predicate;

public enum ReinforcementTier implements StringRepresentable {
    FIRST(1, 50, 0, Cost.TIER_1),
    SECOND(2, 100, 0, Cost.TIER_2),
    THIRD(3, 150, 1, Cost.TIER_3);

    private final int tier;
    private final int extraDurability;
    private final int charmSlots;
    private final Set<Cost> costs;

    ReinforcementTier(int tier, int extraDurability, int charmSlots, Set<Cost> costs) {
        this.tier = tier;
        this.extraDurability = extraDurability;
        this.charmSlots = charmSlots;
        this.costs = costs;
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

    public Set<Cost> getCosts() {
        return this.costs;
    }

    @Nullable
    public Cost getCost(ItemStack stack) {
        for (Cost cost : this.getCosts()) {
            if (cost.stackCondition().test(stack)) {
                return cost;
            }
        }
        return null;
    }

    public int id() {
        return this.tier - 1;
    }

    @Override
    public String getSerializedName() {
        return this.name();
    }

    public record Cost(Predicate<ItemStack> stackCondition, ItemLike primaryMaterial, int primaryCount, ItemLike secondaryMaterial, int secondaryCount) { //todo temps
        public static final Predicate<ItemStack> DEFAULT = (itemStack) -> true;

        public static final Set<Cost> TIER_1 = Set.of(
                new Cost(DEFAULT, AetherIIItems.ARKENIUM_PLATES, 3, AetherIIItems.CORROBONITE_CRYSTAL, 1)
        );
        public static final Set<Cost> TIER_2 = Set.of(
                new Cost(DEFAULT, AetherIIItems.ARKENIUM_PLATES, 5, AetherIIItems.CORROBONITE_CRYSTAL, 2)
        );
        public static final Set<Cost> TIER_3 = Set.of(
                new Cost(DEFAULT, AetherIIItems.ARKENIUM_PLATES, 7, AetherIIItems.CORROBONITE_CRYSTAL, 3)
        );
    }
}
