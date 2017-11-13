package com.gildedgames.aether.api.orbis_core.data;

import com.gildedgames.aether.api.util.NBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.Random;

public class DataCondition implements NBT
{

	private float weight = 1.0F;

	public DataCondition()
	{
		super();
	}

	public boolean isMet(final Random random, final World world)
	{
		return random.nextFloat() <= this.weight;
	}

	public boolean isMet(final float randomValue, final float chanceSum, final Random random, final World world)
	{
		return randomValue <= this.weight + chanceSum;
	}

	public float getWeight()
	{
		return this.weight;
	}

	public void setWeight(final float weight)
	{
		this.weight = weight;
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		tag.setFloat("weight", this.weight);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		this.weight = tag.getFloat("weight");
	}

}
