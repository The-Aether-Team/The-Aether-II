package com.aetherteam.aetherii.api.entity;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public interface CustomPickItemEntity {
    @Nullable
    ItemStack getPickResult(ServerPlayer player, boolean includeData);
}
