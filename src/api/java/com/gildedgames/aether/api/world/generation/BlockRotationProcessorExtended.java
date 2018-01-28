package com.gildedgames.aether.api.world.generation;

import com.gildedgames.orbis.api.processing.IBlockAccessExtended;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockRotationProcessorExtended implements ITemplateProcessorExtended
{
	private final float chance;

	private final Random random;

	public BlockRotationProcessorExtended(final BlockPos pos, final PlacementSettings
			settings)
	{
		this.chance = settings.getIntegrity();
		this.random = settings.getRandom(pos);
	}

	@Override
	@Nullable
	public Template.BlockInfo processBlock(final IBlockAccessExtended worldIn, final BlockPos pos, final Template.BlockInfo blockInfoIn)
	{
		return this.chance < 1.0F && this.random.nextFloat() > this.chance ? null : blockInfoIn;
	}

	@Nullable
	@Override
	public Template.BlockInfo processBlock(final World worldIn, final BlockPos pos, final Template.BlockInfo blockInfoIn)
	{
		return this.processBlock((IBlockAccessExtended) null, pos, blockInfoIn);
	}
}