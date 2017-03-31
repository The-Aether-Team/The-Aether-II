package com.gildedgames.aether.common.entities.util;

import com.gildedgames.aether.api.genes.IGeneStorage;
import net.minecraft.nbt.NBTTagCompound;

public class EntityGeneStorage implements IGeneStorage
{

	private EntityGeneticAnimal entity;

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
	public int getFatherSeed()
	{
		return this.entity.getSeed();
	}

	@Override
	public int getMotherSeed()
	{
		return this.entity.getMotherSeed();
	}

	@Override
	public void setSeed(int seed)
	{
		this.entity.setSeed(seed);
	}

	@Override
	public void setFatherSeed(int seed)
	{
		this.entity.setFatherSeed(seed);
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
	public void write(NBTTagCompound tag)
	{
		tag.setInteger("seed", this.getSeed());
		tag.setInteger("fatherSeed", this.getFatherSeed());
		tag.setInteger("motherSeed", this.getMotherSeed());
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		this.setSeed(tag.getInteger("seed"));
		this.setFatherSeed(tag.getInteger("fatherSeed"));
		this.setMotherSeed(tag.getInteger("motherSeed"));
	}

}
