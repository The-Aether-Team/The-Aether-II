package com.gildedgames.aether.api.lighting;

public interface IChunkLightingData
{
	short[] getNeighborLightChecks();

	void setNeighborLightChecks(short[] data);

	boolean isLightInitialized();

	void setLightInitialized(boolean val);

	void setSkylightUpdatedPublic();
}
