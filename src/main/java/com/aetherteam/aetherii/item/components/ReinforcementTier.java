package com.aetherteam.aetherii.item.components;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.api.ItemReinforcement;
import com.aetherteam.aetherii.data.resources.registries.AetherIIItemReinforcements;
import com.aetherteam.aetherii.item.AetherIIItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.core.RegistryAccess;
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
    FIRST(1),
    SECOND(2),
    THIRD(3),
    FOURTH(4);

    private static final IntFunction<ReinforcementTier> BY_ID = ByIdMap.continuous(ReinforcementTier::id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    public static final StringRepresentable.EnumCodec<ReinforcementTier> CODEC = StringRepresentable.fromEnum(ReinforcementTier::values);
    public static final StreamCodec<ByteBuf, ReinforcementTier> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, ReinforcementTier::id);

    private final int tier;

    ReinforcementTier(int tier) {
        this.tier = tier;
    }

    public int getTierNumber() {
        return this.tier;
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

    public static int getTierCount(RegistryAccess registryAccess, ItemStack stack) {
        int count = 0;
        ItemReinforcement reinforcement = AetherIIItemReinforcements.get(registryAccess, stack);
        if (reinforcement != null) {
            count = reinforcement.upgrades().length;
        }
        return count;
    }

    public static boolean isItemAtMaxTier(RegistryAccess registryAccess, ItemStack itemStack) {
        int max = getTierCount(registryAccess, itemStack);
        ReinforcementTier tier = itemStack.get(AetherIIDataComponents.REINFORCEMENT_TIER);
        return tier != null && tier.getTierNumber() == max;
    }

    public static int getTierForItem(RegistryAccess registryAccess, ItemStack itemStack) {
        int max = getTierCount(registryAccess, itemStack);
        ReinforcementTier tier = itemStack.get(AetherIIDataComponents.REINFORCEMENT_TIER);
        if (tier != null) {
            return Math.min(tier.getTierNumber(), max);
        } else {
            return 0;
        }
    }

    @Nullable
    public static ItemReinforcement.Cost getCostForTier(RegistryAccess registryAccess, ItemStack stack, int tier) {
        ItemReinforcement reinforcement = AetherIIItemReinforcements.get(registryAccess, stack);
        if (reinforcement != null) {
            return reinforcement.upgrades()[tier - 1].cost();
        }
        return null;
    }

    public static int getPrimaryCostForTier(RegistryAccess registryAccess, ItemStack stack, int tier) {
        ItemReinforcement.Cost initialCost = getCostForTier(registryAccess, stack, tier);
        if (initialCost != null) {
            int cost = initialCost.primaryCost().count();
            int minimumTier = getTierForItem(registryAccess, stack);
            for (int i = tier - 1; i > minimumTier; i--) {
                ItemReinforcement.Cost costForTier = getCostForTier(registryAccess, stack, i);
                if (costForTier != null) {
                    cost += costForTier.primaryCost().count();
                }
            }
            return cost;
        }
        return -1;
    }

    public static int getSecondaryCostForTier(RegistryAccess registryAccess, ItemStack stack, int tier) {
        ItemReinforcement.Cost initialCost = getCostForTier(registryAccess, stack, tier);
        if (initialCost != null) {
            int cost = initialCost.secondaryCost().isPresent() ? initialCost.secondaryCost().get().count() : 0;
            int minimumTier = getTierForItem(registryAccess, stack);
            for (int i = tier - 1; i > minimumTier; i--) {
                ItemReinforcement.Cost costForTier = getCostForTier(registryAccess, stack, i);
                if (costForTier != null && costForTier.secondaryCost().isPresent()) {
                    cost += costForTier.secondaryCost().get().count();
                }
            }
            return cost;
        }
        return -1;
    }

//    public record Stats(Predicate<ItemStack> stackCondition, Charms charms, UpgradeInfo upgrades) {
//        public static final Map<Supplier<? extends Item>, Supplier<ItemStack>> UPGRADE_REFERENCE = Map.ofEntries(
//                Map.entry(AetherIIItems.BEAST_PELT_BOOTS, () -> AetherIIItems.BURRUKAI_PLATE_BOOTS.get().getDefaultInstance()),
//                Map.entry(AetherIIItems.BEAST_PELT_LEGGINGS, () -> AetherIIItems.BURRUKAI_PLATE_LEGGINGS.get().getDefaultInstance()),
//                Map.entry(AetherIIItems.BEAST_PELT_CHESTPLATE, () -> AetherIIItems.BURRUKAI_PLATE_CHESTPLATE.get().getDefaultInstance()),
//                Map.entry(AetherIIItems.BEAST_PELT_HELMET, () -> AetherIIItems.BURRUKAI_PLATE_HELMET.get().getDefaultInstance()),
//                Map.entry(AetherIIItems.BEAST_PELT_GLOVES, () -> AetherIIItems.BURRUKAI_PLATE_GLOVES.get().getDefaultInstance()),
//
//                Map.entry(AetherIIItems.BURRUKAI_PLATE_BOOTS, () -> AetherIIItems.ZANITE_BOOTS.get().getDefaultInstance()),
//                Map.entry(AetherIIItems.BURRUKAI_PLATE_LEGGINGS, () -> AetherIIItems.ZANITE_LEGGINGS.get().getDefaultInstance()),
//                Map.entry(AetherIIItems.BURRUKAI_PLATE_CHESTPLATE, () -> AetherIIItems.ZANITE_CHESTPLATE.get().getDefaultInstance()),
//                Map.entry(AetherIIItems.BURRUKAI_PLATE_HELMET, () -> AetherIIItems.ZANITE_HELMET.get().getDefaultInstance()),
//                Map.entry(AetherIIItems.BURRUKAI_PLATE_GLOVES, () -> AetherIIItems.ZANITE_GLOVES.get().getDefaultInstance()),
//
//                Map.entry(AetherIIItems.ZANITE_BOOTS, () -> AetherIIItems.ARKENIUM_BOOTS.get().getDefaultInstance()),
//                Map.entry(AetherIIItems.ZANITE_LEGGINGS, () -> AetherIIItems.ARKENIUM_LEGGINGS.get().getDefaultInstance()),
//                Map.entry(AetherIIItems.ZANITE_CHESTPLATE, () -> AetherIIItems.ARKENIUM_CHESTPLATE.get().getDefaultInstance()),
//                Map.entry(AetherIIItems.ZANITE_HELMET, () -> AetherIIItems.ARKENIUM_HELMET.get().getDefaultInstance()),
//                Map.entry(AetherIIItems.ZANITE_GLOVES, () -> AetherIIItems.ARKENIUM_GLOVES.get().getDefaultInstance()),
//
//                Map.entry(AetherIIItems.ARKENIUM_BOOTS, () -> AetherIIItems.GRAVITITE_BOOTS.get().getDefaultInstance()),
//                Map.entry(AetherIIItems.ARKENIUM_LEGGINGS, () -> AetherIIItems.GRAVITITE_LEGGINGS.get().getDefaultInstance()),
//                Map.entry(AetherIIItems.ARKENIUM_CHESTPLATE, () -> AetherIIItems.GRAVITITE_CHESTPLATE.get().getDefaultInstance()),
//                Map.entry(AetherIIItems.ARKENIUM_HELMET, () -> AetherIIItems.GRAVITITE_HELMET.get().getDefaultInstance()),
//                Map.entry(AetherIIItems.ARKENIUM_GLOVES, () -> AetherIIItems.GRAVITITE_GLOVES.get().getDefaultInstance()),
//
//                Map.entry(AetherIIItems.GRAVITITE_BOOTS, () -> Items.NETHERITE_BOOTS.getDefaultInstance()),
//                Map.entry(AetherIIItems.GRAVITITE_LEGGINGS, () -> Items.NETHERITE_LEGGINGS.getDefaultInstance()),
//                Map.entry(AetherIIItems.GRAVITITE_CHESTPLATE, () -> Items.NETHERITE_CHESTPLATE.getDefaultInstance()),
//                Map.entry(AetherIIItems.GRAVITITE_HELMET, () -> Items.NETHERITE_HELMET.getDefaultInstance()),
//                Map.entry(AetherIIItems.GRAVITITE_GLOVES, () -> AetherIIItems.ZANITE_PENDANT.get().getDefaultInstance()),
//
//                Map.entry(AetherIIItems.ZANITE_PENDANT, () -> AetherIIItems.ZANITE_PENDANT.get().getDefaultInstance()),
//                Map.entry(AetherIIItems.ICESTONE_PENDANT, () -> AetherIIItems.ICESTONE_PENDANT.get().getDefaultInstance())
//        );
//        public static final Set<Stats> TIER_3 = Set.of(
//                new Stats(ARMOR, new Charms(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),  new UpgradeInfo(
//                        (oldStack, newStack, newTier) -> {
//                            newStack.set(DataComponents.MAX_DAMAGE, oldStack.getMaxDamage() + 150);
//                            upgradeAttributes(oldStack, newStack);
//                            upgradeCharms(oldStack, newStack, newTier);
//                            newStack.set(DataComponents.RARITY, AetherIIItems.AETHER_II_UPGRADED);
//                        },
//                        (oldStack, newStack, newTier, baseComponent) -> {
//                            baseComponent = tierTooltip(baseComponent);
//                            baseComponent = baseComponent.append(CommonComponents.NEW_LINE);
//                            baseComponent = charmTooltip(baseComponent, 1, Charms.Tier.ONE);
//                            baseComponent = baseComponent.append(CommonComponents.NEW_LINE);
//                            baseComponent = durabilityTooltip(baseComponent, 150);
//                            return baseComponent;
//                        })),
//                new Stats(ARKENIUM_ARMOR, new Charms(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),  new UpgradeInfo(
//                        (oldStack, newStack, newTier) -> {
//                            newStack.set(DataComponents.MAX_DAMAGE, oldStack.getMaxDamage() + 150);
//                            upgradeCharms(oldStack, newStack, newTier);
//                        },
//                        (oldStack, newStack, newTier, baseComponent) -> {
//                            baseComponent = charmTooltip(baseComponent, 1, Charms.Tier.ONE);
//                            baseComponent = baseComponent.append(CommonComponents.NEW_LINE);
//                            baseComponent = durabilityTooltip(baseComponent, 150);
//                            return baseComponent;
//                        }))
//        );
//        public static final Set<Stats> TIER_4 = Set.of(
//                new Stats(ARKENIUM_ARMOR, new Charms(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO)),  new UpgradeInfo(
//                        (oldStack, newStack, newTier) -> {
//                            newStack.set(DataComponents.MAX_DAMAGE, oldStack.getMaxDamage() + 200);
//                            upgradeAttributes(oldStack, newStack);
//                            upgradeCharms(oldStack, newStack, newTier);
//                            newStack.set(DataComponents.RARITY, AetherIIItems.AETHER_II_UPGRADED);
//                        },
//                        (oldStack, newStack, newTier, baseComponent) -> {
//                            baseComponent = tierTooltip(baseComponent);
//                            baseComponent = baseComponent.append(CommonComponents.NEW_LINE);
//                            baseComponent = charmTooltip(baseComponent, 1, Charms.Tier.TWO);
//                            baseComponent = baseComponent.append(CommonComponents.NEW_LINE);
//                            baseComponent = durabilityTooltip(baseComponent, 200);
//                            return baseComponent;
//                        }))
//        );
//
//        public static ItemStack stackWithAddedStats(Item item, ItemAttributeModifiers.Entry... entries) {
//            ItemStack stack = item.getDefaultInstance();
//            ItemAttributeModifiers oldModifiers = stack.getAttributeModifiers();
//            ItemAttributeModifiers newModifiers = ItemAttributeModifiers.EMPTY;
//            for (ItemAttributeModifiers.Entry oldEntry : oldModifiers.modifiers()) {
//                ItemAttributeModifiers.Entry newEntry = oldEntry;
//                for (ItemAttributeModifiers.Entry entry : entries) {
//                    if (oldEntry.matches(entry.attribute(), entry.modifier().id())) {
//                        newEntry = entry;
//                    }
//                }
//                newModifiers = newModifiers.withModifierAdded(newEntry.attribute(), newEntry.modifier(), newEntry.slot());
//            }
//            stack.set(DataComponents.ATTRIBUTE_MODIFIERS, newModifiers);
//            return stack;
//        }
//
//        private static void upgradeCharms(ItemStack oldStack, ItemStack newStack, ReinforcementTier newTier) {
//            ReinforcementTier currentReinforcementTier = oldStack.get(AetherIIDataComponents.REINFORCEMENT_TIER);
//            ReinforcementTier.Stats newStats = newTier.getStat(oldStack);
//            Charms newCharms = null;
//            if (newStats != null) {
//                Charms newStatCharms = newStats.charms();
//
//                if (newStatCharms != null) {
//                    Charms charms = oldStack.get(AetherIIDataComponents.CHARMS);
//                    newCharms = new Charms();
//
//                    if (charms != null) {
//                        List<Charms.CharmHolder> currentCharmHolders = charms.charmHolders();
//                        List<Charms.CharmHolder> newStatCharmHolders = newStatCharms.charmHolders();
//                        List<Charms.CharmHolder> newCharmHolders = newCharms.charmHolders();
//                        List<Charms.CharmHolder> currentStatCharmHolders = List.of();
//                        if (currentReinforcementTier != null) {
//                            ReinforcementTier.Stats currentStats = currentReinforcementTier.getStat(oldStack);
//                            if (currentStats != null) {
//                                currentStatCharmHolders = currentStats.charms().charmHolders();
//                            }
//                        }
//
//                        int baseSize = currentCharmHolders.size() - currentStatCharmHolders.size();
//                        int size = baseSize + newStatCharmHolders.size();
//                        for (int i = 0; i < size; i++) {
//                            if (i < baseSize) {
//                                newCharmHolders.add(i, new Charms.CharmHolder(currentCharmHolders.get(i)));
//                            } else {
//                                Charms.CharmHolder newStatCharmHolder = newStatCharmHolders.get(i - baseSize);
//                                if (i < currentCharmHolders.size()) {
//                                    Charms.CharmHolder currentCharmHolder = currentCharmHolders.get(i);
//                                    newCharmHolders.add(i, new Charms.CharmHolder(newStatCharmHolder.getType(), newStatCharmHolder.getTier(), currentCharmHolder.getStack()));
//                                } else {
//                                    newCharmHolders.add(i, newStatCharmHolder);
//                                }
//                            }
//                        }
//                    } else {
//                        newCharms = newStatCharms;
//                    }
//                }
//            }
//            if (newCharms != null) {
//                newStack.set(AetherIIDataComponents.CHARMS, newCharms);
//            }
//        }
//    }
//
//    public record Cost(Predicate<ItemStack> stackCondition, ItemLike primaryMaterial, int primaryCount, ItemLike secondaryMaterial, int secondaryCount) {
//        public static final Set<Cost> TIER_1 = Set.of(
//                new Cost(BEAST_PELT, AetherIIItems.ARKENIUM_PLATE, 1, Items.AIR, 0),
//                new Cost(BURRUKAI_PLATE, AetherIIItems.ARKENIUM_PLATE, 1, Items.AIR, 0),
//                new Cost(ZANITE, AetherIIItems.ARKENIUM_PLATE, 2, Items.AIR, 0),
//                new Cost(ARKENIUM, AetherIIItems.ARKENIUM_PLATE, 2, Items.AIR, 0),
//                new Cost(GRAVITITE, AetherIIItems.ARKENIUM_PLATE, 3, Items.AIR, 0),
//                new Cost(PENDANT, AetherIIItems.ARKENIUM_PLATE, 2, Items.AIR, 0)
//        );
//        public static final Set<Cost> TIER_2 = Set.of(
//                new Cost(BEAST_PELT, AetherIIItems.ARKENIUM_PLATE, 2, AetherIIItems.CORROBONITE_CRYSTAL, 1),
//                new Cost(BURRUKAI_PLATE, AetherIIItems.ARKENIUM_PLATE, 2, AetherIIItems.CORROBONITE_CRYSTAL, 1),
//                new Cost(ZANITE, AetherIIItems.ARKENIUM_PLATE, 3, AetherIIItems.CORROBONITE_CRYSTAL, 1),
//                new Cost(ARKENIUM, AetherIIItems.ARKENIUM_PLATE, 3, AetherIIItems.CORROBONITE_CRYSTAL, 1),
//                new Cost(GRAVITITE, AetherIIItems.ARKENIUM_PLATE, 4, AetherIIItems.CORROBONITE_CRYSTAL, 1),
//                new Cost(PENDANT, AetherIIItems.ARKENIUM_PLATE, 3, AetherIIItems.CORROBONITE_CRYSTAL, 1)
//        );
//        public static final Set<Cost> TIER_3 = Set.of(
//                new Cost(BEAST_PELT, AetherIIItems.ARKENIUM_PLATE, 4, AetherIIItems.CORROBONITE_CRYSTAL, 3),
//                new Cost(BURRUKAI_PLATE, AetherIIItems.ARKENIUM_PLATE, 4, AetherIIItems.CORROBONITE_CRYSTAL, 3),
//                new Cost(ZANITE, AetherIIItems.ARKENIUM_PLATE, 5, AetherIIItems.CORROBONITE_CRYSTAL, 3),
//                new Cost(ARKENIUM, AetherIIItems.ARKENIUM_PLATE, 5, AetherIIItems.CORROBONITE_CRYSTAL, 3),
//                new Cost(GRAVITITE, AetherIIItems.ARKENIUM_PLATE, 6, AetherIIItems.CORROBONITE_CRYSTAL, 3),
//                new Cost(PENDANT, AetherIIItems.ARKENIUM_PLATE, 5, AetherIIItems.CORROBONITE_CRYSTAL, 3)
//        );
//        public static final Set<Cost> TIER_4 = Set.of(
//                new Cost(ARKENIUM, AetherIIItems.ARKENIUM_PLATE, 6, AetherIIItems.CORROBONITE_CRYSTAL, 4)
//        );
//    }
}
