package com.aetherteam.aetherii.event.hooks;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.AetherGrassBlock;
import com.aetherteam.aetherii.block.natural.Snowable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class BlockInteractionHooks {
    public static boolean snowlog(Player player, Level level, BlockPos pos, ItemStack itemStack, InteractionHand hand) {
        BlockState stateInLevel = level.getBlockState(pos);
        if (itemStack.getItem() instanceof BlockItem blockItem) {
            boolean success = false;
            Block blockInHand = blockItem.getBlock();
            if (stateInLevel.is(AetherIIBlocks.ARCTIC_SNOW) && blockInHand instanceof Snowable snowable) {
                level.setBlock(pos, snowable.setSnowy(blockInHand.defaultBlockState()), 1 | 2);
                success = true;
            } else if (blockInHand == AetherIIBlocks.ARCTIC_SNOW.get() && AetherGrassBlock.plantNotSnowed(stateInLevel) && stateInLevel instanceof Snowable snowable) {
                level.setBlock(pos, snowable.setSnowy(stateInLevel), 1 | 2);
                success = true;
            }
            if (success) {
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
                player.swing(hand);
                return true;
            }
        }
        return false;
    }

    @Nullable
    public static void finishSleep(LevelAccessor level, long newTime) {
        if (level instanceof ServerLevel serverLevel) {
            if (serverLevel.dimension().location().getNamespace().equals(AetherII.MODID)) {
                serverLevel.getServer().overworld().setWeatherParameters(0, 0, false, false);
                serverLevel.getServer().overworld().setDayTime(newTime);
            }
        }
    }
}
