package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.data.resources.builders.highlands.HighlandsBiomeBuilders;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class AetherIIBiomes {

    // Highfields
    public static final ResourceKey<Biome> FLOURISHING_FIELD = createKey("flourishing_field");
    public static final ResourceKey<Biome> VERDANT_WOODS = createKey("verdant_woods");
    public static final ResourceKey<Biome> SHROUDED_FOREST = createKey("shrouded_forest");
    public static final ResourceKey<Biome> SHIMMERING_BASIN = createKey("shimmering_basin");

    // Magnetic
    public static final ResourceKey<Biome> MAGNETIC_SCAR = createKey("magnetic_scar");
    public static final ResourceKey<Biome> TURQUOISE_FOREST = createKey("turquoise_forest");
    public static final ResourceKey<Biome> GLISTENING_SWAMP = createKey("glistening_swamp");
    public static final ResourceKey<Biome> VIOLET_HIGHWOODS = createKey("violet_highwoods");

    // Arctic
    public static final ResourceKey<Biome> FRIGID_SIERRA = createKey("frigid_sierra");
    public static final ResourceKey<Biome> ENDURING_WOODLAND = createKey("enduring_woodland");
    public static final ResourceKey<Biome> FROZEN_LAKES = createKey("frozen_lakes");
    public static final ResourceKey<Biome> SHEER_TUNDRA = createKey("sheer_tundra");

    // Irradiated
    public static final ResourceKey<Biome> CONTAMINATED_JUNGLE = createKey("contaminated_jungle");
    public static final ResourceKey<Biome> BATTLEGROUND_WASTES = createKey("battleground_wastes");

    // Aercloud Sea
    public static final ResourceKey<Biome> EXPANSE = createKey("expanse");

    private static ResourceKey<Biome> createKey(String name) {
        return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void bootstrap(BootstrapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> vanillaConfiguredCarvers = context.lookup(Registries.CONFIGURED_CARVER);

        // Highlands
        context.register(FLOURISHING_FIELD, HighlandsBiomeBuilders.flourishingFieldBiome(placedFeatures, vanillaConfiguredCarvers, 0.8F, 0.4F));
        context.register(VERDANT_WOODS, HighlandsBiomeBuilders.verdantWoodsBiome(placedFeatures, vanillaConfiguredCarvers, 0.7F, 0.8F));
        context.register(SHROUDED_FOREST, HighlandsBiomeBuilders.shroudedForestBiome(placedFeatures, vanillaConfiguredCarvers, 0.7F, 0.8F));
        context.register(SHIMMERING_BASIN, HighlandsBiomeBuilders.shimmeringBasinBiome(placedFeatures, vanillaConfiguredCarvers, 0.5F, 0.5F));

        context.register(MAGNETIC_SCAR, HighlandsBiomeBuilders.magneticScarBiome(placedFeatures, vanillaConfiguredCarvers, 0.6F, 0.3F));
        context.register(TURQUOISE_FOREST, HighlandsBiomeBuilders.turquoiseForestBiome(placedFeatures, vanillaConfiguredCarvers, 0.7F, 0.8F));
        context.register(GLISTENING_SWAMP, HighlandsBiomeBuilders.glisteningSwampBiome(placedFeatures, vanillaConfiguredCarvers, 0.8F, 0.9F));
        context.register(VIOLET_HIGHWOODS, HighlandsBiomeBuilders.violetHighwoodsBiome(placedFeatures, vanillaConfiguredCarvers, 0.7F, 0.8F));

        context.register(FRIGID_SIERRA, HighlandsBiomeBuilders.frigidSierraBiome(placedFeatures, vanillaConfiguredCarvers, -0.3F, 0.9F));
        context.register(ENDURING_WOODLAND, HighlandsBiomeBuilders.enduringWoodlandBiome(placedFeatures, vanillaConfiguredCarvers, -0.5F, 0.4F));
        context.register(FROZEN_LAKES, HighlandsBiomeBuilders.frozenLakesBiome(placedFeatures, vanillaConfiguredCarvers, 0.0F, 0.5F));
        context.register(SHEER_TUNDRA, HighlandsBiomeBuilders.sheerTundraBiome(placedFeatures, vanillaConfiguredCarvers, 0.35F, 0.5F));

        context.register(CONTAMINATED_JUNGLE, HighlandsBiomeBuilders.contaminatedJungleBiome(placedFeatures, vanillaConfiguredCarvers, 1.0F, 0.3F));
        context.register(BATTLEGROUND_WASTES, HighlandsBiomeBuilders.battlegroundWastesBiome(placedFeatures, vanillaConfiguredCarvers, 1.0F, 0.3F));

        context.register(EXPANSE, HighlandsBiomeBuilders.makeAercloudSeaBiome(placedFeatures, vanillaConfiguredCarvers, 0.5F, 0.5F));
    }
}