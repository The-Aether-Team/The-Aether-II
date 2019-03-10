package com.gildedgames.aether.api.entity.genes;

import com.gildedgames.orbis.lib.util.mc.NBT;

public interface IGeneStorage extends NBT
{

	int getSeed();

	void setSeed(int seed);

	int getFatherSeed();

	void setFatherSeed(int seed);

	int getMotherSeed();

	void setMotherSeed(int seed);

	void setShouldRetransform(boolean flag);

}
