package com.gildedgames.aether.common.world.templates;

import com.gildedgames.aether.api.world.generation.TemplateDefinition;
import com.gildedgames.aether.api.world.generation.TemplateLoc;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class TemplateWorldGen extends WorldGenerator
{
	private final TemplateDefinition def;

	public TemplateWorldGen(final TemplateDefinition def)
	{
		this.def = def;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos position)
	{
		return TemplatePlacer.place(world, this.def, new TemplateLoc().set(position), rand);
	}
}
