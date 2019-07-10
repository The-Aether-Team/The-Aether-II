package com.gildedgames.aether.api.world.templates;

import com.gildedgames.orbis.lib.processing.IBlockAccessExtended;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.Template;

import javax.annotation.Nullable;

public interface ITemplateProcessorExtended extends ITemplateProcessor
{

	@Nullable
	Template.BlockInfo processBlock(IBlockAccessExtended worldIn, BlockPos pos, Template.BlockInfo blockInfoIn);

}
