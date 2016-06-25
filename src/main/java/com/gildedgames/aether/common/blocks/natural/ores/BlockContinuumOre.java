package com.gildedgames.aether.common.blocks.natural.ores;

import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class BlockContinuumOre extends BlockAetherOre
{

	public BlockContinuumOre()
	{
		super(Material.ROCK);

		this.setHardness(3.0f);
		this.setResistance(5.0f);
		this.setHarvestLevel("pickaxe", 3);

		this.setSoundType(SoundType.STONE);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return ItemsAether.continuum_orb;
	}

	@Override
	protected int getUnmodifiedExpDrop(Random rand)
	{
		return MathHelper.getRandomIntegerInRange(rand, 7, 10);
	}
}
