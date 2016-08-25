package com.gildedgames.aether.api.genes;

public interface IGenePool<I, O>
{

	IGeneStorage<I, O> getStorage();

	void transformFromParents(int seed, int fatherSeed, int motherSeed);

	void transformFromSeed(int seed);

}
