package com.gildedgames.aether.common.blocks.natural.ores;

import com.gildedgames.aether.api.registrar.ItemsAether;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class BlockZaniteOre extends BlockAetherOre
{

	public BlockZaniteOre(Block.Properties properties)
	{
		super(properties);
	}

	@Override
	public Item getItemDropped(BlockState state, Random rand, int fortune)
	{
		return ItemsAether.zanite_gemstone;
	}

	@Override
	protected int getUnmodifiedExpDrop(Random rand)
	{
		return MathHelper.getInt(rand, 2, 4);
	}
}
