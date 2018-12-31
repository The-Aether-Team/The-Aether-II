package com.gildedgames.aether.api.world.islands;

public interface IIslandChunkColumnInfo
{
	INoiseProvider getTerrainDepthBuffer();

	INoiseProvider getCloudDepthBuffer();

	int getHeight(int x, int y);

	void setHeight(int x, int y, int height);
}
