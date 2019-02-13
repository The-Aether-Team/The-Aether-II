package com.gildedgames.aether.common.world.templates;

import com.gildedgames.aether.api.world.generation.TemplateDefinition;
import com.gildedgames.aether.api.world.generation.TemplateLoc;
import com.gildedgames.aether.api.world.generation.WorldDecorationGenerator;
import com.gildedgames.orbis_api.world.WorldSlice;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class TemplateWorldGen implements WorldDecorationGenerator
{
	private final TemplateDefinition def;

	public TemplateWorldGen(final TemplateDefinition def)
	{
		this.def = def;
	}

	@Override
	public boolean generate(WorldSlice slice, Random rand, BlockPos pos)
	{
		return TemplatePlacer.place(slice.getWorld(), this.def, new TemplateLoc().set(pos), rand);
	}
}
