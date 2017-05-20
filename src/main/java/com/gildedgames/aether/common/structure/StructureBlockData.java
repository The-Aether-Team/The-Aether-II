package com.gildedgames.aether.common.structure;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.Validate;

public class StructureBlockData
{
	private int[] blocks;

	private int width, height, depth;

	public StructureBlockData(int width, int height, int depth)
	{
		this(width, height, depth, new int[width * height * depth]);
	}

	public StructureBlockData(int width, int height, int depth, int[] blocks)
	{
		if (blocks.length != (width * height * depth))
		{
			throw new IllegalArgumentException("Invalid block data array size");
		}

		this.width = width;
		this.height = height;
		this.depth = depth;

		this.blocks = blocks;
	}

	public int getBlock(BlockPos pos)
	{
		return this.blocks[pos.getX() + this.width * (pos.getY() + this.height * pos.getZ())];
	}

	public void setBlock(BlockPos pos, int paletteKey)
	{
		this.blocks[pos.getX() + this.width * (pos.getY() + this.height * pos.getZ())] = paletteKey;
	}

	public void load(NBTBase base)
	{
		Validate.isTrue(base instanceof NBTTagIntArray, "Expected NBTTagIntArray");

		this.blocks = ((NBTTagIntArray) base).getIntArray();
	}

	public NBTBase save()
	{
		return new NBTTagIntArray(this.blocks);
	}
}
