package com.gildedgames.aether.api.io;

import com.gildedgames.aether.api.util.IClassSerializer;
import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.aether.api.util.NBTHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

public class NBTFunnel
{

	private final NBTTagCompound tag;

	private final IClassSerializer serializer;

	public NBTFunnel(final NBTTagCompound tag, final IClassSerializer serializer)
	{
		this.tag = tag;
		this.serializer = serializer;
	}

	public void set(final String key, final NBT nbt)
	{
		this.tag.setTag(key, NBTHelper.write(this.serializer, nbt));
	}

	public <T extends NBT> T get(final World world, final String key)
	{
		return NBTHelper.read(world, this.serializer, this.tag.getCompoundTag(key));
	}

	public void setPos(final String key, final BlockPos pos)
	{
		this.tag.setTag(key, NBTHelper.writeBlockPos(pos));
	}

	public BlockPos getPos(final String key)
	{
		return NBTHelper.readBlockPos(this.tag.getCompoundTag(key));
	}

	public void setIntMap(final String key, final Map<Integer, ? extends NBT> nbtMap)
	{
		final NBTTagList writtenKeys = new NBTTagList();
		final NBTTagList writtenObjects = new NBTTagList();

		for (final Map.Entry<Integer, ? extends NBT> entrySet : nbtMap.entrySet())
		{
			final int intKey = entrySet.getKey();
			final NBT nbt = entrySet.getValue();

			writtenKeys.appendTag(new NBTTagInt(intKey));
			writtenObjects.appendTag(NBTHelper.write(this.serializer, nbt));
		}

		this.tag.setTag(key + "_keys", writtenKeys);
		this.tag.setTag(key + "_obj", writtenObjects);
	}

	public <T extends NBT> Map<Integer, T> getIntMap(final World world, final String key)
	{
		final Map<Integer, T> readObjects = Maps.newHashMap();

		final NBTTagList keys = this.tag.getTagList(key + "_keys", 3);
		final NBTTagList objects = this.tag.getTagList(key + "_obj", 10);

		for (int i = 0; i < keys.tagCount(); i++)
		{
			final int intKey = keys.getIntAt(i);
			final NBTTagCompound data = objects.getCompoundTagAt(i);

			readObjects.put(intKey, NBTHelper.read(world, this.serializer, data));
		}

		return readObjects;
	}

	public void setList(final String key, final List<? extends NBT> nbtList)
	{
		final NBTTagList writtenObjects = new NBTTagList();

		for (final NBT nbt : nbtList)
		{
			writtenObjects.appendTag(NBTHelper.write(this.serializer, nbt));
		}

		this.tag.setTag(key, writtenObjects);
	}

	public <T extends NBT> List<T> getList(final World world, final String key)
	{
		final List<T> readObjects = Lists.newArrayList();
		final NBTTagList nbtList = this.tag.getTagList(key, 10);

		for (int i = 0; i < nbtList.tagCount(); i++)
		{
			final NBTTagCompound data = nbtList.getCompoundTagAt(i);

			readObjects.add(NBTHelper.read(world, this.serializer, data));
		}

		return readObjects;
	}

}
