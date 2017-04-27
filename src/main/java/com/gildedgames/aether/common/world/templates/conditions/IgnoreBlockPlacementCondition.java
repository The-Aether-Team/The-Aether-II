package com.gildedgames.aether.common.world.templates.conditions;

import com.gildedgames.aether.common.world.dimensions.aether.features.WorldGenTemplate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.Template;

import java.util.List;

public class IgnoreBlockPlacementCondition implements WorldGenTemplate.PlacementCondition
{

	private IBlockState state;

	public IgnoreBlockPlacementCondition(IBlockState state)
	{
		this.state = state;
	}

	@Override
	public boolean canPlace(Template template, World world, BlockPos placedAt, Template.BlockInfo block)
	{
		if (block.pos.getY() == placedAt.getY() && block.blockState.getBlock() != Blocks.AIR
				&& block.blockState.getBlock() != Blocks.STRUCTURE_VOID)
		{
			BlockPos down = block.pos.down();

			IBlockState state = world.getBlockState(down);

			if (this.state == state)
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
