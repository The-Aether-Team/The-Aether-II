package com.gildedgames.aether.api.orbis_core.api;

import com.gildedgames.aether.api.util.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class CreationData implements ICreationData
{

	private Random rand;

	private BlockPos pos;

	private World world;

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
	public ICreationData set(final BlockPos pos)
	{
		this.pos = pos;

		return this;
	}

	@Override
	public ICreationData set(final World world)
	{
		this.world = world;

		return this;
	}

	@Override
	public ICreationData set(final Rotation rotation)
	{
		this.rotation = rotation;

		return this;
	}

	@Override
	public ICreationData set(final Random random)
	{
		this.rand = random;

		return this;
	}

	@Override
	public ICreationData set(final EntityPlayer creator)
	{
		this.creator = creator;

		return this;
	}

	@Override
	public BlockPos getPos()
	{
		return this.pos;
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

	@Override
	public ICreationData clone()
	{
		return new CreationData(this.world).set(new BlockPos(this.pos)).set(this.rand).set(this.rotation).set(this.creator);
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		tag.setTag("pos", NBTHelper.writeBlockPos(this.pos));
		tag.setString("rotation", this.rotation.name());
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		this.pos = NBTHelper.readBlockPos(tag.getCompoundTag("pos"));
		this.rotation = Rotation.valueOf(tag.getString("rotation"));
	}
}
