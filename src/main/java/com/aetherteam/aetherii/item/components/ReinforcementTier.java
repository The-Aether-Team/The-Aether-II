package com.aetherteam.aetherii.item.components;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.item.AetherIIItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;

public enum ReinforcementTier implements StringRepresentable, TooltipProvider {
    FIRST(1, Stats.TIER_1, Cost.TIER_1),
    SECOND(2, Stats.TIER_2, Cost.TIER_2),
    THIRD(3, Stats.TIER_3, Cost.TIER_3),
    FOURTH(4, Stats.TIER_4, Cost.TIER_4);

    private final int tier;
    private final Set<Stats> stats;
    private final Set<Cost> costs;

    ReinforcementTier(int tier, Set<Stats> stats, Set<Cost> costs) {
        this.tier = tier;
        this.stats = stats;
        this.costs = costs;
    }

    private static final IntFunction<ReinforcementTier> BY_ID = ByIdMap.continuous(ReinforcementTier::id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    public static final StringRepresentable.EnumCodec<ReinforcementTier> CODEC = StringRepresentable.fromEnum(ReinforcementTier::values);
    public static final StreamCodec<ByteBuf, ReinforcementTier> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, ReinforcementTier::id);

    public int getTierNumber() {
        return this.tier;
    }

    public Set<Stats> getStats() {
        return this.stats;
    }

    public Set<Cost> getCosts() {
        return this.costs;
    }

    @Nullable
    public Stats getStat(ItemStack stack) {
        for (Stats testStats : this.getStats()) {
            if (testStats.stackCondition().test(stack)) {
                return testStats;
            }
        }
        return null;
    }

    @Nullable
    public Cost getCost(ItemStack stack) {
        for (Cost testCost : this.getCosts()) {
            if (testCost.stackCondition().test(stack)) {
                return testCost;
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

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
        consumer.accept(createReinforcementComponent(this.getTierNumber()));
    }

    public static Component createReinforcementComponent(int tier) {
        return Component.translatable("aether_ii.tooltip.item.reinforcement", Component.translatable("enchantment.level." + tier)).withColor(14408667);
    }

    public static Map<Integer, Cost> getCosts(ItemStack stack) {
        Map<Integer, ReinforcementTier.Cost> costs = new HashMap<>();
        for (ReinforcementTier tier : ReinforcementTier.values()) {
            int value = tier.getTierNumber();
            ReinforcementTier.Cost cost = tier.getCost(stack);
            costs.put(value, cost);
        }
        return costs;
    }

    public static int getTierCount(ItemStack stack) {
        int count = 0;
        List<Cost> costs = new ArrayList<>(getCosts(stack).values());
        for (int i = costs.size() - 1; i >= 0; i--) {
            ReinforcementTier.Cost cost = costs.get(i);
            if (cost != null) {
                count++;
            }
        }
        return count;
    }

    public static boolean isItemAtMaxTier(ItemStack itemStack) {
        int max = getTierCount(itemStack);
        ReinforcementTier tier = itemStack.get(AetherIIDataComponents.REINFORCEMENT_TIER);
        return tier != null && tier.getTierNumber() == max;
    }

    public static int getTierForItem(ItemStack itemStack) {
        int max = getTierCount(itemStack);
        ReinforcementTier tier = itemStack.get(AetherIIDataComponents.REINFORCEMENT_TIER);
        if (tier != null) {
            return Math.min(tier.getTierNumber(), max);
        } else {
            return 0;
        }
    }

    @Nullable
    public static ReinforcementTier.Cost getCostForTier(ItemStack stack, int tier) {
        return getCosts(stack).getOrDefault(tier, null);
    }

    public static int getPrimaryCostForTier(ItemStack stack, int tier) {
        ReinforcementTier.Cost initialCost = getCostForTier(stack, tier);
        if (initialCost != null) {
            int cost = initialCost.primaryCount();
            int minimumTier = getTierForItem(stack);
            for (int i = tier - 1; i > minimumTier; i--) {
                ReinforcementTier.Cost costForTier = getCostForTier(stack, i);
                if (costForTier != null) {
                    cost += costForTier.primaryCount();
                }
            }
            return cost;
        }
        return -1;
    }

    public static int getSecondaryCostForTier(ItemStack stack, int tier) {
        ReinforcementTier.Cost initialCost = getCostForTier(stack, tier);
        if (initialCost != null) {
            int cost = initialCost.secondaryCount();
            int minimumTier = getTierForItem(stack);
            for (int i = tier - 1; i > minimumTier; i--) {
                ReinforcementTier.Cost costForTier = getCostForTier(stack, i);
                if (costForTier != null) {
                    cost += costForTier.secondaryCount();
                }
            }
            return cost;
        }
        return -1;
    }

    public record Stats(Predicate<ItemStack> stackCondition, int durabilityToAdd, Charms charmsToSet) {
        public static final Predicate<ItemStack> DEFAULT = (stack) -> true;
        public static final Predicate<ItemStack> TOOLS = (stack) -> stack.is(AetherIITags.Items.ACCEPTS_CHARMS_TOOLS);
        public static final Predicate<ItemStack> WEAPONS = (stack) -> stack.is(AetherIITags.Items.ACCEPTS_CHARMS_WEAPONS);
        public static final Predicate<ItemStack> ARMOR = (stack) -> stack.is(AetherIITags.Items.ACCEPTS_CHARMS_ARMOR);

        public static final Set<Stats> TIER_1 = Set.of(
                new Stats(DEFAULT, 50, new Charms())
        );
        public static final Set<Stats> TIER_2 = Set.of(
                new Stats(DEFAULT, 100, new Charms())
        );
        public static final Set<Stats> TIER_3 = Set.of(
                new Stats(TOOLS, 150, new Charms(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE))),
                new Stats(WEAPONS, 150, new Charms(new Charms.CharmHolder(Charms.Type.WEAPON, Charms.Tier.ONE))),
                new Stats(ARMOR, 150, new Charms(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)))
        );
        public static final Set<Stats> TIER_4 = Set.of(
                new Stats(TOOLS, 200, new Charms(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.TWO))),
                new Stats(WEAPONS, 200, new Charms(new Charms.CharmHolder(Charms.Type.WEAPON, Charms.Tier.TWO))),
                new Stats(ARMOR, 200, new Charms(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO)))
        );
    }

    public record Cost(Predicate<ItemStack> stackCondition, ItemLike primaryMaterial, int primaryCount, ItemLike secondaryMaterial, int secondaryCount) {
        public static final Predicate<ItemStack> BEAST_PELT = isTier(AetherIITags.Items.BEAST_PELT_ARMOR);
        public static final Predicate<ItemStack> BURRUKAI_PLATE = isTier(AetherIITags.Items.BURRUKAI_PLATE_ARMOR);
        public static final Predicate<ItemStack> SKYROOT = isTier(AetherIITags.Items.SKYROOT_TOOL);
        public static final Predicate<ItemStack> HOLYSTONE = isTier(AetherIITags.Items.HOLYSTONE_TOOL);
        public static final Predicate<ItemStack> ZANITE = isTier(AetherIITags.Items.ZANITE_TOOL).or(isTier(AetherIITags.Items.ZANITE_ARMOR));
        public static final Predicate<ItemStack> ARKENIUM = isTier(AetherIITags.Items.ARKENIUM_TOOL).or(isTier(AetherIITags.Items.ARKENIUM_ARMOR));
        public static final Predicate<ItemStack> GRAVITITE = isTier(AetherIITags.Items.GRAVITITE_TOOL).or(isTier(AetherIITags.Items.GRAVITITE_ARMOR));

        public static final Set<Cost> TIER_1 = Set.of(
                new Cost(BEAST_PELT, AetherIIItems.ARKENIUM_PLATES, 1, Items.AIR, 0),
                new Cost(BURRUKAI_PLATE, AetherIIItems.ARKENIUM_PLATES, 1, Items.AIR, 0),
                new Cost(SKYROOT, AetherIIItems.ARKENIUM_PLATES, 1, Items.AIR, 0),
                new Cost(HOLYSTONE, AetherIIItems.ARKENIUM_PLATES, 1, Items.AIR, 0),
                new Cost(ZANITE, AetherIIItems.ARKENIUM_PLATES, 2, Items.AIR, 0),
                new Cost(ARKENIUM, AetherIIItems.ARKENIUM_PLATES, 2, Items.AIR, 0),
                new Cost(GRAVITITE, AetherIIItems.ARKENIUM_PLATES, 3, Items.AIR, 0)
        );
        public static final Set<Cost> TIER_2 = Set.of(
                new Cost(BEAST_PELT, AetherIIItems.ARKENIUM_PLATES, 2, AetherIIItems.CORROBONITE_CRYSTAL, 1),
                new Cost(BURRUKAI_PLATE, AetherIIItems.ARKENIUM_PLATES, 2, AetherIIItems.CORROBONITE_CRYSTAL, 1),
                new Cost(SKYROOT, AetherIIItems.ARKENIUM_PLATES, 2, AetherIIItems.CORROBONITE_CRYSTAL, 1),
                new Cost(HOLYSTONE, AetherIIItems.ARKENIUM_PLATES, 2, AetherIIItems.CORROBONITE_CRYSTAL, 1),
                new Cost(ZANITE, AetherIIItems.ARKENIUM_PLATES, 3, AetherIIItems.CORROBONITE_CRYSTAL, 1),
                new Cost(ARKENIUM, AetherIIItems.ARKENIUM_PLATES, 3, AetherIIItems.CORROBONITE_CRYSTAL, 1),
                new Cost(GRAVITITE, AetherIIItems.ARKENIUM_PLATES, 4, AetherIIItems.CORROBONITE_CRYSTAL, 1)
        );
        public static final Set<Cost> TIER_3 = Set.of(
                new Cost(BEAST_PELT, AetherIIItems.ARKENIUM_PLATES, 4, AetherIIItems.CORROBONITE_CRYSTAL, 3),
                new Cost(BURRUKAI_PLATE, AetherIIItems.ARKENIUM_PLATES, 4, AetherIIItems.CORROBONITE_CRYSTAL, 3),
                new Cost(SKYROOT, AetherIIItems.ARKENIUM_PLATES, 4, AetherIIItems.CORROBONITE_CRYSTAL, 3),
                new Cost(HOLYSTONE, AetherIIItems.ARKENIUM_PLATES, 4, AetherIIItems.CORROBONITE_CRYSTAL, 3),
                new Cost(ZANITE, AetherIIItems.ARKENIUM_PLATES, 5, AetherIIItems.CORROBONITE_CRYSTAL, 3),
                new Cost(ARKENIUM, AetherIIItems.ARKENIUM_PLATES, 5, AetherIIItems.CORROBONITE_CRYSTAL, 3),
                new Cost(GRAVITITE, AetherIIItems.ARKENIUM_PLATES, 6, AetherIIItems.CORROBONITE_CRYSTAL, 3)
        );
        public static final Set<Cost> TIER_4 = Set.of(
                new Cost(ARKENIUM, AetherIIItems.ARKENIUM_PLATES, 6, AetherIIItems.CORROBONITE_CRYSTAL, 4)
        );

        private static Predicate<ItemStack> isTier(TagKey<Item> tier) {
            return (itemStack) -> itemStack.is(tier);
        }
    }
}
