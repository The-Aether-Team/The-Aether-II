package com.gildedgames.aether.common.blocks.util;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockCustomBookshelf extends Block
{
	public BlockCustomBookshelf(Material material, SoundType soundType)
	{
		super(material);

		this.setSoundType(soundType);
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 3;
	}

	@Override
	public float getEnchantPowerBonus(World world, BlockPos pos)
	{
		return 1;
	}

	@Override
	@Nullable
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Items.BOOK;
	}
}
