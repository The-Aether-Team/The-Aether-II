package com.gildedgames.aether.common.registry.minecraft;

import com.gildedgames.aether.common.world.biome.BiomeAether;
import com.gildedgames.aether.common.world.labyrinth.BiomeSliderLabyrinth;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BiomesAether
{
	public static final BiomeAether BIOME_AETHER = new BiomeAether();

	public static final BiomeSliderLabyrinth BIOME_SLIDER_LABYRINTH = new BiomeSliderLabyrinth();

	public static void preInit()
	{
		GameRegistry.register(BIOME_AETHER);
		GameRegistry.register(BIOME_SLIDER_LABYRINTH);
	}
}
