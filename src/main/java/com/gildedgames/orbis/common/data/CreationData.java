package com.gildedgames.orbis.common.data;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Rotation;
import net.minecraft.world.World;

import java.util.Random;

public class CreationData implements ICreationData
{

	private final World world;

	private final Random rand;

	private EntityPlayer creator;

	private Rotation rotation;

	public CreationData(final World world)
	{
		this.world = world;
		this.rand = world.rand;
	}

	public CreationData(final World world, final long seed)
	{
		this.world = world;
		this.rand = new Random(seed);
	}

	public CreationData(final World world, final EntityPlayer creator)
	{
		this(world);

		this.creator = creator;
	}

	@Override
	public World getWorld()
	{
		return this.world;
	}

	@Override
	public Random getRandom()
	{
		return this.rand;
	}

	@Override
	public Rotation getRotation()
	{
		return this.rotation;
	}

	@Override
	public EntityPlayer getCreator()
	{
		return this.creator;
	}
}
