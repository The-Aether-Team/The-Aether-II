package com.gildedgames.aether.common.world.templates.conditions;

import com.gildedgames.aether.api.util.BlockUtil;
import com.gildedgames.aether.api.world.generation.IBlockAccessExtended;
import com.gildedgames.aether.api.world.generation.PlacementConditionTemplate;
import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.template.Template;

import java.util.List;

public class InsideGroundAtSourcePlacementCondition implements PlacementConditionTemplate
{

	@Override
	public boolean canPlace(final Template template, final IBlockAccessExtended world, final BlockPos placedAt, final Template.BlockInfo block)
	{
		return true;
	}

	@Override
	public boolean canPlaceCheckAll(final Template template, final IBlockAccessExtended world, final BlockPos placedAt, final List<Template.BlockInfo> blocks)
	{
		if (!world.canAccess(placedAt))
		{
			return false;
		}

		IBlockState state = world.getBlockState(placedAt);

		if (state.getBlock() != BlocksAether.aether_grass)
		{
			return false;
		}

		for (final Template.BlockInfo info : blocks)
		{
			if (info.pos.getY() == placedAt.getY() && info.blockState.getBlock() != Blocks.AIR && info.blockState.getBlock() != Blocks.STRUCTURE_VOID)
			{
				final BlockPos down = info.pos.down();

				if (!world.canAccess(down))
				{
					return false;
				}

				state = world.getBlockState(down);

				if (!BlockUtil.isSolid(state))
				{
					return false;
				}
			}
		}

		return true;
	}

}
