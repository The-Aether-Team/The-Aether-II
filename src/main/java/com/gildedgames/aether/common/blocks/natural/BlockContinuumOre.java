package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.common.AetherCreativeTabs;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockContinuumOre extends Block
{

	public BlockContinuumOre()
	{
		super(Material.rock);

		this.setHardness(3.0f);
		this.setResistance(5.0f);
		this.setHarvestLevel("pickaxe", 3);

		this.setStepSound(Block.soundTypeStone);

		this.setCreativeTab(AetherCreativeTabs.tabBlocks);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return ItemsAether.continuum_orb;
	}

}
