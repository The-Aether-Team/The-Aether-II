package com.gildedgames.aether.api.biology;

import net.minecraft.entity.Entity;

public interface GenePool
{

	Entity getEntity();

	void onUpdate();

	int getSeed();

	int getFatherSeed();

	int getMotherSeed();

	void transformFromParents(int seed, int fatherSeed, int motherSeed);

	void transformFromSeed(int seed);

}
