package com.gildedgames.aether.common.world.aether.biomes.magnetic_hills;

import com.gildedgames.orbis_api.util.io.NBTFunnel;
import com.gildedgames.orbis_api.util.mc.NBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class MagneticHillPillar implements NBT
{
	private float elongationMod;

	private int topHeight;

	private double radius;

	private BlockPos pos;

	private MagneticHillPillar()
	{

	}

	public MagneticHillPillar(final float elongationMod, final int topHeight, final BlockPos pos, final double radius)
	{
		this.elongationMod = elongationMod;
		this.topHeight = topHeight;
		this.pos = pos;
		this.radius = radius;
	}

	public float getElongationMod()
	{
		return this.elongationMod;
	}

	public int getTopHeight()
	{
		return this.topHeight;
	}

	public BlockPos getPos()
	{
		return this.pos;
	}

	public double getRadius()
	{
		return this.radius;
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = new NBTFunnel(tag);

		tag.setFloat("elongationMod", this.elongationMod);
		tag.setInteger("topHeight", this.topHeight);
		funnel.setPos("pos", this.pos);
		tag.setDouble("radius", this.radius);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = new NBTFunnel(tag);

		this.elongationMod = tag.getFloat("elongationMod");
		this.topHeight = tag.getInteger("topHeight");
		this.pos = funnel.getPos("pos");
		this.radius = tag.getDouble("radius");
	}
}
