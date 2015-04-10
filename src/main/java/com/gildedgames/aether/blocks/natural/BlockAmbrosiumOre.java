package com.gildedgames.aether.blocks.natural;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.AetherCreativeTabs;

public class BlockAmbrosiumOre extends Block
{

	public BlockAmbrosiumOre()
	{
		super(Material.rock);

		this.setLightLevel(1.0F);
		this.setHardness(3.0F);
		this.setStepSound(soundTypeStone);
		this.setHarvestLevel("pickaxe", 0);

		this.setCreativeTab(Aether.getCreativeTabs().tabBlocks);

	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Aether.getItems().ambrosium_shard;
	}

}
