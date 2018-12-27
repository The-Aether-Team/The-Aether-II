package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.aether.biomes.BiomeVoid;
import com.gildedgames.aether.common.world.aether.biomes.arctic_peaks.BiomeArcticPeaks;
import com.gildedgames.aether.common.world.aether.biomes.forgotten_highlands.BiomeForgottenHighlands;
import com.gildedgames.aether.common.world.aether.biomes.highlands.BiomeHighlands;
import com.gildedgames.aether.common.world.aether.biomes.irradiated_forests.BiomeIrradiatedForests;
import com.gildedgames.aether.common.world.aether.biomes.magnetic_hills.BiomeMagneticHills;
import com.gildedgames.aether.common.world.util.BiomeInstancedZone;
import com.google.common.collect.Lists;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber()
@GameRegistry.ObjectHolder(AetherCore.MOD_ID)
public class BiomesAether
{
	@GameRegistry.ObjectHolder("aether_highlands")
	public static final BiomeAetherBase HIGHLANDS = new BiomeHighlands(
			new Biome.BiomeProperties("Highlands").setTemperature(0.5f).setWaterColor(0x70DB70), AetherCore.getResource("aether_highlands"));

	@GameRegistry.ObjectHolder("aether_magnetic_hills")
	public static final BiomeAetherBase MAGNETIC_HILLS = new BiomeMagneticHills(
			new Biome.BiomeProperties("Magnetic Hills").setTemperature(0.5f).setWaterColor(0x70DB70),
			AetherCore.getResource("aether_magnetic_hills"));

	@GameRegistry.ObjectHolder("aether_arctic_peaks")
	public static final BiomeAetherBase ARCTIC_PEAKS = new BiomeArcticPeaks(
			new Biome.BiomeProperties("Arctic Peaks").setRainDisabled().setSnowEnabled().setTemperature(0.0f).setWaterColor(0x70DB70),
			AetherCore.getResource("aether_arctic_peaks"));

	@GameRegistry.ObjectHolder("aether_forgotten_highlands")
	public static final BiomeAetherBase FORGOTTEN_HIGHLANDS = new BiomeForgottenHighlands(
			new Biome.BiomeProperties("Forgotten Highlands").setTemperature(0.0f).setWaterColor(0x70DB70),
			AetherCore.getResource("aether_forgotten_highlands"));

	@GameRegistry.ObjectHolder("aether_irradiated_forests")
	public static final BiomeAetherBase IRRADIATED_FORESTS = new BiomeIrradiatedForests(
			new Biome.BiomeProperties("Irradiated Forests").setTemperature(0.0f).setWaterColor(0xdebc33),
			AetherCore.getResource("aether_irradiated_forests"));

	@GameRegistry.ObjectHolder("aether_void")
	public static final Biome VOID = new BiomeVoid();

	@GameRegistry.ObjectHolder("instanced_zone")
	public static final Biome INSTANCED_ZONE = new BiomeInstancedZone();

	public static List<BiomeAetherBase> POSSIBLE_BIOMES = Lists.newArrayList();

	@SubscribeEvent
	public static void registerBiome(final RegistryEvent.Register<Biome> event)
	{
		event.getRegistry()
				.registerAll(HIGHLANDS, MAGNETIC_HILLS, ARCTIC_PEAKS, FORGOTTEN_HIGHLANDS, IRRADIATED_FORESTS, VOID, INSTANCED_ZONE);

		BiomeDictionary.addTypes(HIGHLANDS, BiomeDictionary.Type.PLAINS);
		BiomeDictionary.addTypes(MAGNETIC_HILLS, BiomeDictionary.Type.HILLS, BiomeDictionary.Type.HOT, BiomeDictionary.Type.SPARSE);
		BiomeDictionary.addTypes(ARCTIC_PEAKS, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY, BiomeDictionary.Type.DENSE);
		BiomeDictionary.addTypes(FORGOTTEN_HIGHLANDS, BiomeDictionary.Type.DENSE);
		BiomeDictionary.addTypes(IRRADIATED_FORESTS, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.HOT, BiomeDictionary.Type.DENSE);
		BiomeDictionary.addTypes(VOID, BiomeDictionary.Type.VOID);
		BiomeDictionary.addTypes(INSTANCED_ZONE, BiomeDictionary.Type.VOID);

		registerPossibleBiome(HIGHLANDS, MAGNETIC_HILLS, ARCTIC_PEAKS, FORGOTTEN_HIGHLANDS, IRRADIATED_FORESTS);
	}

	public static void registerPossibleBiome(final BiomeAetherBase... biomes)
	{
		POSSIBLE_BIOMES.addAll(Arrays.asList(biomes));
	}

	public static float getTotalBiomeWeight()
	{
		float total = 0.0F;

		for (final BiomeAetherBase b : POSSIBLE_BIOMES)
		{
			total += b.getRarityWeight();
		}

		return total;
	}

	public static BiomeAetherBase fetchRandomBiome(final Random rand)
	{/*

		final float randomValue = rand.nextFloat() * getTotalBiomeWeight();
		float chanceSum = 0.0F;

		for (final BiomeAetherBase b : POSSIBLE_BIOMES)
		{
			if (b.getRarityWeight() + chanceSum >= randomValue)
			{
				return b;
			}

			chanceSum += b.getRarityWeight();
		}

		return null;
		 */

		return ARCTIC_PEAKS;
	}
}
