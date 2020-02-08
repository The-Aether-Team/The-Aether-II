package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.common.blocks.IBlockWithItem;
import com.gildedgames.aether.common.items.blocks.ItemBlockCloudwool;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public class BlockCloudwoolBlock extends Block implements IBlockWithItem
{
	public BlockCloudwoolBlock()
	{
		super(Material.CLOTH);

		this.setHardness(0.8f);

		this.setSoundType(SoundType.CLOTH);
	}

	@Override
	public ItemBlock createItemBlock()
	{
		return new ItemBlockCloudwool(this);
	}
}
