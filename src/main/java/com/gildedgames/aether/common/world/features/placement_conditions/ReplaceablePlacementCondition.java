package com.gildedgames.aether.common.world.features.placement_conditions;

import com.gildedgames.aether.common.world.features.WorldGenTemplate;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.Template;

import java.util.List;

public class ReplaceablePlacementCondition implements WorldGenTemplate.PlacementCondition
{

	@Override
	public boolean canPlace(World world, BlockPos placedAt, List<Template.BlockInfo> blocks)
	{
		for (Template.BlockInfo block : blocks)
		{
			if (block.blockState.getBlock() != Blocks.AIR && block.blockState.getBlock() != Blocks.STRUCTURE_VOID)
			{
				if (!WorldGenTemplate.isReplaceable(world, block.pos))// || !state.isSideSolid(world, itPos, EnumFacing.UP))
				{
					return false;
				}
			}
		}

		return true;
	}

}
