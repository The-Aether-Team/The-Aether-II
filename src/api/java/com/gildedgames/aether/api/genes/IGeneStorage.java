package com.gildedgames.aether.api.genes;

import com.gildedgames.aether.api.util.IO;

public interface IGeneStorage<I, O> extends IO<I, O>
{

	int getSeed();

	int getFatherSeed();

	int getMotherSeed();

	void setSeed(int seed);

	void setFatherSeed(int seed);

	void setMotherSeed(int seed);

	void setShouldRetransform(boolean flag);

}
