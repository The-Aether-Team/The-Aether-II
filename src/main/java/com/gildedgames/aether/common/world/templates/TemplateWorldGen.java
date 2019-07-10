package com.gildedgames.aether.common.world.templates;

import com.gildedgames.aether.api.world.decoration.WorldDecorationGenerator;
import com.gildedgames.aether.api.world.templates.TemplateDefinition;
import com.gildedgames.aether.api.world.templates.TemplateLoc;
import com.gildedgames.orbis.lib.world.WorldSlice;
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
