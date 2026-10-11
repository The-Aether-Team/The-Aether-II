package com.aetherteam.aetherii.client.renderer.item.properties.conditional;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public record LassoThrow() implements ConditionalItemModelProperty {
    public static final MapCodec<LassoThrow> MAP_CODEC = MapCodec.unit(new LassoThrow());

    @Override
    public boolean get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int i, ItemDisplayContext context) {
        if (entity instanceof Player player) {
            return !Leashable.leashableLeashedTo(player).isEmpty();
        }
        return false;
    }

    @Override
    public MapCodec<LassoThrow> type() {
        return MAP_CODEC;
    }
}
