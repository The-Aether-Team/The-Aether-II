package com.gildedgames.aether.common.world.features.placement_conditions;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.world.features.WorldGenTemplate;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.Template;

import java.util.List;

public class UndergroundEntrancePlacementCondition implements WorldGenTemplate.PlacementCondition
{

	@Override
	public boolean canPlace(Template template, World world, BlockPos placedAt, List<Template.BlockInfo> blocks)
	{
		List<BlockPos> posToDelete = Lists.newArrayList();

		for (Template.BlockInfo block : blocks)
		{
			if (block.pos.getY() == placedAt.getY() + template.getSize().getY() - 1 && block.blockState.getBlock() != Blocks.STRUCTURE_VOID)
			{
				BlockPos up = block.pos.up();

				if (!WorldGenTemplate.isReplaceable(world, up))
				{
					return false;
				}
				else if (!world.isAirBlock(up))
				{
					posToDelete.add(up);
				}

				if (block.blockState.getBlock() != Blocks.AIR)
				{
					IBlockState state = world.getBlockState(block.pos);

					if (state.getBlock() != BlocksAether.aether_grass)
					{
						return false;
					}
				}
			}
		}

		for (BlockPos pos : posToDelete)
		{
			world.setBlockToAir(pos);
		}

		return true;
	}

}
