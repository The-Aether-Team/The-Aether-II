package com.aetherteam.aetherii.mixin.mixins.common.accessor;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ServerPlayer.class)
public interface ServerPlayerAccessor {
    @Invoker
    ItemEntity callCreateItemStackToDrop(ItemStack droppedItem, boolean dropAround, boolean includeThrowerName);
}
