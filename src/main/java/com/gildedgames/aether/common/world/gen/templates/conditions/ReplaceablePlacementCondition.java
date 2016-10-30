package com.gildedgames.aether.common.world.gen.templates.conditions;

import com.gildedgames.aether.common.world.dimensions.aether.features.WorldGenTemplate;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.Template;

import java.util.List;

public class ReplaceablePlacementCondition implements WorldGenTemplate.PlacementCondition
{

	@Override
	public boolean canPlace(Template template, World world, BlockPos placedAt, Template.BlockInfo block)
	{
		if (block.blockState.getBlock() != Blocks.STRUCTURE_VOID)
		{
			if (block.blockState.getMaterial().isSolid() && WorldGenTemplate.isReplaceable(world, block.pos))
			{
				return true;
			}
			else if (block.blockState != world.getBlockState(block.pos) && !world.isAirBlock(block.pos))
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean canPlaceCheckAll(Template template, World world, BlockPos placedAt, List<Template.BlockInfo> blocks)
	{
		return true;
	}

}
