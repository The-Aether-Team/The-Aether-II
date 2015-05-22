package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.common.AetherCreativeTabs;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockAmbrosiumOre extends Block
{

	public BlockAmbrosiumOre()
	{
		super(Material.rock);

		this.setLightLevel(1.0F);
		this.setHardness(3.0F);
		this.setStepSound(soundTypeStone);
		this.setHarvestLevel("pickaxe", 0);

		this.setCreativeTab(AetherCreativeTabs.tabBlocks);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return ItemsAether.ambrosium_shard;
	}

}
