package com.gildedgames.aether.common.structure;

import com.gildedgames.aether.api.structure.IBakedStructure;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;

public class BakedStructure implements IBakedStructure
{
	private static final int LATEST_VERSION = 1;

	private String name = "missingno";

	private StructurePalette palette = new StructurePalette();

	private StructureBlockData blocks = new StructureBlockData(0, 0, 0);

	private BlockPos size = new BlockPos(0, 0, 0);

	public BakedStructure(String name, BlockPos size, StructurePalette palette, StructureBlockData blocks)
	{
		this.name = name;
		this.size = size;
		this.palette = palette;
		this.blocks = blocks;
	}

	public BakedStructure(NBTTagCompound tag)
	{
		this.read(tag);
	}

	@Override
	public IBlockState getBlock(BlockPos pos)
	{
		return this.palette.get(this.blocks.getBlock(pos));
	}

	@Override
	public BlockPos getDimensions()
	{
		return this.size;
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		this.name = tag.getString("Name");
		this.size = NBTUtil.getPosFromTag(tag.getCompoundTag("Size"));

		this.blocks = new StructureBlockData(this.size.getX(), this.size.getY(), this.size.getZ());
		this.blocks.load(tag.getTag("BlockData"));

		this.palette = new StructurePalette();
		this.palette.load(tag.getTag("Palette"));
	}

	@Override
	public void write(NBTTagCompound tag)
	{
		tag.setString("Name", this.name);
		tag.setInteger("FormatVersion", LATEST_VERSION);

		tag.setTag("Size", NBTUtil.createPosTag(this.size));

		tag.setTag("BlockData", this.blocks.save());
		tag.setTag("Palette", this.palette.save());

	}
}
