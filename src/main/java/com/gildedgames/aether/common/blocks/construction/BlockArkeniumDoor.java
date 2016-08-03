package com.gildedgames.aether.common.blocks.construction;

import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockArkeniumDoor extends BlockDoor
{
	public BlockArkeniumDoor()
	{
		super(Material.IRON);

		this.setSoundType(SoundType.METAL);

		this.setHardness(3.0f);

		this.disableStats();
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? null : ItemsAether.arkenium_door;
	}
}
