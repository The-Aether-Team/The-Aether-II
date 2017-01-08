package com.gildedgames.aether.api.capabilites.chunk;

import com.gildedgames.aether.api.util.NBT;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;

public interface IChunkAttachment
{
	void load(ChunkDataEvent.Load event);

	void save(ChunkDataEvent.Save event);

	void init(ChunkEvent.Load event);

	void destroy(ChunkEvent.Unload event);

	<T extends NBT> T getAttachment(ChunkPos pos, Capability<T> capability);
}
