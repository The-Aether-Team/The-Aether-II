package com.gildedgames.aether.common.world.gen.templates.conditions;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.util.helpers.BlockUtil;
import com.gildedgames.aether.common.world.dimensions.aether.features.WorldGenTemplate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.Template;

import java.util.List;

public class InsideGroundAtSourcePlacementCondition implements WorldGenTemplate.PlacementCondition
{

	@Override
	public boolean canPlace(Template template, World world, BlockPos placedAt, Template.BlockInfo block)
	{
		return true;
	}

	@Override
	public boolean canPlaceCheckAll(Template template, World world, BlockPos placedAt, List<Template.BlockInfo> blocks)
	{
		BlockPos down = placedAt.down();

		IBlockState state = world.getBlockState(down);

		if (state.getBlock() != BlocksAether.aether_grass)
		{
			return false;
		}

		for (Template.BlockInfo info : blocks)
		{
			if (info.pos.getY() == placedAt.getY() + 1 && info.blockState.getBlock() != Blocks.AIR && info.blockState.getBlock() != Blocks.STRUCTURE_VOID)
			{
				down = info.pos.down();

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
