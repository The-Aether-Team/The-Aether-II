package com.gildedgames.aether.common.world.templates.conditions;

import com.gildedgames.aether.api.world.generation.IBlockAccessExtended;
import com.gildedgames.aether.api.world.generation.PlacementConditionTemplate;
import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.template.Template;

import java.util.List;

public class InsideGroundPlacementCondition implements PlacementConditionTemplate
{

	@Override
	public boolean canPlace(final Template template, final IBlockAccessExtended world, final BlockPos placedAt, final Template.BlockInfo block)
	{
		if (block.pos.getY() == placedAt.getY() + 1 && block.blockState.getBlock() != Blocks.AIR
				&& block.blockState.getBlock() != Blocks.STRUCTURE_VOID)
		{
			final BlockPos down = block.pos.down();

			if (!world.canAccess(down))
			{
				return false;
			}

			final IBlockState state = world.getBlockState(down);

			if (state.getBlock() != BlocksAether.aether_grass)
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean canPlaceCheckAll(final Template template, final IBlockAccessExtended world, final BlockPos placedAt, final List<Template.BlockInfo> blocks)
	{
		return true;
	}

}
