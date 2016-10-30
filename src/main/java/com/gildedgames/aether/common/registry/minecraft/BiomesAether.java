package com.gildedgames.aether.common.registry.minecraft;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.world.biome.BiomeVoid;
import com.gildedgames.aether.common.world.dimensions.aether.biomes.highlands.BiomeHighlands;
import com.gildedgames.aether.common.world.dimensions.labyrinth.BiomeSliderLabyrinth;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BiomesAether
{

	public static final Biome HIGHLANDS = new BiomeHighlands(new Biome.BiomeProperties("Highlands").setRainDisabled().setTemperature(0.5f), AetherCore.getResource("aether_highlands"));

	public static final Biome VOID = new BiomeVoid();

	public static final Biome BIOME_SLIDER_LABYRINTH = new BiomeSliderLabyrinth();

	public static void preInit()
	{
		GameRegistry.register(HIGHLANDS);

		GameRegistry.register(VOID);

		GameRegistry.register(BIOME_SLIDER_LABYRINTH);
	}
}
