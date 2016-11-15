package com.gildedgames.aether.common.world.gen.templates.conditions;

import com.gildedgames.aether.common.util.helpers.BlockUtil;
import com.gildedgames.aether.common.world.dimensions.aether.features.WorldGenTemplate;
import com.google.common.collect.Lists;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.Template;

import java.util.List;

public class ReplaceablePlacementCondition implements WorldGenTemplate.PlacementCondition
{

	private List<Material> acceptedMaterials;

	private final boolean isCriticalWithCheck;

	public ReplaceablePlacementCondition(boolean isCriticalWithCheck, Material... acceptedMaterials)
	{
		this.isCriticalWithCheck = isCriticalWithCheck;
		this.acceptedMaterials = Lists.newArrayList(acceptedMaterials);
	}

	@Override
	public boolean canPlace(Template template, World world, BlockPos placedAt, Template.BlockInfo block)
	{
		if (block.blockState.getBlock() != Blocks.STRUCTURE_VOID)
		{
			IBlockState state = world.getBlockState(block.pos);

			if ((BlockUtil.isSolid(block.blockState) || block.blockState.getMaterial() == Material.PORTAL || block.blockState == Blocks.AIR.getDefaultState()) && (WorldGenTemplate.isReplaceable(world, block.pos) || this.acceptedMaterials.contains(state.getMaterial())))
			{
				return true;
			}

			if ((this.isCriticalWithCheck ? block.blockState == state : block.blockState.getBlock() == state.getBlock()) || this.acceptedMaterials.contains(state.getMaterial()))
			{
				return true;
			}

			if (!world.isAirBlock(block.pos))
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
