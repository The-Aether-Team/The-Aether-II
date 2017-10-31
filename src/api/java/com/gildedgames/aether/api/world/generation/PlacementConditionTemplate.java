package com.gildedgames.aether.api.world.generation;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.template.Template;

import java.util.List;

public interface PlacementConditionTemplate
{

	boolean canPlace(Template template, IBlockAccessExtended world, BlockPos placedAt, Template.BlockInfo block);

	/** Should return true by default **/
	boolean canPlaceCheckAll(Template template, IBlockAccessExtended world, BlockPos placedAt, List<Template.BlockInfo> blocks);

}