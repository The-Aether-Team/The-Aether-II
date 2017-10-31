package com.gildedgames.aether.api.orbis_core.api;

import com.gildedgames.aether.api.orbis_core.api.util.BlueprintPlacer;
import com.gildedgames.aether.api.orbis_core.processing.DataPrimer;
import com.gildedgames.aether.api.world.generation.IBlockAccessExtended;
import com.gildedgames.aether.api.world.generation.IWorldGen;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlueprintWorldGen implements IWorldGen
{

	private final BlueprintDefinition def;

	private final BlueprintDefinitionPool defPool;

	public BlueprintWorldGen(final BlueprintDefinitionPool defPool)
	{
		this.def = null;
		this.defPool = defPool;
	}

	public BlueprintWorldGen(final BlueprintDefinition def)
	{
		this.def = def;
		this.defPool = null;
	}

	@Override
	public boolean generate(final IBlockAccessExtended blockAccess, final World world, final Random rand, final BlockPos position, final boolean centered)
	{
		return BlueprintPlacer
				.place(new DataPrimer(blockAccess), this.def == null ? this.defPool.getRandomDefinition(rand) : this.def,
						new CreationData(world).set(position).set(centered),
						rand);
	}

	@Override
	public boolean generate(final IBlockAccessExtended blockAccess, final World world, final Random rand, final BlockPos position)
	{
		return this.generate(blockAccess, world, rand, position, false);
	}
}