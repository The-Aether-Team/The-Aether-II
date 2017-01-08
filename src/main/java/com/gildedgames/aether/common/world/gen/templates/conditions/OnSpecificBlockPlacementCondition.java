package com.gildedgames.aether.common.world.gen.templates.conditions;

import com.gildedgames.aether.common.world.dimensions.aether.features.WorldGenTemplate;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.Template;

import java.util.List;

public class OnSpecificBlockPlacementCondition implements WorldGenTemplate.PlacementCondition
{

	private Block[] blocks;

	public OnSpecificBlockPlacementCondition(Block... blocks)
	{
		this.blocks = blocks;
	}

	@Override
	public boolean canPlace(Template template, World world, BlockPos placedAt, Template.BlockInfo block)
	{
		if (block.pos.getY() == placedAt.getY() && block.blockState.getBlock() != Blocks.AIR
				&& block.blockState.getBlock() != Blocks.STRUCTURE_VOID)
		{
			BlockPos down = block.pos.down();

			Block blockDown = world.getBlockState(down).getBlock();

			for (Block s : this.blocks)
			{
				if (s == blockDown)
				{
					return true;
				}
			}

			return false;
		}

		return true;
	}

	@Override
	public boolean canPlaceCheckAll(Template template, World world, BlockPos placedAt, List<Template.BlockInfo> blocks)
	{
		return true;
	}

}
