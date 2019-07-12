package com.gildedgames.aether.common.world.templates.conditions;

import com.gildedgames.aether.api.util.TemplateUtil;
import com.gildedgames.aether.api.world.templates.PlacementConditionTemplate;
import com.gildedgames.orbis.lib.processing.IBlockAccess;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.template.Template;

import java.util.List;

public class UndergroundPlacementCondition implements PlacementConditionTemplate
{

	@Override
	public boolean canPlace(final Template template, final IBlockAccess world, final BlockPos placedAt, final Template.BlockInfo block)
	{
		if (block.blockState.getBlock() != Blocks.STRUCTURE_VOID)
		{
			if (!world.canAccess(block.pos))
			{
				return false;
			}

			return (TemplateUtil.isReplaceable(world, block.pos) || world.getBlockState(block.pos).getMaterial().isSolid()
					|| block.blockState.getBlock() == Blocks.AIR) && (block.blockState.getBlock() == Blocks.AIR
					|| world.getBlockState(block.pos) != Blocks.AIR.getDefaultState());
		}

		return true;
	}

	@Override
	public boolean canPlaceCheckAll(final Template template, final IBlockAccess world, final BlockPos placedAt, final List<Template.BlockInfo> blocks)
	{
		return true;
	}

}
