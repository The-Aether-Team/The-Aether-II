package com.gildedgames.orbis.common.data;

import com.gildedgames.aether.api.util.NBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.Random;

public class DataCondition implements NBT
{

	private float placementChance = 1.0F;

	public DataCondition()
	{
		super();
	}

	public boolean isMet(final Random random, final World world)
	{
		return random.nextFloat() <= this.placementChance;
	}

	public boolean isMet(final float randomValue, final float chanceSum, final Random random, final World world)
	{
		return randomValue <= this.placementChance + chanceSum;
	}

	public float getPlacementChance()
	{
		return this.placementChance;
	}

	public void setPlacementChance(final float placementChance)
	{
		this.placementChance = Math.min(1.0F, placementChance);
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		tag.setFloat("placementChance", this.placementChance);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		this.placementChance = tag.getFloat("placementChance");
	}

}
