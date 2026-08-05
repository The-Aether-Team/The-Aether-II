package com.aetherteam.aetherii.data.resources.registries.holyisles;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.data.resources.builders.worldgen.holyisles.HolyIslesBiomeBuilders;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class HolyIslesBiomes {
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
    public static final ResourceKey<Biome> AERCLOUD_SEA = createKey("aercloud_sea");
    public static final ResourceKey<Biome> HIGHFIELDS_EXPANSE = createKey("highfields_expanse");
    public static final ResourceKey<Biome> MAGNETIC_EXPANSE = createKey("magnetic_expanse");
    public static final ResourceKey<Biome> ARCTIC_EXPANSE = createKey("arctic_expanse");
    public static final ResourceKey<Biome> IRRADIATED_EXPANSE = createKey("irradiated_expanse");

    // Caves
    public static final ResourceKey<Biome> HIGHFIELDS_UNDERCLOUD = createKey("highfields_undercloud");
    public static final ResourceKey<Biome> MAGNETIC_UNDERCLOUD = createKey("magnetic_undercloud");
    public static final ResourceKey<Biome> ARCTIC_UNDERCLOUD = createKey("arctic_undercloud");
    public static final ResourceKey<Biome> IRRADIATED_UNDERCLOUD = createKey("irradiated_undercloud");

    public static final ResourceKey<Biome> HESTVEIL_CAVERNS = createKey("hestveil_caverns");

    private static ResourceKey<Biome> createKey(String name) {
        return ResourceKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(AetherII.MODID, name));
    }
    public static void bootstrap(BootstrapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> vanillaConfiguredCarvers = context.lookup(Registries.CONFIGURED_CARVER);

        // Holy Isles
        context.register(FLOURISHING_FIELD, HolyIslesBiomeBuilders.flourishingFieldBiome(placedFeatures, vanillaConfiguredCarvers, 0.8F, 0.4F));
        context.register(VERDANT_WOODS, HolyIslesBiomeBuilders.verdantWoodsBiome(placedFeatures, vanillaConfiguredCarvers, 0.7F, 0.8F));
        context.register(SHROUDED_FOREST, HolyIslesBiomeBuilders.shroudedForestBiome(placedFeatures, vanillaConfiguredCarvers, 0.7F, 0.8F));
        context.register(SHIMMERING_BASIN, HolyIslesBiomeBuilders.shimmeringBasinBiome(placedFeatures, vanillaConfiguredCarvers, 0.5F, 0.5F));

        context.register(MAGNETIC_SCAR, HolyIslesBiomeBuilders.magneticScarBiome(placedFeatures, vanillaConfiguredCarvers, 0.6F, 0.3F));
        context.register(TURQUOISE_FOREST, HolyIslesBiomeBuilders.turquoiseForestBiome(placedFeatures, vanillaConfiguredCarvers, 0.7F, 0.8F));
        context.register(GLISTENING_SWAMP, HolyIslesBiomeBuilders.glisteningSwampBiome(placedFeatures, vanillaConfiguredCarvers, 0.8F, 0.9F));
        context.register(VIOLET_HIGHWOODS, HolyIslesBiomeBuilders.violetHighwoodsBiome(placedFeatures, vanillaConfiguredCarvers, 0.7F, 0.8F));

        context.register(FRIGID_SIERRA, HolyIslesBiomeBuilders.frigidSierraBiome(placedFeatures, vanillaConfiguredCarvers, -0.5F, 0.9F));
        context.register(ENDURING_WOODLAND, HolyIslesBiomeBuilders.enduringWoodlandBiome(placedFeatures, vanillaConfiguredCarvers, -0.3F, 0.8F));
        context.register(FROZEN_LAKES, HolyIslesBiomeBuilders.frozenLakesBiome(placedFeatures, vanillaConfiguredCarvers, -0.2F, 0.7F));
        context.register(SHEER_TUNDRA, HolyIslesBiomeBuilders.sheerTundraBiome(placedFeatures, vanillaConfiguredCarvers, -0.4F, 0.7F));

        context.register(CONTAMINATED_JUNGLE, HolyIslesBiomeBuilders.contaminatedJungleBiome(placedFeatures, vanillaConfiguredCarvers, 1.0F, 0.3F));
        context.register(BATTLEGROUND_WASTES, HolyIslesBiomeBuilders.battlegroundWastesBiome(placedFeatures, vanillaConfiguredCarvers, 1.0F, 0.3F));

        context.register(AERCLOUD_SEA, HolyIslesBiomeBuilders.makeAercloudSeaBiome(placedFeatures, vanillaConfiguredCarvers, 0.5F, 0.5F));
        context.register(HIGHFIELDS_EXPANSE, HolyIslesBiomeBuilders.makeHighfieldsExpanseBiome(placedFeatures, vanillaConfiguredCarvers, 0.7F, 0.6F));
        context.register(MAGNETIC_EXPANSE, HolyIslesBiomeBuilders.makeAercloudSeaBiome(placedFeatures, vanillaConfiguredCarvers, 0.7F, 0.6F));
        context.register(ARCTIC_EXPANSE, HolyIslesBiomeBuilders.makeAercloudSeaBiome(placedFeatures, vanillaConfiguredCarvers, -0.35F, 0.8F));
        context.register(IRRADIATED_EXPANSE, HolyIslesBiomeBuilders.makeAercloudSeaBiome(placedFeatures, vanillaConfiguredCarvers, 1.0F, 0.3F));

        context.register(HIGHFIELDS_UNDERCLOUD, HolyIslesBiomeBuilders.highfieldsUndercloudBiome(placedFeatures, vanillaConfiguredCarvers, 0.7F, 0.6F));
        context.register(MAGNETIC_UNDERCLOUD, HolyIslesBiomeBuilders.magneticUndercloudBiome(placedFeatures, vanillaConfiguredCarvers, 0.7F, 0.6F));
        context.register(ARCTIC_UNDERCLOUD, HolyIslesBiomeBuilders.arcticUndercloudBiome(placedFeatures, vanillaConfiguredCarvers, -0.35F, 0.8F));
        context.register(IRRADIATED_UNDERCLOUD, HolyIslesBiomeBuilders.irradiatedUndercloudBiome(placedFeatures, vanillaConfiguredCarvers, 1.0F, 0.3F));

        context.register(HESTVEIL_CAVERNS, HolyIslesBiomeBuilders.makeHeastveilCavernsBiome(placedFeatures, vanillaConfiguredCarvers, 0.5F, 0.5F));
    }
}