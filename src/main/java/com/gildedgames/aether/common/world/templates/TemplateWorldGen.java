package com.gildedgames.aether.common.world.templates;

import com.gildedgames.aether.api.world.generation.TemplateDefinition;
import com.gildedgames.aether.api.world.generation.TemplateDefinitionPool;
import com.gildedgames.aether.api.world.generation.TemplateLoc;
import com.gildedgames.orbis.api.processing.IBlockAccessExtended;
import com.gildedgames.orbis.api.world.IWorldGen;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class TemplateWorldGen implements IWorldGen
{

	private final TemplateDefinition def;

	private final TemplateDefinitionPool defPool;

	public TemplateWorldGen(final TemplateDefinitionPool defPool)
	{
		this.def = null;
		this.defPool = defPool;
	}

	public TemplateWorldGen(final TemplateDefinition def)
	{
		this.def = def;
		this.defPool = null;
	}

	@Override
	public boolean generate(final IBlockAccessExtended blockAccess, final World world, final Random rand, final BlockPos position, final boolean centered)
	{
		return TemplatePlacer
				.place(blockAccess, this.def == null ? this.defPool.getRandomDefinition(rand) : this.def, new TemplateLoc().set(position).set(centered), rand);
	}

	@Override
	public boolean generate(final IBlockAccessExtended blockAccess, final World world, final Random rand, final BlockPos position)
	{
		return this.generate(blockAccess, world, rand, position, false);
	}
}
