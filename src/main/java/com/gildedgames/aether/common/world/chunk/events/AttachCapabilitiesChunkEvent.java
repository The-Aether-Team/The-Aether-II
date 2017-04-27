package com.gildedgames.aether.common.world.chunk.events;

import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class AttachCapabilitiesChunkEvent extends AttachCapabilitiesEvent<Chunk>
{
	private final Chunk chunk;

	public AttachCapabilitiesChunkEvent(Chunk obj)
	{
		super(Chunk.class, obj);

		this.chunk = obj;
	}

	public Chunk getChunk()
	{
		return this.chunk;
	}
}
