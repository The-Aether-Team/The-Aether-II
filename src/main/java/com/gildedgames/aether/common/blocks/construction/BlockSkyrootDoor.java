package com.gildedgames.aether.common.blocks.construction;

import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockSkyrootDoor extends BlockDoor
{
	public BlockSkyrootDoor()
	{
		super(Material.wood);

		this.setStepSound(Block.soundTypeWood);

		this.setHardness(3.0f);

		this.disableStats();
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? null : ItemsAether.skyroot_door;
	}
}
