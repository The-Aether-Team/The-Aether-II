package com.aetherteam.aetherii.mixin.wrappers.common;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;

public interface ItemCooldownsWrapper {
    ItemCooldowns aether_ii$setPlayer(Player player);
}
