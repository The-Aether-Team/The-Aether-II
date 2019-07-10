package com.gildedgames.aether.api.world.spawn;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.common.util.INBTSerializable;

public interface ISpawnArea extends INBTSerializable<NBTTagCompound>
{
	ChunkPos getMinChunkPos();

	ChunkPos getMaxChunkPos();

	int getAreaX();

	int getAreaZ();

	void addToEntityCount(int count);

	int getEntityCount();

	void setEntityCount(int entityCount);

	boolean hasPlayerInside();

	void setInPlayersRenderDistance(boolean flag);

	boolean isDirty();

	void markDirty();

	void markClean();
}
