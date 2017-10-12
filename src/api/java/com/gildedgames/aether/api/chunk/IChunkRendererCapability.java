package com.gildedgames.aether.api.chunk;

import com.gildedgames.aether.api.orbis.IWorldRenderer;
import com.gildedgames.aether.api.orbis.region.IRegion;
import com.gildedgames.aether.api.util.NBT;
import net.minecraft.world.World;

import java.util.List;

/**
 * Stores world renderers in a chunk. Used primarily by
 * Orbis to render regions/shapes in the world.
 */
public interface IChunkRendererCapability extends NBT
{

	boolean hasBeenLoaded();

	/**
	 * Loads and sets the "hasBeenLoaded" flag to true
	 */
	void load();

	void render(World world, float partialTicks);

	/**
	 * Adds a world renderer to the chunk
	 */
	void addRenderer(IWorldRenderer renderer);

	/**
	 * Removes a world renderer from the chunk
	 */
	boolean removeRenderer(IWorldRenderer renderer);

	boolean shouldHave(IWorldRenderer renderer);

	/**
	 * A list of world objects stored in this chunk
	 * @return
	 */
	List<IWorldRenderer> getRenderers();

	IRegion getBoundingBox();
}
