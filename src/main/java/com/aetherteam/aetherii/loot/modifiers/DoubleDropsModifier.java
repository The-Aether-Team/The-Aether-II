package com.aetherteam.aetherii.loot.modifiers;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.item.EquipmentUtil;
import com.aetherteam.aetherii.item.combat.abilities.SkyrootWeapon;
import com.aetherteam.aetherii.item.tools.abilities.SkyrootTool;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class DoubleDropsModifier extends LootModifier {
    public static final MapCodec<DoubleDropsModifier> CODEC = RecordCodecBuilder.mapCodec((instance) -> LootModifier.codecStart(instance).apply(instance, DoubleDropsModifier::new));

    public DoubleDropsModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    @Override
    public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> lootStacks, LootContext context) {
        ObjectArrayList<ItemStack> newStacks = new ObjectArrayList<>(lootStacks);

        // Tools
        BlockState targetState = context.getParamOrNull(LootContextParams.BLOCK_STATE);
        ItemStack tool = context.getParamOrNull(LootContextParams.TOOL);

        // Weapons
        Entity targetEntity = context.getParamOrNull(LootContextParams.THIS_ENTITY);
        Entity attacker = context.getParamOrNull(LootContextParams.DIRECT_ATTACKING_ENTITY);

        if (targetState != null) {
            if (tool != null && tool.getItem() instanceof SkyrootTool) {
                this.doubleDrops(lootStacks, newStacks, context.getRandom());
            }
        } else if (targetEntity != null) {
            if (attacker instanceof LivingEntity livingEntity && EquipmentUtil.isFullStrength(livingEntity) && livingEntity.getMainHandItem().getItem() instanceof SkyrootWeapon && !targetEntity.getType().is(AetherIITags.Entities.NO_DOUBLE_DROPS)) {
                this.doubleDrops(lootStacks, newStacks, context.getRandom());
            }
        }
        return newStacks;
    }

    private void doubleDrops(ObjectArrayList<ItemStack> lootStacks, ObjectArrayList<ItemStack> newStacks, RandomSource random) {
        for (ItemStack stack : lootStacks) {
            if (stack.is(AetherIITags.Items.DOUBLE_DROPS)) {
                boolean shouldDouble = stack.getItem() instanceof BlockItem ? random.nextInt(5) > 3 : random.nextInt(5) > 2;
                if (shouldDouble) {
                    newStacks.add(stack);
                }
            }
        }
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return DoubleDropsModifier.CODEC;
    }
}
