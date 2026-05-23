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
}
