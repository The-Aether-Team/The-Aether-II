package com.gildedgames.aether.common.blocks.containers;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.AetherGuiHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stats.StatList;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAetherCraftingTable extends CraftingTableBlock
{
	public BlockAetherCraftingTable(Block.Properties properties)
	{
		super(properties);
	}

	@Override
	public boolean onBlockActivated(
			final World worldIn, final BlockPos pos, final BlockState state, final PlayerEntity playerIn, final Hand hand, final Direction facing,
			final float hitX, final float hitY, final float hitZ)
	{
		if (worldIn.isRemote)
		{
			return true;
		}
		else
		{
			playerIn.openGui(AetherCore.INSTANCE, AetherGuiHandler.CUSTOM_WORKBENCH_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
			playerIn.addStat(StatList.CRAFTING_TABLE_INTERACTION);
			return true;
		}
	}
}
