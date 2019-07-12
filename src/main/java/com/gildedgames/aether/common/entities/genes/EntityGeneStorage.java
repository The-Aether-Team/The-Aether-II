package com.gildedgames.aether.common.entities.genes;

import com.gildedgames.aether.api.entity.genes.IGeneStorage;
import net.minecraft.nbt.CompoundNBT;

public class EntityGeneStorage implements IGeneStorage
{

	private final EntityGeneticAnimal entity;

	public EntityGeneStorage(EntityGeneticAnimal entity)
	{
		this.entity = entity;
	}

	@Override
	public int getSeed()
	{
		return this.entity.getSeed();
	}

	@Override
	public void setSeed(int seed)
	{
		this.entity.setSeed(seed);
	}

	@Override
	public int getFatherSeed()
	{
		return this.entity.getSeed();
	}

	@Override
	public void setFatherSeed(int seed)
	{
		this.entity.setFatherSeed(seed);
	}

	@Override
	public int getMotherSeed()
	{
		return this.entity.getMotherSeed();
	}

	@Override
	public void setMotherSeed(int seed)
	{
		this.entity.setMotherSeed(seed);
	}

	@Override
	public void setShouldRetransform(boolean flag)
	{
		this.entity.setShouldRetransform(flag);
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
