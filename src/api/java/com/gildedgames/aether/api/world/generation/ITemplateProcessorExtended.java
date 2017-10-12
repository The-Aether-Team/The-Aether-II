package com.gildedgames.aether.api.world.generation;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.Template;

import javax.annotation.Nullable;

public interface ITemplateProcessorExtended extends ITemplateProcessor
{

	@Nullable
	Template.BlockInfo processBlock(IBlockAccessExtended worldIn, BlockPos pos, Template.BlockInfo blockInfoIn);

}