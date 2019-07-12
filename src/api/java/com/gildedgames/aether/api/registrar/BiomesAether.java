package com.gildedgames.aether.api.registrar;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("aether")
public class BiomesAether extends AbstractRegistrar
{
	@ObjectHolder("aether_highlands")
	public static final Biome HIGHLANDS = getDefault();

	@ObjectHolder("aether_magnetic_hills")
	public static final Biome MAGNETIC_HILLS = getDefault();

	@ObjectHolder("aether_arctic_peaks")
	public static final Biome ARCTIC_PEAKS = getDefault();

	@ObjectHolder("aether_forgotten_highlands")
	public static final Biome FORGOTTEN_HIGHLANDS = getDefault();

	@ObjectHolder("aether_irradiated_forests")
	public static final Biome IRRADIATED_FORESTS = getDefault();

	@ObjectHolder("aether_void")
	public static final Biome VOID = getDefault();

	@ObjectHolder("instanced_zone")
	public static final Biome INSTANCED_ZONE = getDefault();
}
