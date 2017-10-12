package com.gildedgames.aether.api.chunk;

import com.gildedgames.aether.api.util.NBT;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;

/**
 * A mutable data structure that can be added to a chunk.
 */
public interface IChunkAttachment
{

	World getWorld();

	int getChunkX();

	int getChunkZ();

	/**
	 * Called when the chunk this attachment is added to is loaded. This is not guaranteed
	 * to be called, and may not when a newly generated chunk is loaded with the attachment. You
	 * should perform all initialization of your data structures in {@link IChunkAttachment#init(ChunkEvent.Load)}.
	 *
	 * @param event The relevant {@link ChunkDataEvent} containing the chunk's data
	 */
	void load(ChunkDataEvent.Load event);

	/**
	 * Called when the chunk this attachment is added to is loaded. This will be called when
	 * a chunk is saving it's data to disk.
	 *
	 * @param event The relevant {@link ChunkDataEvent} containing the data to be written to disk
	 */
	void save(ChunkDataEvent.Save event);

	/**
	 * Called when this attachment is initialized (not when attached) to a chunk. You should allocate
	 * your data structures here. The reason initialization is not done at attachment time is to preserve
	 * memory in the case of empty chunks.
	 *
	 * @param event The relevant {@link ChunkDataEvent} containing details about the chunk loaded.
	 */
	void init(ChunkEvent.Load event);

	/**
	 * Called when the chunk belonging to this attachment is unloaded from disk.
	 *
	 * @param event The relevant {@link ChunkDataEvent} containing details about the chunk unloaded.
	 */
	void destroy(ChunkEvent.Unload event);

	/**
	 * Returns the attached capability implementing {@param capability} for the chunk at the specified
	 * chunk coordinates.
	 *
	 * @param pos The chunk's coordinates
	 * @param capability The capability to retrieve
	 * @return The capability if it's attached to this chunk, null otherwise.
	 */
	<T extends NBT> T getAttachment(ChunkPos pos, Capability<T> capability);
}
