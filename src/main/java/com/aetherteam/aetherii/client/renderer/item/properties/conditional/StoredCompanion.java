package com.aetherteam.aetherii.client.renderer.item.properties.conditional;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public record StoredCompanion() implements ConditionalItemModelProperty {
    public static final MapCodec<StoredCompanion> MAP_CODEC = MapCodec.unit(new StoredCompanion());

    @Override
    public boolean get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int i, ItemDisplayContext context) {
        return stack.has(AetherIIDataComponents.COMPANION_NBT);
    }

    @Override
    public MapCodec<StoredCompanion> type() {
        return MAP_CODEC;
    }
}
