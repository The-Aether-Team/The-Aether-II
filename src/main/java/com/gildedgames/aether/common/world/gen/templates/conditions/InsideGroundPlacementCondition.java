package com.gildedgames.aether.common.world.gen.templates.conditions;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.world.dimensions.aether.features.WorldGenTemplate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.Template;

import java.util.List;

public class InsideGroundPlacementCondition implements WorldGenTemplate.PlacementCondition
{

	@Override
	public boolean canPlace(Template template, World world, BlockPos placedAt, Template.BlockInfo block)
	{
		if (block.pos.getY() == placedAt.getY() + 1 && block.blockState.getBlock() != Blocks.AIR
				&& block.blockState.getBlock() != Blocks.STRUCTURE_VOID)
		{
			BlockPos down = block.pos.down();

			IBlockState state = world.getBlockState(down);

			if (state.getBlock() != BlocksAether.aether_grass)
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
