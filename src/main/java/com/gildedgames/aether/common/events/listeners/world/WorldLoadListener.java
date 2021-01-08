package com.gildedgames.aether.common.events.listeners.world;

import com.gildedgames.aether.common.blocks.IBlockRadiation;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

@Mod.EventBusSubscriber
public class WorldLoadListener
{
    @SubscribeEvent
    public static void onWorldLoad(final ChunkEvent.Load event)
    {
        int j = event.getChunk().x * 16;
        int k = event.getChunk().z * 16;

        for (ExtendedBlockStorage extendedblockstorage : event.getChunk().getBlockStorageArray())
        {
            if (extendedblockstorage != Chunk.NULL_BLOCK_STORAGE)
            {
                int updateLCG = (new Random()).nextInt();
                updateLCG = updateLCG * 3 + 1013904223;
                int j1 = updateLCG >> 2;
                int k1 = j1 & 15;
                int l1 = j1 >> 8 & 15;
                int i2 = j1 >> 16 & 15;
                IBlockState iblockstate = extendedblockstorage.get(k1, i2, l1);
                Block block = iblockstate.getBlock();

                if (block instanceof IBlockRadiation)
                {
                    event.getWorld().scheduleUpdate(new BlockPos(k1 + j, i2 + extendedblockstorage.getYLocation(), l1 + k), block, 1);
                }
            }
        }
    }
}
