package com.aetherteam.aetherii.item.components;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.item.AetherIIItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

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

    //todo the item tier upgrading system; need to upgrade:
    //  base attack damage/typed damage - attributes
    //  base attack speed - attributes
    //  mining tool tier - tool component
    //  mining speed - tool component
    //  armor level - attributes
    //  armor toughness - attributes

    public record Stats(Predicate<ItemStack> stackCondition, Charms charms, UpgradeInfo upgrades) {
        public static final Predicate<ItemStack> DEFAULT = (stack) -> true;
        public static final Predicate<ItemStack> TOOLS = (stack) -> stack.is(AetherIITags.Items.ACCEPTS_CHARMS_TOOLS) && !(stack.is(AetherIITags.Items.ARKENIUM_TOOL) || stack.is(AetherIITags.Items.ARKENIUM_ARMOR));
        public static final Predicate<ItemStack> WEAPONS = (stack) -> stack.is(AetherIITags.Items.ACCEPTS_CHARMS_WEAPONS) && !(stack.is(AetherIITags.Items.ARKENIUM_TOOL) || stack.is(AetherIITags.Items.ARKENIUM_ARMOR));
        public static final Predicate<ItemStack> ARMOR = (stack) -> stack.is(AetherIITags.Items.ACCEPTS_CHARMS_ARMOR) && !(stack.is(AetherIITags.Items.ARKENIUM_TOOL) || stack.is(AetherIITags.Items.ARKENIUM_ARMOR));
        public static final Predicate<ItemStack> ARKENIUM_TOOLS = (stack) -> stack.is(AetherIITags.Items.ACCEPTS_CHARMS_TOOLS) && (stack.is(AetherIITags.Items.ARKENIUM_TOOL) || stack.is(AetherIITags.Items.ARKENIUM_ARMOR));
        public static final Predicate<ItemStack> ARKENIUM_WEAPONS = (stack) -> stack.is(AetherIITags.Items.ACCEPTS_CHARMS_WEAPONS) && (stack.is(AetherIITags.Items.ARKENIUM_TOOL) || stack.is(AetherIITags.Items.ARKENIUM_ARMOR));
        public static final Predicate<ItemStack> ARKENIUM_ARMOR = (stack) -> stack.is(AetherIITags.Items.ACCEPTS_CHARMS_ARMOR) && (stack.is(AetherIITags.Items.ARKENIUM_TOOL) || stack.is(AetherIITags.Items.ARKENIUM_ARMOR));

        public static final Map<Supplier<? extends Item>, Supplier<? extends Item>> UPGRADE_REFERENCE = Map.ofEntries(
                Map.entry(AetherIIItems.SKYROOT_PICKAXE, AetherIIItems.HOLYSTONE_PICKAXE),
                Map.entry(AetherIIItems.SKYROOT_AXE, AetherIIItems.HOLYSTONE_AXE),
                Map.entry(AetherIIItems.SKYROOT_SHOVEL, AetherIIItems.HOLYSTONE_SHOVEL),
                Map.entry(AetherIIItems.SKYROOT_TROWEL, AetherIIItems.HOLYSTONE_TROWEL),
                Map.entry(AetherIIItems.HOLYSTONE_PICKAXE, AetherIIItems.ZANITE_PICKAXE),
                Map.entry(AetherIIItems.HOLYSTONE_AXE, AetherIIItems.ZANITE_AXE),
                Map.entry(AetherIIItems.HOLYSTONE_SHOVEL, AetherIIItems.ZANITE_SHOVEL),
                Map.entry(AetherIIItems.HOLYSTONE_TROWEL, AetherIIItems.ZANITE_TROWEL),
                Map.entry(AetherIIItems.ZANITE_PICKAXE, AetherIIItems.GRAVITITE_PICKAXE),
                Map.entry(AetherIIItems.ZANITE_AXE, AetherIIItems.GRAVITITE_AXE),
                Map.entry(AetherIIItems.ZANITE_SHOVEL, AetherIIItems.GRAVITITE_SHOVEL),
                Map.entry(AetherIIItems.ZANITE_TROWEL, AetherIIItems.GRAVITITE_TROWEL),
                Map.entry(AetherIIItems.ARKENIUM_PICKAXE, AetherIIItems.GRAVITITE_PICKAXE),
                Map.entry(AetherIIItems.ARKENIUM_AXE, AetherIIItems.GRAVITITE_AXE),
                Map.entry(AetherIIItems.ARKENIUM_SHOVEL, AetherIIItems.GRAVITITE_SHOVEL),
                Map.entry(AetherIIItems.ARKENIUM_TROWEL, AetherIIItems.GRAVITITE_TROWEL),
                Map.entry(AetherIIItems.GRAVITITE_PICKAXE, () -> Items.NETHERITE_PICKAXE),
                Map.entry(AetherIIItems.GRAVITITE_AXE, () -> Items.NETHERITE_AXE),
                Map.entry(AetherIIItems.GRAVITITE_SHOVEL, () -> Items.NETHERITE_SHOVEL),
                Map.entry(AetherIIItems.GRAVITITE_TROWEL, () -> Items.NETHERITE_HOE),

                Map.entry(AetherIIItems.SKYROOT_SHORTSWORD, AetherIIItems.HOLYSTONE_SHORTSWORD),
                Map.entry(AetherIIItems.SKYROOT_HAMMER, AetherIIItems.HOLYSTONE_HAMMER),
                Map.entry(AetherIIItems.SKYROOT_SPEAR, AetherIIItems.HOLYSTONE_SPEAR),
                Map.entry(AetherIIItems.HOLYSTONE_SHORTSWORD, AetherIIItems.ZANITE_SHORTSWORD),
                Map.entry(AetherIIItems.HOLYSTONE_HAMMER, AetherIIItems.ZANITE_HAMMER),
                Map.entry(AetherIIItems.HOLYSTONE_SPEAR, AetherIIItems.ZANITE_SPEAR),
                Map.entry(AetherIIItems.ZANITE_SHORTSWORD, AetherIIItems.GRAVITITE_SHORTSWORD),
                Map.entry(AetherIIItems.ZANITE_HAMMER, AetherIIItems.GRAVITITE_HAMMER),
                Map.entry(AetherIIItems.ZANITE_SPEAR, AetherIIItems.GRAVITITE_SPEAR),
                Map.entry(AetherIIItems.ARKENIUM_SHORTSWORD, AetherIIItems.GRAVITITE_SHORTSWORD),
                Map.entry(AetherIIItems.ARKENIUM_HAMMER, AetherIIItems.GRAVITITE_HAMMER),
                Map.entry(AetherIIItems.ARKENIUM_SPEAR, AetherIIItems.GRAVITITE_SPEAR)
//                Map.entry(AetherIIItems.GRAVITITE_SHORTSWORD, AetherIIItems.GRAVITITE_SHORTSWORD), //TODO
//                Map.entry(AetherIIItems.GRAVITITE_HAMMER, AetherIIItems.GRAVITITE_HAMMER),
//                Map.entry(AetherIIItems.GRAVITITE_SPEAR, AetherIIItems.GRAVITITE_SPEAR),
        );


        public static final Set<Stats> TIER_1 = Set.of(
                new Stats(DEFAULT, new Charms(), new UpgradeInfo(
                        (oldStack, newStack, newTier) -> {
                            newStack.set(DataComponents.MAX_DAMAGE, oldStack.getMaxDamage() + 50);
                        },
                        (oldStack, newStack, newTier, baseComponent) -> {
                            baseComponent = durabilityTooltip(baseComponent, 50);
                            return baseComponent;
                        }))
        );
        public static final Set<Stats> TIER_2 = Set.of(
                new Stats(DEFAULT, new Charms(), new UpgradeInfo(
                        (oldStack, newStack, newTier) -> {
                            newStack.set(DataComponents.MAX_DAMAGE, oldStack.getMaxDamage() + 100);
                        },
                        (oldStack, newStack, newTier, baseComponent) -> {
                            baseComponent = durabilityTooltip(baseComponent, 100);
                            return baseComponent;
                        }))
        );
        public static final Set<Stats> TIER_3 = Set.of(
                new Stats(TOOLS, new Charms(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)), new UpgradeInfo(
                        (oldStack, newStack, newTier) -> {
                            newStack.set(DataComponents.MAX_DAMAGE, oldStack.getMaxDamage() + 150);
                            upgradeToolTier(oldStack, newStack);
                            upgradeAttributes(oldStack, newStack);
                            upgradeCharms(oldStack, newStack, newTier);
                            newStack.set(DataComponents.RARITY, AetherIIItems.AETHER_II_UPGRADED);
                        },
                        (oldStack, newStack, newTier, baseComponent) -> {
                            baseComponent = tierTooltip(baseComponent);
                            baseComponent = baseComponent.append(CommonComponents.NEW_LINE);
                            baseComponent = charmTooltip(baseComponent, 1, Charms.Tier.ONE.getValue());
                            baseComponent = baseComponent.append(CommonComponents.NEW_LINE);
                            baseComponent = durabilityTooltip(baseComponent, 150);
                            return baseComponent;
                        })),
                new Stats(WEAPONS, new Charms(new Charms.CharmHolder(Charms.Type.WEAPON, Charms.Tier.ONE)),  new UpgradeInfo(
                        (oldStack, newStack, newTier) -> {
                            newStack.set(DataComponents.MAX_DAMAGE, oldStack.getMaxDamage() + 150);
                            upgradeAttributes(oldStack, newStack);
                            upgradeCharms(oldStack, newStack, newTier);
                            newStack.set(DataComponents.RARITY, AetherIIItems.AETHER_II_UPGRADED);
                        },
                        (oldStack, newStack, newTier, baseComponent) -> {
                            baseComponent = tierTooltip(baseComponent);
                            baseComponent = baseComponent.append(CommonComponents.NEW_LINE);
                            baseComponent = charmTooltip(baseComponent, 1, Charms.Tier.ONE.getValue());
                            baseComponent = baseComponent.append(CommonComponents.NEW_LINE);
                            baseComponent = durabilityTooltip(baseComponent, 150);
                            return baseComponent;
                        })),
                new Stats(ARMOR, new Charms(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),  new UpgradeInfo(
                        (oldStack, newStack, newTier) -> {
                            newStack.set(DataComponents.MAX_DAMAGE, oldStack.getMaxDamage() + 150);
                            upgradeCharms(oldStack, newStack, newTier);
                            newStack.set(DataComponents.RARITY, AetherIIItems.AETHER_II_UPGRADED);
                        },
                        (oldStack, newStack, newTier, baseComponent) -> {
                            baseComponent = tierTooltip(baseComponent);
                            baseComponent = baseComponent.append(CommonComponents.NEW_LINE);
                            baseComponent = charmTooltip(baseComponent, 1, Charms.Tier.ONE.getValue());
                            baseComponent = baseComponent.append(CommonComponents.NEW_LINE);
                            baseComponent = durabilityTooltip(baseComponent, 150);
                            return baseComponent;
                        })),
                new Stats(ARKENIUM_TOOLS, new Charms(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),  new UpgradeInfo(
                        (oldStack, newStack, newTier) -> {
                            newStack.set(DataComponents.MAX_DAMAGE, oldStack.getMaxDamage() + 150);
                            upgradeCharms(oldStack, newStack, newTier);
                            newStack.set(DataComponents.RARITY, AetherIIItems.AETHER_II_UPGRADED);
                        },
                        (oldStack, newStack, newTier, baseComponent) -> {
                            baseComponent = charmTooltip(baseComponent, 1, Charms.Tier.ONE.getValue());
                            baseComponent = baseComponent.append(CommonComponents.NEW_LINE);
                            baseComponent = durabilityTooltip(baseComponent, 150);
                            return baseComponent;
                        })),
                new Stats(ARKENIUM_WEAPONS, new Charms(new Charms.CharmHolder(Charms.Type.WEAPON, Charms.Tier.ONE)),  new UpgradeInfo(
                        (oldStack, newStack, newTier) -> {
                            newStack.set(DataComponents.MAX_DAMAGE, oldStack.getMaxDamage() + 150);
                            upgradeCharms(oldStack, newStack, newTier);
                            newStack.set(DataComponents.RARITY, AetherIIItems.AETHER_II_UPGRADED);
                        },
                        (oldStack, newStack, newTier, baseComponent) -> {
                            baseComponent = charmTooltip(baseComponent, 1, Charms.Tier.ONE.getValue());
                            baseComponent = baseComponent.append(CommonComponents.NEW_LINE);
                            baseComponent = durabilityTooltip(baseComponent, 150);
                            return baseComponent;
                        })),
                new Stats(ARKENIUM_ARMOR, new Charms(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),  new UpgradeInfo(
                        (oldStack, newStack, newTier) -> {
                            newStack.set(DataComponents.MAX_DAMAGE, oldStack.getMaxDamage() + 150);
                            upgradeCharms(oldStack, newStack, newTier);
                            newStack.set(DataComponents.RARITY, AetherIIItems.AETHER_II_UPGRADED);
                        },
                        (oldStack, newStack, newTier, baseComponent) -> {
                            baseComponent = charmTooltip(baseComponent, 1, Charms.Tier.ONE.getValue());
                            baseComponent = baseComponent.append(CommonComponents.NEW_LINE);
                            baseComponent = durabilityTooltip(baseComponent, 150);
                            return baseComponent;
                        }))
        );
        public static final Set<Stats> TIER_4 = Set.of(
                new Stats(ARKENIUM_TOOLS, new Charms(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.TWO)),  new UpgradeInfo(
                        (oldStack, newStack, newTier) -> {
                            newStack.set(DataComponents.MAX_DAMAGE, oldStack.getMaxDamage() + 200);
                            upgradeToolTier(oldStack, newStack);
                            upgradeAttributes(oldStack, newStack);
                            upgradeCharms(oldStack, newStack, newTier);
                            newStack.set(DataComponents.RARITY, AetherIIItems.AETHER_II_UPGRADED);
                        },
                        (oldStack, newStack, newTier, baseComponent) -> {
                            baseComponent = tierTooltip(baseComponent);
                            baseComponent = baseComponent.append(CommonComponents.NEW_LINE);
                            baseComponent = charmTooltip(baseComponent, 1, Charms.Tier.TWO.getValue());
                            baseComponent = baseComponent.append(CommonComponents.NEW_LINE);
                            baseComponent = durabilityTooltip(baseComponent, 200);
                            return baseComponent;
                        })),
                new Stats(ARKENIUM_WEAPONS, new Charms(new Charms.CharmHolder(Charms.Type.WEAPON, Charms.Tier.TWO)),  new UpgradeInfo(
                        (oldStack, newStack, newTier) -> {
                            newStack.set(DataComponents.MAX_DAMAGE, oldStack.getMaxDamage() + 200);
                            upgradeAttributes(oldStack, newStack);
                            upgradeCharms(oldStack, newStack, newTier);
                            newStack.set(DataComponents.RARITY, AetherIIItems.AETHER_II_UPGRADED);
                        },
                        (oldStack, newStack, newTier, baseComponent) -> {
                            baseComponent = tierTooltip(baseComponent);
                            baseComponent = baseComponent.append(CommonComponents.NEW_LINE);
                            baseComponent = charmTooltip(baseComponent, 1, Charms.Tier.TWO.getValue());
                            baseComponent = baseComponent.append(CommonComponents.NEW_LINE);
                            baseComponent = durabilityTooltip(baseComponent, 200);
                            return baseComponent;
                        })),
                new Stats(ARKENIUM_ARMOR, new Charms(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO)),  new UpgradeInfo(
                        (oldStack, newStack, newTier) -> {
                            newStack.set(DataComponents.MAX_DAMAGE, oldStack.getMaxDamage() + 200);
                            upgradeCharms(oldStack, newStack, newTier);
                            newStack.set(DataComponents.RARITY, AetherIIItems.AETHER_II_UPGRADED);
                        },
                        (oldStack, newStack, newTier, baseComponent) -> {
                            baseComponent = tierTooltip(baseComponent);
                            baseComponent = baseComponent.append(CommonComponents.NEW_LINE);
                            baseComponent = charmTooltip(baseComponent, 1, Charms.Tier.TWO.getValue());
                            baseComponent = baseComponent.append(CommonComponents.NEW_LINE);
                            baseComponent = durabilityTooltip(baseComponent, 200);
                            return baseComponent;
                        }))
        );

        private static void upgradeToolTier(ItemStack oldStack, ItemStack newStack) {
            Item upgradeReference = null;
            for (Map.Entry<Supplier<? extends Item>, Supplier<? extends Item>> entry : UPGRADE_REFERENCE.entrySet()) {
                if (entry.getKey().get() == oldStack.getItem()) {
                    upgradeReference = entry.getValue().get();
                    break;
                }
            }
            if (upgradeReference != null) {
                Tool tool = upgradeReference.getDefaultInstance().get(DataComponents.TOOL);
                if (tool != null) {
                    newStack.set(DataComponents.TOOL, tool);
                }
            }
        }

        private static void upgradeAttributes(ItemStack oldStack, ItemStack newStack) {
            Item upgradeReference = null;
            for (Map.Entry<Supplier<? extends Item>, Supplier<? extends Item>> entry : UPGRADE_REFERENCE.entrySet()) {
                if (entry.getKey().get() == oldStack.getItem()) {
                    upgradeReference = entry.getValue().get();
                    break;
                }
            }
            if (upgradeReference != null) {
                ItemAttributeModifiers modifiers = upgradeReference.getDefaultInstance().get(DataComponents.ATTRIBUTE_MODIFIERS);
                if (modifiers != null) {
                    newStack.set(DataComponents.ATTRIBUTE_MODIFIERS, modifiers);
                }
            }
        }

        private static void upgradeCharms(ItemStack oldStack, ItemStack newStack, ReinforcementTier newTier) {
            ReinforcementTier currentReinforcementTier = oldStack.get(AetherIIDataComponents.REINFORCEMENT_TIER);
            ReinforcementTier.Stats newStats = newTier.getStat(oldStack);
            Charms newCharms = null;
            if (newStats != null) {
                Charms newStatCharms = newStats.charms();

                if (newStatCharms != null) {
                    Charms charms = oldStack.get(AetherIIDataComponents.CHARMS);
                    newCharms = new Charms();

                    if (charms != null) {
                        List<Charms.CharmHolder> currentCharmHolders = charms.charmHolders();
                        List<Charms.CharmHolder> newStatCharmHolders = newStatCharms.charmHolders();
                        List<Charms.CharmHolder> newCharmHolders = newCharms.charmHolders();
                        List<Charms.CharmHolder> currentStatCharmHolders = List.of();
                        if (currentReinforcementTier != null) {
                            ReinforcementTier.Stats currentStats = currentReinforcementTier.getStat(oldStack);
                            if (currentStats != null) {
                                currentStatCharmHolders = currentStats.charms().charmHolders();
                            }
                        }

                        int baseSize = currentCharmHolders.size() - currentStatCharmHolders.size();
                        int size = baseSize + newStatCharmHolders.size();
                        for (int i = 0; i < size; i++) {
                            if (i < baseSize) {
                                newCharmHolders.add(i, new Charms.CharmHolder(currentCharmHolders.get(i)));
                            } else {
                                Charms.CharmHolder newStatCharmHolder = newStatCharmHolders.get(i - baseSize);
                                if (i < currentCharmHolders.size()) {
                                    Charms.CharmHolder currentCharmHolder = currentCharmHolders.get(i);
                                    newCharmHolders.add(i, new Charms.CharmHolder(newStatCharmHolder.getType(), newStatCharmHolder.getTier(), currentCharmHolder.getStack()));
                                } else {
                                    newCharmHolders.add(i, newStatCharmHolder);
                                }
                            }
                        }
                    } else {
                        newCharms = newStatCharms;
                    }
                }
            }
            if (newCharms != null) {
                newStack.set(AetherIIDataComponents.CHARMS, newCharms);
            }
        }

        public static MutableComponent durabilityTooltip(MutableComponent baseComponent, int value) {
            return baseComponent.append(Component.translatable("gui.aether_ii.arkenium_forge.tooltip.durability", Component.literal(String.valueOf(value))).withStyle(ChatFormatting.GRAY));
        }

        public static MutableComponent charmTooltip(MutableComponent baseComponent, int amount, int tier) {
            if (amount > 1) {
                return baseComponent.append(Component.translatable("gui.aether_ii.arkenium_forge.tooltip.charms", Component.literal(String.valueOf(amount)), Charms.createCharmTierComponent(tier)).withStyle(ChatFormatting.GRAY));
            } else {
                return baseComponent.append(Component.translatable("gui.aether_ii.arkenium_forge.tooltip.charm", Component.literal(String.valueOf(amount)), Charms.createCharmTierComponent(tier)).withStyle(ChatFormatting.GRAY));
            }
        }

        public static MutableComponent tierTooltip(MutableComponent baseComponent) {
            return baseComponent.append(Component.translatable("gui.aether_ii.arkenium_forge.tooltip.tier").withStyle(ChatFormatting.GRAY));
        }

        public record UpgradeInfo(UpgradeFunction upgradeFunction, TooltipFunction tooltipFunction) { }

        @FunctionalInterface
        public interface UpgradeFunction {
            void updateComponents(ItemStack oldStack, ItemStack newStack, ReinforcementTier newTier);
        }

        @FunctionalInterface
        public interface TooltipFunction {
            MutableComponent createTooltip(ItemStack oldStack, ItemStack newStack, ReinforcementTier newTier, MutableComponent baseComponent);
        }
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
