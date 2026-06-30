package com.aetherteam.aetherii.client.renderer.item.properties.conditional;

import com.aetherteam.aetherii.entity.FakeShiftEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public record HoldingShift() {
    public static final MapCodec<HoldingShift> MAP_CODEC = MapCodec.unit(new HoldingShift());

    public boolean get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int i, ItemDisplayContext context) {
        if (entity instanceof FakeShiftEntity && ((FakeShiftEntity) entity).isFakeShift()) {
            return true;
        }

        return entity != null && entity.isShiftKeyDown();
    }

    public MapCodec<HoldingShift> type() {
        return MAP_CODEC;
    }
}
