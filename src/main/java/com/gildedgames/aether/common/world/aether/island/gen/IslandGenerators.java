package com.gildedgames.aether.common.world.aether.island.gen;

import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.common.world.aether.island.gen.highlands.IslandGeneratorHighlandMegacoast;

public class IslandGenerators
{

	public static IIslandGenerator HIGHLAND_MEGACOAST = new IslandGeneratorHighlandMegacoast();

	public static IIslandGenerator FORGOTTEN_HIGHLANDS = new IslandGeneratorForgottenHighlands();

	public static IIslandGenerator ARCTIC_PEAKS = new IslandGeneratorArcticPeaks();

	public static IIslandGenerator MAGNETIC_HILLS = new IslandGeneratorMagneticHills();

	public static IIslandGenerator IRRADIATED_FORESTS = new IslandGeneratorIrradiatedForests();

}
