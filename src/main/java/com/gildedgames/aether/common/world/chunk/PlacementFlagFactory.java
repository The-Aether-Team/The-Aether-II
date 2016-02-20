package com.gildedgames.aether.common.world.chunk;

import com.gildedgames.util.modules.chunk.common.hook.IChunkHook;
import com.gildedgames.util.modules.chunk.common.hook.IChunkHookFactory;
import com.gildedgames.util.modules.chunk.common.pools.ChunkHookPool;
import com.gildedgames.util.modules.chunk.common.pools.IChunkHookPool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class PlacementFlagFactory implements IChunkHookFactory<IChunkHook>
{
	@Override
	public PlacementFlagChunkData createHook(World world, NBTTagCompound tag)
	{
		PlacementFlagChunkData data = new PlacementFlagChunkData();
		data.read(tag);

		return data;
	}

	@Override
	public IChunkHookPool createPool()
	{
		return new ChunkHookPool();
	}
}
