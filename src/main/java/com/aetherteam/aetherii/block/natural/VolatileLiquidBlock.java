package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.fluid.CanisterFluid;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class VolatileLiquidBlock extends LiquidBlock implements CanisterPickup {
    public VolatileLiquidBlock(FlowingFluid fluid, Properties properties) {
        super(fluid, properties);
    }

    @Override
    public ItemStack pickupBlock(@Nullable Player player, LevelAccessor level, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack pickupBlockWithCanister(@Nullable Player player, LevelAccessor level, BlockPos pos, BlockState state) {
        if (state.getValue(LEVEL) == 0 && this.fluid instanceof CanisterFluid canisterFluid) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
            return new ItemStack(canisterFluid.getCanister());
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public Optional<SoundEvent> getCanisterPickupSound(BlockState state) { //todo
        return Optional.empty();
    }
}
