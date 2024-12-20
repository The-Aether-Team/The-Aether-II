package com.aetherteam.aetherii.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Optional;

public interface CanisterPickup {
    ItemStack pickupBlockWithCanister(@Nullable Player var1, LevelAccessor var2, BlockPos var3, BlockState var4);

    Optional<SoundEvent> getCanisterPickupSound(BlockState state);
}

