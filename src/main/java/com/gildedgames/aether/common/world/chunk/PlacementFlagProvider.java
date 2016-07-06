package com.gildedgames.aether.common.world.chunk;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.util.modules.chunk.impl.hooks.BlockBitFlagChunkHook;
import com.gildedgames.util.modules.chunk.api.hook.IChunkHookProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.ChunkPos;

public class PlacementFlagProvider implements IChunkHookProvider<BlockBitFlagChunkHook>
{
	@Override
	public BlockBitFlagChunkHook createHook(ChunkPos pos)
	{
		return new BlockBitFlagChunkHook();
	}

	@Override
	public ResourceLocation getID()
	{
		return AetherCore.getResource("placement_flags");
	}
}
