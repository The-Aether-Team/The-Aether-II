package com.gildedgames.aether.api.registrar;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder("aether")
public class BiomesAether extends AbstractRegistrar
{
	@GameRegistry.ObjectHolder("aether_highlands")
	public static final Biome HIGHLANDS = getDefault();

	@GameRegistry.ObjectHolder("aether_magnetic_hills")
	public static final Biome MAGNETIC_HILLS = getDefault();

	@GameRegistry.ObjectHolder("aether_arctic_peaks")
	public static final Biome ARCTIC_PEAKS = getDefault();

	@GameRegistry.ObjectHolder("aether_forgotten_highlands")
	public static final Biome FORGOTTEN_HIGHLANDS = getDefault();

	@GameRegistry.ObjectHolder("aether_irradiated_forests")
	public static final Biome IRRADIATED_FORESTS = getDefault();

	@GameRegistry.ObjectHolder("aether_void")
	public static final Biome VOID = getDefault();

	@GameRegistry.ObjectHolder("instanced_zone")
	public static final Biome INSTANCED_ZONE = getDefault();
}
