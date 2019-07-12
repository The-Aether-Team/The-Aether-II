package com.gildedgames.aether.api.world.templates;

import com.gildedgames.orbis.lib.processing.IBlockAccess;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.template.Template;

import java.util.List;

public interface PlacementConditionTemplate
{

	boolean canPlace(Template template, IBlockAccess world, BlockPos placedAt, Template.BlockInfo block);

	/** Should return true by default **/
	boolean canPlaceCheckAll(Template template, IBlockAccess world, BlockPos placedAt, List<Template.BlockInfo> blocks);

}