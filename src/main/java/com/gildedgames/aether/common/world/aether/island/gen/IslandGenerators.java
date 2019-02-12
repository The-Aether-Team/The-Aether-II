package com.gildedgames.aether.common.world.aether.island.gen;

import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.common.world.aether.island.gen.types.IslandGeneratorHighlandMegacoast;
import com.gildedgames.aether.common.world.aether.island.gen.types.IslandGeneratorIrradiatedForests;

public class IslandGenerators
{

	public static final IIslandGenerator HIGHLAND_MEGACOAST = new IslandGeneratorHighlandMegacoast();

//	public static final IIslandGenerator FORGOTTEN_HIGHLANDS = new IslandGeneratorForgottenHighlands();

	public static final IIslandGenerator IRRADIATED_FORESTS = new IslandGeneratorIrradiatedForests();

}
