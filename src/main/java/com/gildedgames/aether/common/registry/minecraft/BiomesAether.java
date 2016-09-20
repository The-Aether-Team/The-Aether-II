package com.gildedgames.aether.common.registry.minecraft;

import com.gildedgames.aether.common.world.biome.BiomeAether;
import com.gildedgames.aether.common.world.biome.BiomeAetherEnchanted;
import com.gildedgames.aether.common.world.biome.BiomeVoid;
import com.gildedgames.aether.common.world.labyrinth.BiomeSliderLabyrinth;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BiomesAether
{
	public static final Biome BIOME_AETHER = new BiomeAether();

	public static final Biome BIOME_AETHER_ENCHANTED = new BiomeAetherEnchanted();

	public static final Biome BIOME_AETHER_VOID = new BiomeVoid();

	public static final Biome BIOME_SLIDER_LABYRINTH = new BiomeSliderLabyrinth();

	public static void preInit()
	{
		GameRegistry.register(BIOME_AETHER);
		GameRegistry.register(BIOME_AETHER_VOID);
		GameRegistry.register(BIOME_AETHER_ENCHANTED);
		GameRegistry.register(BIOME_SLIDER_LABYRINTH);
	}
}
