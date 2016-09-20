package com.gildedgames.aether.common.registry.minecraft;

import com.gildedgames.aether.common.world.biome.BiomeEnchantedForest;
import com.gildedgames.aether.common.world.biome.highlands.BiomeHighlandForest;
import com.gildedgames.aether.common.world.biome.highlands.BiomeHighlandPlains;
import com.gildedgames.aether.common.world.biome.highlands.BiomeHighlands;
import com.gildedgames.aether.common.world.biome.BiomeVoid;
import com.gildedgames.aether.common.world.biome.highlands.BiomeHighlandsCrystal;
import com.gildedgames.aether.common.world.labyrinth.BiomeSliderLabyrinth;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BiomesAether
{

	public static final Biome HIGHLANDS = new BiomeHighlands();

	public static final Biome HIGHLANDS_CRYSTAL = new BiomeHighlandsCrystal();

	public static final Biome HIGHLANDS_PLAINS = new BiomeHighlandPlains();

	public static final Biome HIGHLANDS_FOREST = new BiomeHighlandForest();

	public static final Biome ENCHANTED_FOREST = new BiomeEnchantedForest();

	public static final Biome VOID = new BiomeVoid();

	public static final Biome BIOME_SLIDER_LABYRINTH = new BiomeSliderLabyrinth();

	public static void preInit()
	{
		GameRegistry.register(HIGHLANDS);
		GameRegistry.register(HIGHLANDS_CRYSTAL);
		GameRegistry.register(HIGHLANDS_PLAINS);
		GameRegistry.register(HIGHLANDS_FOREST);

		GameRegistry.register(VOID);
		GameRegistry.register(ENCHANTED_FOREST);
		GameRegistry.register(BIOME_SLIDER_LABYRINTH);
	}
}
