package com.aetherteam.aetherii.loot.conditions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.HolderSetCodec;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import java.util.Set;

public record TierCompare(HolderSet<Item> items) implements LootItemCondition {
    public static final MapCodec<TierCompare> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            HolderSetCodec.create(Registries.ITEM, Item.CODEC, false).fieldOf("items").forGetter(TierCompare::items)
    ).apply(instance, TierCompare::new));

    @Override
    public LootItemConditionType getType() {
        return AetherIILootConditions.TIER_COMPARE.get();
    }

    @Override
    public Set<ContextKey<?>> getReferencedContextParams() {
        return Set.of(LootContextParams.TOOL);
    }

    @Override
    public boolean test(LootContext context) {
        ItemStack stack = context.getOptionalParameter(LootContextParams.TOOL);
        if (stack != null) {
            return compareStack(stack, this.items());
        }
        return false;
    }

    public static boolean compareStack(ItemStack stack, HolderSet<Item> items) {
        Tool thisTool = stack.get(DataComponents.TOOL);
        if (thisTool != null) {
            for (Holder<Item> holder : items) {
                Tool otherTool = holder.value().getDefaultInstance().get(DataComponents.TOOL);
                if (otherTool != null && otherTool.rules().equals(thisTool.rules())) {
                    return true;
                }
            }
        }
        return false;
    }
}
