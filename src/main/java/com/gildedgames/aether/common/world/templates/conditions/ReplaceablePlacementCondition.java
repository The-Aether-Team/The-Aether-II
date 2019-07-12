package com.gildedgames.aether.common.world.templates.conditions;

import com.gildedgames.aether.api.util.TemplateUtil;
import com.gildedgames.aether.api.world.templates.PlacementConditionTemplate;
import com.gildedgames.orbis.lib.processing.IBlockAccess;
import com.gildedgames.orbis.lib.util.mc.BlockUtil;
import com.google.common.collect.Lists;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.template.Template;

import java.util.List;

public class ReplaceablePlacementCondition implements PlacementConditionTemplate
{

	private final boolean isCriticalWithCheck;

	private final List<Material> acceptedMaterials;

	public ReplaceablePlacementCondition(final boolean isCriticalWithCheck, final Material... acceptedMaterials)
	{
		this.isCriticalWithCheck = isCriticalWithCheck;
		this.acceptedMaterials = Lists.newArrayList(acceptedMaterials);
	}

	@Override
	public boolean canPlace(final Template template, final IBlockAccess world, final BlockPos placedAt, final Template.BlockInfo block)
	{
		if (block.blockState.getBlock() != Blocks.STRUCTURE_VOID)
		{
			if (!world.canAccess(block.pos))
			{
				return false;
			}

			final BlockState state = world.getBlockState(block.pos);

			if ((BlockUtil.isSolid(block.blockState) || block.blockState.getMaterial() == Material.PORTAL
					|| block.blockState == Blocks.AIR.getDefaultState()) && (TemplateUtil.isReplaceable(world, block.pos)
					|| this.acceptedMaterials.contains(state.getMaterial())))
			{
				return true;
			}

			if ((this.isCriticalWithCheck ? block.blockState == state : block.blockState.getBlock() == state.getBlock())
					|| this.acceptedMaterials.contains(state.getMaterial()))
			{
				return true;
			}

			return world.isAirBlock(block.pos);
		}

		return true;
	}

	@Override
	public boolean canPlaceCheckAll(final Template template, final IBlockAccess world, final BlockPos placedAt, final List<Template.BlockInfo> blocks)
	{
		return true;
	}

}
