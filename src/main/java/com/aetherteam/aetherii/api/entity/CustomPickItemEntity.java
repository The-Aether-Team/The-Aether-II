package com.aetherteam.aetherii.api.entity;

import javax.annotation.Nullable;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public interface CustomPickItemEntity {
    @Nullable
    ItemStack getPickResult(ServerPlayer player, boolean includeData);
}
