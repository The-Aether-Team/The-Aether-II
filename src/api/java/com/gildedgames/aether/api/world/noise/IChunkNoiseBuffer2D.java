package com.gildedgames.aether.api.world.noise;

public interface IChunkNoiseBuffer2D
{
	double get(int x, int z);

	void set(int x, int z, double val);
}
