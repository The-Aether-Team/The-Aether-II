package com.gildedgames.aether.api.capabilites.chunk;

import com.gildedgames.util.io_manager.io.NBT;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.world.ChunkDataEvent;

public interface IChunkAttachmentCapability
{
	void load(ChunkDataEvent.Load event);

	void save(ChunkDataEvent.Save event);

	<T extends NBT> T getAttachment(ChunkPos pos, Capability<T> capability);
}
