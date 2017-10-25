package com.gildedgames.aether.api.util;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class NBTHelper
{

	public static NBTTagCompound readNBTFromFile(final String fileName)
	{
		return readNBTFromFile(new File(DimensionManager.getCurrentSaveRootDirectory(), fileName));
	}

	public static NBTTagCompound readNBTFromFile(final File file)
	{
		try
		{
			if (!file.exists())
			{
				return null;
			}
			final FileInputStream inputStream = new FileInputStream(file);
			return CompressedStreamTools.readCompressed(inputStream);
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static void writeNBTToFile(final NBTTagCompound tag, final String fileName)
	{
		writeNBTToFile(tag, new File(DimensionManager.getCurrentSaveRootDirectory(), fileName));
	}

	public static void writeNBTToFile(final NBTTagCompound tag, final File file)
	{
		file.mkdirs();
		final File tmpFile = new File(file.getParentFile(), file.getName() + ".tmp");
		try
		{
			CompressedStreamTools.writeCompressed(tag, new FileOutputStream(tmpFile));
			if (file.exists())
			{
				file.delete();
			}
			tmpFile.renameTo(file);
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
	}

	public static BlockPos readBlockPos(final NBTTagCompound tag)
	{
		if (tag == null || (tag.hasKey("_null") && tag.getBoolean("_null")))
		{
			return null;
		}

		return new BlockPos(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z"));
	}

	public static NBTBase writeBlockPos(final BlockPos pos)
	{
		final NBTTagCompound tag = new NBTTagCompound();

		if (pos == null)
		{
			tag.setBoolean("_null", true);

			return tag;
		}

		tag.setInteger("x", pos.getX());
		tag.setInteger("y", pos.getY());
		tag.setInteger("z", pos.getZ());

		return tag;
	}

	public static ChunkPos readChunkPos(final NBTTagCompound tag)
	{
		if (tag == null || (tag.hasKey("_null") && tag.getBoolean("_null")))
		{
			return null;
		}

		return new ChunkPos(tag.getInteger("x"), tag.getInteger("z"));
	}

	public static NBTBase writeChunkPos(final ChunkPos pos)
	{
		final NBTTagCompound tag = new NBTTagCompound();

		if (pos == null)
		{
			tag.setBoolean("_null", true);

			return tag;
		}

		tag.setInteger("x", pos.chunkXPos);
		tag.setInteger("z", pos.chunkZPos);

		return tag;
	}

	public static double[] readDoubleArray(final NBTTagCompound tag)
	{
		if (tag == null || (tag.hasKey("_null") && tag.getBoolean("_null")))
		{
			return null;
		}

		final double[] array = new double[tag.getInteger("length")];

		for (int i = 0; i < array.length; i++)
		{
			array[i] = tag.getDouble(String.valueOf(i));
		}

		return array;
	}

	public static NBTBase writeDoubleArray(final double[] array)
	{
		final NBTTagCompound tag = new NBTTagCompound();

		if (array == null)
		{
			tag.setBoolean("_null", true);

			return tag;
		}

		tag.setInteger("length", array.length);

		int i = 0;

		for (final double value : array)
		{
			tag.setDouble(String.valueOf(i), value);

			i++;
		}

		return tag;
	}

	public static NBTTagCompound write(final NBT nbt)
	{
		final NBTTagCompound tag = new NBTTagCompound();

		nbt.write(tag);

		return tag;
	}

	public static NBTTagCompound write(final IClassSerializer serializer, final NBT nbt)
	{
		final NBTTagCompound tag = new NBTTagCompound();

		if (nbt == null)
		{
			tag.setBoolean("_null", true);

			return tag;
		}

		tag.setInteger("id", serializer.serialize(nbt));

		final NBTTagCompound data = new NBTTagCompound();

		nbt.write(data);

		tag.setTag("data", data);

		return tag;
	}

	public static <T extends NBT> T read(final World world, final IClassSerializer serializer, final NBTTagCompound tag)
	{
		if (tag.getBoolean("_null"))
		{
			return null;
		}

		final int id = tag.getInteger("id");

		final T obj = serializer.deserialize(world, id);
		obj.read(tag.getCompoundTag("data"));

		return obj;
	}

	public static <T extends NBT> T read(final IClassSerializer serializer, final NBTTagCompound tag)
	{
		if (tag.getBoolean("_null"))
		{
			return null;
		}

		final int id = tag.getInteger("id");

		final T obj = serializer.deserialize(id);
		obj.read(tag.getCompoundTag("data"));

		return obj;
	}
}
