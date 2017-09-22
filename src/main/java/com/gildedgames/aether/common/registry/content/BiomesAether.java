package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.world.aether.biomes.BiomeVoid;
import com.gildedgames.aether.common.world.aether.biomes.highlands.BiomeHighlands;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BiomesAether
{

	public static final Biome HIGHLANDS = new BiomeHighlands(
			new Biome.BiomeProperties("Highlands").setRainDisabled().setTemperature(0.5f).setWaterColor(0x70DB70), AetherCore.getResource("aether_highlands"));

	public static final Biome VOID = new BiomeVoid();

	public static void preInit()
	{
		GameRegistry.register(HIGHLANDS);
		GameRegistry.register(VOID);
	}
}
