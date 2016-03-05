package com.gildedgames.aether.common.world.chunk;

import com.gildedgames.util.modules.chunk.api.IChunkHookPool;
import com.gildedgames.util.modules.chunk.api.hook.IChunkHook;
import com.gildedgames.util.modules.chunk.api.hook.IChunkHookFactory;
import com.gildedgames.util.modules.chunk.impl.pools.ChunkHookPool;
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
