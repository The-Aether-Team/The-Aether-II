package com.gildedgames.aether.common.entities.genes;

import com.gildedgames.aether.api.entity.genes.IGeneStorage;
import net.minecraft.nbt.CompoundNBT;

public class SimpleGeneStorage implements IGeneStorage
{

	private int seed, fatherSeed, motherSeed;

	public SimpleGeneStorage()
	{

	}

	@Override
	public int getSeed()
	{
		return this.seed;
	}

	@Override
	public void setSeed(int seed)
	{
		this.seed = seed;
	}

	@Override
	public int getFatherSeed()
	{
		return this.fatherSeed;
	}

	@Override
	public void setFatherSeed(int seed)
	{
		this.fatherSeed = seed;
	}

	@Override
	public int getMotherSeed()
	{
		return this.motherSeed;
	}

	@Override
	public void setMotherSeed(int seed)
	{
		this.motherSeed = seed;
	}

	@Override
	public void setShouldRetransform(boolean flag)
	{

	}

	@Override
	public void write(CompoundNBT tag)
	{
		tag.putInt("seed", this.getSeed());
		tag.putInt("fatherSeed", this.getFatherSeed());
		tag.putInt("motherSeed", this.getMotherSeed());
	}

	@Override
	public void read(CompoundNBT tag)
	{
		this.setSeed(tag.getInt("seed"));
		this.setFatherSeed(tag.getInt("fatherSeed"));
		this.setMotherSeed(tag.getInt("motherSeed"));
	}
}
