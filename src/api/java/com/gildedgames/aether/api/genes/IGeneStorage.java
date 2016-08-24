package com.gildedgames.aether.api.genes;

import com.gildedgames.util.io_manager.io.NBT;

public interface IGeneStorage extends NBT
{

	int getSeed();

	int getFatherSeed();

	int getMotherSeed();

	void setSeed(int seed);

	void setFatherSeed(int seed);

	void setMotherSeed(int seed);

	boolean shouldRetransform();

	void setShouldRetransform(boolean flag);

}
