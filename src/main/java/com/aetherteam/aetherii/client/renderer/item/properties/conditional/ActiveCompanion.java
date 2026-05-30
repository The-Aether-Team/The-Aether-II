package com.aetherteam.aetherii.client.renderer.item.properties.conditional;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public record ActiveCompanion() implements ConditionalItemModelProperty {
    public static final MapCodec<ActiveCompanion> MAP_CODEC = MapCodec.unit(new ActiveCompanion());

    @Override
    public boolean get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int i, ItemDisplayContext context) {
        return stack.has(AetherIIDataComponents.COMPANION_UUID) && !stack.has(AetherIIDataComponents.COMPANION_NBT);
    }

    @Override
    public MapCodec<ActiveCompanion> type() {
        return MAP_CODEC;
    }
}
