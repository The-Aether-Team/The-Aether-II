package com.gildedgames.aether.common.blocks.containers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAetherCraftingTable extends Block
{
	public BlockAetherCraftingTable()
	{
		super(Material.WOOD);

		this.setHardness(2.5f);

		this.setSoundType(SoundType.WOOD);
	}

	@Override
	public boolean onBlockActivated(
			final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side,
			final float hitX, final float hitY, final float hitZ)
	{
		if (!world.isRemote)
		{
			player.displayGui(new BlockWorkbench.InterfaceCraftingTable(world, pos));
			player.addStat(StatList.CRAFTING_TABLE_INTERACTION);
		}

		return true;
	}
}
