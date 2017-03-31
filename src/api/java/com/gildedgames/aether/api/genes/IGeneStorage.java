package com.gildedgames.aether.api.genes;

import com.gildedgames.aether.api.util.NBT;

public interface IGeneStorage extends NBT
{

	int getSeed();

	int getFatherSeed();

	int getMotherSeed();

	void setSeed(int seed);

	void setFatherSeed(int seed);

	void setMotherSeed(int seed);

	void setShouldRetransform(boolean flag);

}
