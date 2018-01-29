package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.world.aether.biomes.BiomeVoid;
import com.gildedgames.aether.common.world.aether.biomes.highlands.BiomeHighlands;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber()
@GameRegistry.ObjectHolder(AetherCore.MOD_ID)
public class BiomesAether
{
	@GameRegistry.ObjectHolder("aether_highlands")
	public static final Biome HIGHLANDS = new BiomeHighlands(
			new Biome.BiomeProperties("Highlands").setRainDisabled().setTemperature(0.5f).setWaterColor(0x70DB70), AetherCore.getResource("aether_highlands"));

	@GameRegistry.ObjectHolder("aether_void")
	public static final Biome VOID = new BiomeVoid();

	@SubscribeEvent
	public static void registerBiome(final RegistryEvent.Register<Biome> event)
	{
		event.getRegistry().registerAll(HIGHLANDS, VOID);
	}
}
