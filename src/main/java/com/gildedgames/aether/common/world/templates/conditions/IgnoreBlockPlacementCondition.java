package com.gildedgames.aether.common.world.templates.conditions;

import com.gildedgames.aether.api.world.templates.PlacementConditionTemplate;
import com.gildedgames.orbis.lib.processing.IBlockAccess;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.template.Template;

import java.util.List;

public class IgnoreBlockPlacementCondition implements PlacementConditionTemplate
{

	private final BlockState state;

	public IgnoreBlockPlacementCondition(final BlockState state)
	{
		this.state = state;
	}

	@Override
	public boolean canPlace(final Template template, final IBlockAccess world, final BlockPos placedAt, final Template.BlockInfo block)
	{
		if (block.pos.getY() == placedAt.getY() && block.blockState.getBlock() != Blocks.AIR
				&& block.blockState.getBlock() != Blocks.STRUCTURE_VOID)
		{
			final BlockPos down = block.pos.down();

			if (!world.canAccess(down))
			{
				return false;
			}

			final BlockState state = world.getBlockState(down);

			return this.state != state;
		}

		return true;
	}

	@Override
	public boolean canPlaceCheckAll(final Template template, final IBlockAccess world, final BlockPos placedAt, final List<Template.BlockInfo> blocks)
	{
		return true;
	}

}
