package com.aetherteam.aetherii.item.components;

import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.equipment.armor.AetherIIArmorMaterials;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.IntFunction;
import java.util.function.Predicate;

public enum ReinforcementTier implements StringRepresentable {
    FIRST(1, 50, 0, Cost.TIER_1),
    SECOND(2, 100, 0, Cost.TIER_2),
    THIRD(3, 150, 1, Cost.TIER_3),
    FOURTH(4, 200, 1, Cost.TIER_4);

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

    public record Cost(Predicate<ItemStack> stackCondition, ItemLike primaryMaterial, int primaryCount, ItemLike secondaryMaterial, int secondaryCount) {
        public static final Predicate<ItemStack> TAEGORE_HIDE = isArmorTier(AetherIIArmorMaterials.TAEGORE_HIDE);
        public static final Predicate<ItemStack> BURRUKAI_PELT = isArmorTier(AetherIIArmorMaterials.BURRUKAI_PELT);
        public static final Predicate<ItemStack> SKYROOT = isTier(AetherIIItemTiers.SKYROOT);
        public static final Predicate<ItemStack> HOLYSTONE = isTier(AetherIIItemTiers.HOLYSTONE);
        public static final Predicate<ItemStack> ZANITE = isTier(AetherIIItemTiers.ZANITE).or(isArmorTier(AetherIIArmorMaterials.ZANITE));
        public static final Predicate<ItemStack> ARKENIUM = isTier(AetherIIItemTiers.ARKENIUM).or(isArmorTier(AetherIIArmorMaterials.ARKENIUM));
        public static final Predicate<ItemStack> GRAVITITE = isTier(AetherIIItemTiers.GRAVITITE).or(isArmorTier(AetherIIArmorMaterials.GRAVITITE));

        public static final Set<Cost> TIER_1 = Set.of(
                new Cost(TAEGORE_HIDE, AetherIIItems.ARKENIUM_PLATES, 1, Items.AIR, 0),
                new Cost(BURRUKAI_PELT, AetherIIItems.ARKENIUM_PLATES, 1, Items.AIR, 0),
                new Cost(SKYROOT, AetherIIItems.ARKENIUM_PLATES, 1, Items.AIR, 0),
                new Cost(HOLYSTONE, AetherIIItems.ARKENIUM_PLATES, 1, Items.AIR, 0),
                new Cost(ZANITE, AetherIIItems.ARKENIUM_PLATES, 2, Items.AIR, 0),
                new Cost(ARKENIUM, AetherIIItems.ARKENIUM_PLATES, 2, Items.AIR, 0),
                new Cost(GRAVITITE, AetherIIItems.ARKENIUM_PLATES, 3, Items.AIR, 0)
        );
        public static final Set<Cost> TIER_2 = Set.of(
                new Cost(TAEGORE_HIDE, AetherIIItems.ARKENIUM_PLATES, 2, AetherIIItems.CORROBONITE_CRYSTAL, 1),
                new Cost(BURRUKAI_PELT, AetherIIItems.ARKENIUM_PLATES, 2, AetherIIItems.CORROBONITE_CRYSTAL, 1),
                new Cost(SKYROOT, AetherIIItems.ARKENIUM_PLATES, 2, AetherIIItems.CORROBONITE_CRYSTAL, 1),
                new Cost(HOLYSTONE, AetherIIItems.ARKENIUM_PLATES, 2, AetherIIItems.CORROBONITE_CRYSTAL, 1),
                new Cost(ZANITE, AetherIIItems.ARKENIUM_PLATES, 3, AetherIIItems.CORROBONITE_CRYSTAL, 1),
                new Cost(ARKENIUM, AetherIIItems.ARKENIUM_PLATES, 3, AetherIIItems.CORROBONITE_CRYSTAL, 1),
                new Cost(GRAVITITE, AetherIIItems.ARKENIUM_PLATES, 4, AetherIIItems.CORROBONITE_CRYSTAL, 1)
        );
        public static final Set<Cost> TIER_3 = Set.of(
                new Cost(TAEGORE_HIDE, AetherIIItems.ARKENIUM_PLATES, 4, AetherIIItems.CORROBONITE_CRYSTAL, 3),
                new Cost(BURRUKAI_PELT, AetherIIItems.ARKENIUM_PLATES, 4, AetherIIItems.CORROBONITE_CRYSTAL, 3),
                new Cost(SKYROOT, AetherIIItems.ARKENIUM_PLATES, 4, AetherIIItems.CORROBONITE_CRYSTAL, 3),
                new Cost(HOLYSTONE, AetherIIItems.ARKENIUM_PLATES, 4, AetherIIItems.CORROBONITE_CRYSTAL, 3),
                new Cost(ZANITE, AetherIIItems.ARKENIUM_PLATES, 5, AetherIIItems.CORROBONITE_CRYSTAL, 3),
                new Cost(ARKENIUM, AetherIIItems.ARKENIUM_PLATES, 5, AetherIIItems.CORROBONITE_CRYSTAL, 3),
                new Cost(GRAVITITE, AetherIIItems.ARKENIUM_PLATES, 6, AetherIIItems.CORROBONITE_CRYSTAL, 3)
        );
        public static final Set<Cost> TIER_4 = Set.of(
                new Cost(ARKENIUM, AetherIIItems.ARKENIUM_PLATES, 6, AetherIIItems.CORROBONITE_CRYSTAL, 4)
        );

        private static Predicate<ItemStack> isTier(AetherIIItemTiers tier) {
            return (itemStack) -> itemStack.getItem() instanceof TieredItem tieredItem && tieredItem.getTier() == tier;
        }

        private static Predicate<ItemStack> isArmorTier(Holder<ArmorMaterial> material) {
            return (itemStack) -> itemStack.getItem() instanceof ArmorItem armorItem && armorItem.getMaterial().is(material);
        }
    }
}
