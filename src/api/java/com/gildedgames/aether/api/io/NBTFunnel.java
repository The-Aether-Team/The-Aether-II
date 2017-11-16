package com.gildedgames.aether.api.io;

import com.gildedgames.aether.api.util.IClassSerializer;
import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.aether.api.util.NBTHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDateTime;
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

	public LocalDateTime getDate(final String key)
	{
		final NBTTagCompound tag = this.tag.getCompoundTag(key);

		if (tag.getBoolean("_null") || !tag.hasKey("_null"))
		{
			return null;
		}

		return LocalDateTime.parse(tag.getString("date"));
	}

	public void setDate(final String key, final LocalDateTime date)
	{
		final NBTTagCompound tag = new NBTTagCompound();

		if (date == null)
		{
			tag.setBoolean("_null", true);

			return;
		}

		tag.setBoolean("_null", false);
		tag.setString("date", date.toString());

		this.tag.setTag(key, tag);
	}

	public <T extends NBT> T get(final String key)
	{
		return NBTHelper.read(this.serializer, this.tag.getCompoundTag(key));
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

	public void setIntToStringMap(final String key, final Map<Integer, String> nbtMap)
	{
		final NBTTagList writtenKeys = new NBTTagList();
		final NBTTagList writtenObjects = new NBTTagList();

		for (final Map.Entry<Integer, String> entrySet : nbtMap.entrySet())
		{
			final int intKey = entrySet.getKey();
			final String string = entrySet.getValue();

			writtenKeys.appendTag(new NBTTagInt(intKey));
			writtenObjects.appendTag(new NBTTagString(string));
		}

		this.tag.setTag(key + "_keys", writtenKeys);
		this.tag.setTag(key + "_obj", writtenObjects);
	}

	public Map<Integer, String> getIntToStringMap(final String key)
	{
		final Map<Integer, String> readObjects = Maps.newHashMap();

		final NBTTagList keys = this.tag.getTagList(key + "_keys", 3);
		final NBTTagList objects = this.tag.getTagList(key + "_obj", 8);

		for (int i = 0; i < keys.tagCount(); i++)
		{
			final int intKey = keys.getIntAt(i);
			final String data = objects.getStringTagAt(i);

			readObjects.put(intKey, data);
		}

		return readObjects;
	}

	private <T extends NBT> Map<String, T> getStringMapInner(final World world, final String key)
	{
		final Map<String, T> readObjects = Maps.newHashMap();

		final NBTTagList keys = this.tag.getTagList(key + "_keys", 8);
		final NBTTagList objects = this.tag.getTagList(key + "_obj", 10);

		for (int i = 0; i < keys.tagCount(); i++)
		{
			final String stringKey = keys.getStringTagAt(i);
			final NBTTagCompound data = objects.getCompoundTagAt(i);

			readObjects.put(stringKey, world == null ? NBTHelper.read(this.serializer, data) : NBTHelper.read(world, this.serializer, data));
		}

		return readObjects;
	}

	public <T extends NBT> Map<String, T> getStringMap(final String key)
	{
		return this.getStringMapInner(null, key);
	}

	public <T extends NBT> Map<String, T> getStringMap(final World world, final String key)
	{
		return this.getStringMapInner(world, key);
	}

	public void setMap(final String key, final Map<? extends NBT, ? extends NBT> nbtMap)
	{
		final NBTTagList writtenKeys = new NBTTagList();
		final NBTTagList writtenObjects = new NBTTagList();

		for (final Map.Entry<? extends NBT, ? extends NBT> entrySet : nbtMap.entrySet())
		{
			final NBT keyNBT = entrySet.getKey();
			final NBT valueNBT = entrySet.getValue();

			writtenKeys.appendTag(NBTHelper.write(this.serializer, keyNBT));
			writtenObjects.appendTag(NBTHelper.write(this.serializer, valueNBT));
		}

		this.tag.setTag(key + "_keys", writtenKeys);
		this.tag.setTag(key + "_obj", writtenObjects);
	}

	private <K extends NBT, T extends NBT> Map<K, T> getMapInner(final World world, final String key)
	{
		final Map<K, T> readObjects = Maps.newHashMap();

		final NBTTagList keys = this.tag.getTagList(key + "_keys", 10);
		final NBTTagList objects = this.tag.getTagList(key + "_obj", 10);

		for (int i = 0; i < keys.tagCount(); i++)
		{
			final NBTTagCompound keyData = objects.getCompoundTagAt(i);
			final NBTTagCompound valueData = objects.getCompoundTagAt(i);

			readObjects.put(world == null ? NBTHelper.read(this.serializer, keyData) : NBTHelper.read(world, this.serializer, keyData),
					world == null ? NBTHelper.read(this.serializer, valueData) : NBTHelper.read(world, this.serializer, valueData));
		}

		return readObjects;
	}

	public <K extends NBT, T extends NBT> Map<K, T> getMap(final String key)
	{
		return this.getMapInner(null, key);
	}

	public <K extends NBT, T extends NBT> Map<K, T> getMap(final World world, final String key)
	{
		return this.getMapInner(world, key);
	}

	public void setStringMap(final String key, final Map<String, ? extends NBT> nbtMap)
	{
		final NBTTagList writtenKeys = new NBTTagList();
		final NBTTagList writtenObjects = new NBTTagList();

		for (final Map.Entry<String, ? extends NBT> entrySet : nbtMap.entrySet())
		{
			final String stringKey = entrySet.getKey();
			final NBT nbt = entrySet.getValue();

			writtenKeys.appendTag(new NBTTagString(stringKey));
			writtenObjects.appendTag(NBTHelper.write(this.serializer, nbt));
		}

		this.tag.setTag(key + "_keys", writtenKeys);
		this.tag.setTag(key + "_obj", writtenObjects);
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

	private <T extends NBT> Map<Integer, T> getIntMapInner(final World world, final String key)
	{
		final Map<Integer, T> readObjects = Maps.newHashMap();

		final NBTTagList keys = this.tag.getTagList(key + "_keys", 3);
		final NBTTagList objects = this.tag.getTagList(key + "_obj", 10);

		for (int i = 0; i < keys.tagCount(); i++)
		{
			final int intKey = keys.getIntAt(i);
			final NBTTagCompound data = objects.getCompoundTagAt(i);

			readObjects.put(intKey, world == null ? NBTHelper.read(this.serializer, data) : NBTHelper.read(world, this.serializer, data));
		}

		return readObjects;
	}

	public <T extends NBT> Map<Integer, T> getIntMap(final String key)
	{
		return this.getIntMapInner(null, key);
	}

	public <T extends NBT> Map<Integer, T> getIntMap(final World world, final String key)
	{
		return this.getIntMapInner(world, key);
	}

	public void setPairList(final String key, final List<? extends Pair<? extends NBT, ? extends NBT>> nbtList)
	{
		final NBTTagList writtenObjects = new NBTTagList();

		for (final Pair<? extends NBT, ? extends NBT> pair : nbtList)
		{
			writtenObjects.appendTag(NBTHelper.write(this.serializer, pair.getLeft()));
			writtenObjects.appendTag(NBTHelper.write(this.serializer, pair.getRight()));
		}

		this.tag.setTag(key, writtenObjects);
	}

	public <T extends Pair, L extends NBT, R extends NBT> List<T> getPairList(final World world, final String key)
	{
		final List<Pair> readObjects = Lists.newArrayList();
		final NBTTagList nbtList = this.tag.getTagList(key, 10);

		for (int i = 0; i < nbtList.tagCount(); i++)
		{
			final NBTTagCompound leftData = nbtList.getCompoundTagAt(i);
			final NBTTagCompound rightData = nbtList.getCompoundTagAt(i++);

			final L left = NBTHelper.read(world, this.serializer, leftData);
			final R right = NBTHelper.read(world, this.serializer, rightData);

			readObjects.add(Pair.of(left, right));
		}

		return (List<T>) readObjects;
	}

	public <T extends Pair, L extends NBT, R extends NBT> List<T> getPairList(final String key)
	{
		final List<Pair> readObjects = Lists.newArrayList();
		final NBTTagList nbtList = this.tag.getTagList(key, 10);

		for (int i = 0; i < nbtList.tagCount(); i++)
		{
			final NBTTagCompound leftData = nbtList.getCompoundTagAt(i);
			final NBTTagCompound rightData = nbtList.getCompoundTagAt(++i);

			final L left = NBTHelper.read(this.serializer, leftData);
			final R right = NBTHelper.read(this.serializer, rightData);

			readObjects.add(Pair.of(left, right));
		}

		return (List<T>) readObjects;
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

	public <T extends NBT> List<T> getList(final String key)
	{
		final List<T> readObjects = Lists.newArrayList();
		final NBTTagList nbtList = this.tag.getTagList(key, 10);

		for (int i = 0; i < nbtList.tagCount(); i++)
		{
			final NBTTagCompound data = nbtList.getCompoundTagAt(i);

			readObjects.add(NBTHelper.read(this.serializer, data));
		}

		return readObjects;
	}

	public List<String> getStringList(final String key)
	{
		final List<String> readObjects = Lists.newArrayList();
		final NBTTagList nbtList = this.tag.getTagList(key, 8);

		for (int i = 0; i < nbtList.tagCount(); i++)
		{
			readObjects.add(nbtList.getStringTagAt(i));
		}

		return readObjects;
	}

	public void setStringList(final String key, final List<String> stringList)
	{
		final NBTTagList writtenObjects = new NBTTagList();

		for (final String string : stringList)
		{
			writtenObjects.appendTag(new NBTTagString(string));
		}

		this.tag.setTag(key, writtenObjects);
	}

}
