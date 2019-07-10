package com.gildedgames.aether.api.world.preparation;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Implementations will handle the creation of IPrepSectors.
 * Example is a registration which finds locations to place Blueprints, then
 * schedules them into the sector.
 */
public interface IPrepRegistryEntry<T>
{
	ResourceLocation getUniqueId();

	/**
	 * @return The area size of each IPrepSectorData created by this registration.
	 */
	int getSectorChunkArea();

	boolean shouldAttachTo(World world);

	void postSectorDataCreate(World world, IPrepSectorData data);

	IPrepSectorData createData(World world, long seed, int sectorX, int sectorY);

	IPrepSectorData createDataAndRead(World world, NBTTagCompound tag);

	void threadSafeGenerateMask(T info, World world, IPrepSectorData sectorData, IChunkMask mask, int x, int z);

	IChunkMaskTransformer createMaskTransformer();

	T generateChunkColumnInfo(World world, IPrepSectorData sectorData, int chunkX, int chunkY);
}
