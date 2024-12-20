package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BlueberryBushBlock extends FullAetherBushBlock {
    public BlueberryBushBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
        if (tool.getEnchantmentLevel(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.SILK_TOUCH)) <= 0) {
            level.setBlock(pos, AetherIIBlocks.BLUEBERRY_BUSH_STEM.get().defaultBlockState(), 1 | 2);
        }
    }

    @Override
    public void onBlockExploded(BlockState state, ServerLevel level, BlockPos pos, Explosion explosion) {
        super.onBlockExploded(state, level, pos, explosion);
        level.setBlock(pos, AetherIIBlocks.BLUEBERRY_BUSH_STEM.get().defaultBlockState(), 1 | 2);
    }
}
