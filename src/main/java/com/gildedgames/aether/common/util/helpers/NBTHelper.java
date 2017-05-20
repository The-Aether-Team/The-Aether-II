package com.gildedgames.aether.common.util.helpers;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class NBTHelper
{

	public static NBTTagCompound readNBTFromFile(String fileName)
	{
		return readNBTFromFile(new File(AetherCore.getWorldDirectory(), fileName));
	}

	public static NBTTagCompound readNBTFromFile(File file)
	{
		try
		{
			if (!file.exists())
			{
				return null;
			}
			FileInputStream inputStream = new FileInputStream(file);
			return CompressedStreamTools.readCompressed(inputStream);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static void writeNBTToFile(NBTTagCompound tag, String fileName)
	{
		writeNBTToFile(tag, new File(AetherCore.getWorldDirectory(), fileName));
	}

	public static void writeNBTToFile(NBTTagCompound tag, File file)
	{
		file.mkdirs();
		File tmpFile = new File(file.getParentFile(), file.getName() + ".tmp");
		try
		{
			CompressedStreamTools.writeCompressed(tag, new FileOutputStream(tmpFile));
			if (file.exists())
			{
				file.delete();
			}
			tmpFile.renameTo(file);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static BlockPos readBlockPos(NBTTagCompound tag)
	{
		if (tag == null || (tag.hasKey("_null") && tag.getBoolean("_null")))
		{
			return null;
		}

		return new BlockPos(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z"));
	}

	public static NBTBase writeBlockPos(BlockPos pos)
	{
		NBTTagCompound tag = new NBTTagCompound();

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
}
