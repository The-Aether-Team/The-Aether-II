package com.gildedgames.aether.common.world.gen.templates.conditions;

import com.gildedgames.aether.common.world.dimensions.aether.features.WorldGenTemplate;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.Template;

import java.util.List;

public class UndergroundPlacementCondition implements WorldGenTemplate.PlacementCondition
{

	@Override
	public boolean canPlace(Template template, World world, BlockPos placedAt, Template.BlockInfo block)
	{
		if (block.blockState.getBlock() != Blocks.STRUCTURE_VOID)
		{
			if ((!WorldGenTemplate.isReplaceable(world, block.pos) && !world.getBlockState(block.pos).getMaterial().isSolid()
					&& block.blockState.getBlock() != Blocks.AIR) || (block.blockState.getBlock() != Blocks.AIR
					&& world.getBlockState(block.pos)
					== Blocks.AIR.getDefaultState()))// || !state.isSideSolid(world, itPos, EnumFacing.UP))
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
