package com.gildedgames.aether.common.world.templates.conditions;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.world.templates.PlacementConditionTemplate;
import com.gildedgames.orbis.lib.processing.IBlockAccess;
import com.gildedgames.orbis.lib.util.mc.BlockUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.template.Template;

import java.util.List;

public class InsideGroundAtSourcePlacementCondition implements PlacementConditionTemplate
{

	@Override
	public boolean canPlace(final Template template, final IBlockAccess world, final BlockPos placedAt, final Template.BlockInfo block)
	{
		return true;
	}

	@Override
	public boolean canPlaceCheckAll(final Template template, final IBlockAccess world, final BlockPos placedAt, final List<Template.BlockInfo> blocks)
	{
		if (!world.canAccess(placedAt))
		{
			return false;
		}

		BlockState state = world.getBlockState(placedAt);

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
