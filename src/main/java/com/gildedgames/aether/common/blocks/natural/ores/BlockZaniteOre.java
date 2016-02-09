package com.gildedgames.aether.common.blocks.natural.ores;

import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;

import java.util.Random;

public class BlockZaniteOre extends BlockAetherOre
{

	public BlockZaniteOre()
	{
		super(Material.rock);

		this.setHardness(3.0f);
		this.setResistance(5.0f);
		this.setHarvestLevel("pickaxe", 1);

		this.setStepSound(soundTypeStone);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return ItemsAether.zanite_gemstone;
	}

	@Override
	protected int getUnmodifiedExpDrop(Random rand)
	{
		return MathHelper.getRandomIntegerInRange(rand, 2, 4);
	}
}
