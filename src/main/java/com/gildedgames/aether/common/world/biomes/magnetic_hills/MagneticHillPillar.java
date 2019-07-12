package com.gildedgames.aether.common.world.biomes.magnetic_hills;

import com.gildedgames.orbis.lib.util.io.NBTFunnel;
import com.gildedgames.orbis.lib.util.mc.NBT;
import net.minecraft.nbt.CompoundNBT;
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
	public void write(final CompoundNBT tag)
	{
		final NBTFunnel funnel = new NBTFunnel(tag);

		tag.putFloat("elongationMod", this.elongationMod);
		tag.putInt("topHeight", this.topHeight);
		funnel.setPos("pos", this.pos);
		tag.putDouble("radius", this.radius);
	}

	@Override
	public void read(final CompoundNBT tag)
	{
		final NBTFunnel funnel = new NBTFunnel(tag);

		this.elongationMod = tag.getFloat("elongationMod");
		this.topHeight = tag.getInt("topHeight");
		this.pos = funnel.getPos("pos");
		this.radius = tag.getDouble("radius");
	}
}
