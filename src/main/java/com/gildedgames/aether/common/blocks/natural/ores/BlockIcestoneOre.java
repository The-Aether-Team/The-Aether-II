package com.gildedgames.aether.common.blocks.natural.ores;

import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;

import java.util.Random;

public class BlockIcestoneOre extends BlockAetherOre
{
	public BlockIcestoneOre()
	{
		super(Material.rock);

		this.setHardness(3f);

		this.setStepSound(Block.soundTypeStone);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return ItemsAether.icestone;
	}

	@Override
	protected int getUnmodifiedExpDrop(Random rand)
	{
		return MathHelper.getRandomIntegerInRange(rand, 0, 1);
	}
}
