package com.aetherteam.aetherii.data.resources.builders.worldgen.holyisles;

import com.aetherteam.aetherii.data.resources.registries.holyisles.HolyIslesBiomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;

import java.util.List;

public class HolyIslesBiomeSourceBuilders {
    public static float ARCTIC_START_VALUE = -0.4F;
    public static float IRRADIATED_START_VALUE = 0.65F;
    public static float MAGNETIC_START_VALUE = 0.485F;

    public static BiomeSource buildHolyIslesBiomeSource(HolderGetter<Biome> biomes) {
        Climate.Parameter fullRange = Climate.Parameter.span(-1.5F, 1.5F);

        Climate.Parameter tempArctic = Climate.Parameter.span(-1.5F, ARCTIC_START_VALUE);
        Climate.Parameter tempHighfields = Climate.Parameter.span(ARCTIC_START_VALUE, IRRADIATED_START_VALUE);
        Climate.Parameter tempHighfields1 = Climate.Parameter.span(ARCTIC_START_VALUE, -0.1F);
        Climate.Parameter tempHighfields2 = Climate.Parameter.span(-0.1F, 0.3F);
        Climate.Parameter tempHighfields3 = Climate.Parameter.span(0.3F, 0.6F);
        Climate.Parameter tempMagnetic1 = Climate.Parameter.span(-1.5F, -0.25F);
        Climate.Parameter tempMagnetic2 = Climate.Parameter.span(-0.25F, 0.25F);
        Climate.Parameter tempMagnetic3 = Climate.Parameter.span(0.25F, IRRADIATED_START_VALUE);
        Climate.Parameter tempIrradiated = Climate.Parameter.span(IRRADIATED_START_VALUE, 1.5F);

        Climate.Parameter continentsIsland = Climate.Parameter.span(0.1F, 1.0F);
        Climate.Parameter continentsAercloudSea = Climate.Parameter.span(0.0F, 0.1F);
        Climate.Parameter continentsExpanse = Climate.Parameter.span(1.0F, 2.0F);

        Climate.Parameter erosionDefault = Climate.Parameter.span(0.0F, MAGNETIC_START_VALUE);
        Climate.Parameter erosionMagnetic = Climate.Parameter.span(MAGNETIC_START_VALUE, 1.5F);

        Climate.Parameter depthHestveilCaverns = Climate.Parameter.span(-2.0F, -1.0F);
        Climate.Parameter depthCaves = Climate.Parameter.span(-1.0F, -0.225F);
        Climate.Parameter depthDefault = Climate.Parameter.span(-0.225F, 1.5F);
        Climate.Parameter depthArcticLayer1 = Climate.Parameter.span(-0.225F, 0.3F);
        Climate.Parameter depthArcticLayer2 = Climate.Parameter.span(0.3F, 0.65F);
        Climate.Parameter depthArcticLayer3 = Climate.Parameter.span(0.65F, 1.5F);

        Climate.Parameter ridgeLand = Climate.Parameter.span(-1.0F, 0.3F);
        Climate.Parameter ridgeLake = Climate.Parameter.span(0.3F, 1.0F);

        return MultiNoiseBiomeSource.createFromList(new Climate.ParameterList<>(List.of(

                // Arctic
                Pair.of(new Climate.ParameterPoint(tempArctic, fullRange, continentsIsland, erosionDefault, depthDefault, ridgeLake, 0), biomes.getOrThrow(HolyIslesBiomes.FROZEN_LAKES)),
                Pair.of(new Climate.ParameterPoint(tempArctic, fullRange, continentsIsland, erosionDefault, depthHestveilCaverns, Climate.Parameter.span(0.2F, 0.3F), 0), biomes.getOrThrow(HolyIslesBiomes.FROZEN_LAKES)),

                Pair.of(new Climate.ParameterPoint(tempArctic, Climate.Parameter.span(-1.0F, -0.25F), continentsIsland, erosionDefault, depthArcticLayer1, ridgeLand, 0), biomes.getOrThrow(HolyIslesBiomes.ENDURING_WOODLAND)),
                Pair.of(new Climate.ParameterPoint(tempArctic, Climate.Parameter.span(-0.25F, 0.1F), continentsIsland, erosionDefault, depthArcticLayer1, ridgeLand, 0), biomes.getOrThrow(HolyIslesBiomes.SHEER_TUNDRA)),
                Pair.of(new Climate.ParameterPoint(tempArctic, Climate.Parameter.span(0.1F, 1.0F), continentsIsland, erosionDefault, depthArcticLayer1, ridgeLand, 0), biomes.getOrThrow(HolyIslesBiomes.ENDURING_WOODLAND)),

                Pair.of(new Climate.ParameterPoint(tempArctic, Climate.Parameter.span(-1.0F, -0.1F), continentsIsland, erosionDefault, depthArcticLayer2, ridgeLand, 0), biomes.getOrThrow(HolyIslesBiomes.FRIGID_SIERRA)),
                Pair.of(new Climate.ParameterPoint(tempArctic, Climate.Parameter.span(-0.1F, 0.175F), continentsIsland, erosionDefault, depthArcticLayer2, ridgeLand, 0), biomes.getOrThrow(HolyIslesBiomes.SHEER_TUNDRA)),
                Pair.of(new Climate.ParameterPoint(tempArctic, Climate.Parameter.span(0.175F, 1.0F), continentsIsland, erosionDefault, depthArcticLayer2, ridgeLand, 0), biomes.getOrThrow(HolyIslesBiomes.ENDURING_WOODLAND)),

                Pair.of(new Climate.ParameterPoint(tempArctic, fullRange, continentsIsland, erosionDefault, depthArcticLayer3, ridgeLand, 0), biomes.getOrThrow(HolyIslesBiomes.FRIGID_SIERRA)),

                // Highfields
                Pair.of(new Climate.ParameterPoint(tempHighfields, Climate.Parameter.span(-1.5F, 2.0F), continentsIsland, erosionDefault, depthDefault, ridgeLake, 0), biomes.getOrThrow(HolyIslesBiomes.SHIMMERING_BASIN)),
                Pair.of(new Climate.ParameterPoint(tempHighfields, fullRange, continentsIsland, erosionDefault, depthHestveilCaverns, Climate.Parameter.span(0.2F, 0.3F), 0), biomes.getOrThrow(HolyIslesBiomes.SHIMMERING_BASIN)),

                Pair.of(new Climate.ParameterPoint(tempHighfields1, Climate.Parameter.span(-1.0F, -0.1F), continentsIsland, erosionDefault, depthDefault, ridgeLand, 0), biomes.getOrThrow(HolyIslesBiomes.FLOURISHING_FIELD)),
                Pair.of(new Climate.ParameterPoint(tempHighfields1, Climate.Parameter.span(-0.1F, 0.2F), continentsIsland, erosionDefault, depthDefault, ridgeLand, 0), biomes.getOrThrow(HolyIslesBiomes.VERDANT_WOODS)),
                Pair.of(new Climate.ParameterPoint(tempHighfields1, Climate.Parameter.span(0.2F, 1.0F), continentsIsland, erosionDefault, depthDefault, ridgeLand, 0), biomes.getOrThrow(HolyIslesBiomes.FLOURISHING_FIELD)),
                Pair.of(new Climate.ParameterPoint(tempHighfields1, Climate.Parameter.span(1.0F, 2.0F), continentsIsland, erosionDefault, depthDefault, ridgeLand, 0), biomes.getOrThrow(HolyIslesBiomes.SHROUDED_FOREST)),

                Pair.of(new Climate.ParameterPoint(tempHighfields2, Climate.Parameter.span(-1.0F, -0.15F), continentsIsland, erosionDefault, depthDefault, ridgeLand, 0), biomes.getOrThrow(HolyIslesBiomes.VERDANT_WOODS)),
                Pair.of(new Climate.ParameterPoint(tempHighfields2, Climate.Parameter.span(-0.15F, 1.0F), continentsIsland, erosionDefault, depthDefault, ridgeLand, 0), biomes.getOrThrow(HolyIslesBiomes.FLOURISHING_FIELD)),
                Pair.of(new Climate.ParameterPoint(tempHighfields2, Climate.Parameter.span(1.0F, 2.0F), continentsIsland, erosionDefault, depthDefault, ridgeLand, 0), biomes.getOrThrow(HolyIslesBiomes.SHROUDED_FOREST)),

                Pair.of(new Climate.ParameterPoint(tempHighfields3, Climate.Parameter.span(-1.0F, -0.25F), continentsIsland, erosionDefault, depthDefault, ridgeLand, 0), biomes.getOrThrow(HolyIslesBiomes.FLOURISHING_FIELD)),
                Pair.of(new Climate.ParameterPoint(tempHighfields3, Climate.Parameter.span(-0.25F, 0.1F), continentsIsland, erosionDefault, depthDefault, ridgeLand, 0), biomes.getOrThrow(HolyIslesBiomes.VERDANT_WOODS)),
                Pair.of(new Climate.ParameterPoint(tempHighfields3, Climate.Parameter.span(0.1F, 0.25F), continentsIsland, erosionDefault, depthDefault, ridgeLand, 0), biomes.getOrThrow(HolyIslesBiomes.FLOURISHING_FIELD)),
                Pair.of(new Climate.ParameterPoint(tempHighfields3, Climate.Parameter.span(0.25F, 1.0F), continentsIsland, erosionDefault, depthDefault, ridgeLand, 0), biomes.getOrThrow(HolyIslesBiomes.VERDANT_WOODS)),

                // Magnetic
                Pair.of(new Climate.ParameterPoint(Climate.Parameter.span(-1.5F, 0.6F), continentsIsland, fullRange, erosionMagnetic, depthDefault, ridgeLake, 0), biomes.getOrThrow(HolyIslesBiomes.GLISTENING_SWAMP)),
                Pair.of(new Climate.ParameterPoint(Climate.Parameter.span(-1.5F, 0.6F), continentsIsland, fullRange, erosionMagnetic, depthHestveilCaverns, Climate.Parameter.span(0.2F, 0.3F), 0), biomes.getOrThrow(HolyIslesBiomes.GLISTENING_SWAMP)),

                Pair.of(new Climate.ParameterPoint(tempMagnetic1, Climate.Parameter.span(-1.0F, -0.1F), continentsIsland, erosionMagnetic, depthDefault, ridgeLand, 0), biomes.getOrThrow(HolyIslesBiomes.VIOLET_HIGHWOODS)),
                Pair.of(new Climate.ParameterPoint(tempMagnetic1, Climate.Parameter.span(-0.1F, 1.0F), continentsIsland, erosionMagnetic, depthDefault, ridgeLand, 0), biomes.getOrThrow(HolyIslesBiomes.MAGNETIC_SCAR)),

                Pair.of(new Climate.ParameterPoint(tempMagnetic2, Climate.Parameter.span(-1.0F, -0.15F), continentsIsland, erosionMagnetic, depthDefault, ridgeLand, 0), biomes.getOrThrow(HolyIslesBiomes.TURQUOISE_FOREST)),
                Pair.of(new Climate.ParameterPoint(tempMagnetic2, Climate.Parameter.span(-0.15F, 0.2F), continentsIsland, erosionMagnetic, depthDefault, ridgeLand, 0), biomes.getOrThrow(HolyIslesBiomes.MAGNETIC_SCAR)),
                Pair.of(new Climate.ParameterPoint(tempMagnetic2, Climate.Parameter.span(0.2F, 1.0F), continentsIsland, erosionMagnetic, depthDefault, ridgeLand, 0), biomes.getOrThrow(HolyIslesBiomes.VIOLET_HIGHWOODS)),

                Pair.of(new Climate.ParameterPoint(tempMagnetic3, Climate.Parameter.span(-1.0F, -0.1F), continentsIsland, erosionMagnetic, depthDefault, ridgeLand, 0), biomes.getOrThrow(HolyIslesBiomes.MAGNETIC_SCAR)),
                Pair.of(new Climate.ParameterPoint(tempMagnetic3, Climate.Parameter.span(-0.1F, 0.3F), continentsIsland, erosionMagnetic, depthDefault, ridgeLand, 0), biomes.getOrThrow(HolyIslesBiomes.TURQUOISE_FOREST)),
                Pair.of(new Climate.ParameterPoint(tempMagnetic3, Climate.Parameter.span(0.3F, 1.0F), continentsIsland, erosionMagnetic, depthDefault, ridgeLand, 0), biomes.getOrThrow(HolyIslesBiomes.MAGNETIC_SCAR)),

                Pair.of(new Climate.ParameterPoint(tempIrradiated, fullRange, continentsIsland, erosionMagnetic, depthDefault, fullRange, 0), biomes.getOrThrow(HolyIslesBiomes.MAGNETIC_SCAR)),

                // Irradiated
                Pair.of(new Climate.ParameterPoint(tempIrradiated, Climate.Parameter.span(-1.0F, -0.15F), continentsIsland, erosionDefault, depthDefault, fullRange, 0), biomes.getOrThrow(HolyIslesBiomes.BATTLEGROUND_WASTES)),
                Pair.of(new Climate.ParameterPoint(tempIrradiated, Climate.Parameter.span(-0.15F, 0.2F), continentsIsland, erosionDefault, depthDefault, fullRange, 0), biomes.getOrThrow(HolyIslesBiomes.CONTAMINATED_JUNGLE)),
                Pair.of(new Climate.ParameterPoint(tempIrradiated, Climate.Parameter.span(0.2F, 1.0F), continentsIsland, erosionDefault, depthDefault, fullRange, 0), biomes.getOrThrow(HolyIslesBiomes.BATTLEGROUND_WASTES)),

                // Aercloud Sea
                Pair.of(new Climate.ParameterPoint(fullRange, fullRange, continentsAercloudSea, fullRange, fullRange, fullRange, 0), biomes.getOrThrow(HolyIslesBiomes.AERCLOUD_SEA)),
                Pair.of(new Climate.ParameterPoint(tempHighfields, fullRange, continentsExpanse, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(HolyIslesBiomes.HIGHFIELDS_EXPANSE)),
                Pair.of(new Climate.ParameterPoint(fullRange, fullRange, continentsExpanse, erosionMagnetic, fullRange, fullRange, 0), biomes.getOrThrow(HolyIslesBiomes.MAGNETIC_EXPANSE)),
                Pair.of(new Climate.ParameterPoint(tempArctic, fullRange, continentsExpanse, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(HolyIslesBiomes.ARCTIC_EXPANSE)),
                Pair.of(new Climate.ParameterPoint(tempIrradiated, fullRange, continentsExpanse, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(HolyIslesBiomes.IRRADIATED_EXPANSE)),

                //Caves
                Pair.of(new Climate.ParameterPoint(tempHighfields, Climate.Parameter.span(-1.5F, 2.0F), continentsIsland, erosionDefault, depthCaves, fullRange, 0), biomes.getOrThrow(HolyIslesBiomes.HIGHFIELDS_UNDERCLOUD)),
                Pair.of(new Climate.ParameterPoint(fullRange, Climate.Parameter.span(-1.5F, 2.0F), continentsIsland, erosionMagnetic, depthCaves, fullRange, 0), biomes.getOrThrow(HolyIslesBiomes.MAGNETIC_UNDERCLOUD)),
                Pair.of(new Climate.ParameterPoint(tempArctic, Climate.Parameter.span(-1.5F, 2.0F), continentsIsland, erosionDefault, depthCaves, fullRange, 0), biomes.getOrThrow(HolyIslesBiomes.ARCTIC_UNDERCLOUD)),
                Pair.of(new Climate.ParameterPoint(tempIrradiated, Climate.Parameter.span(-1.5F, 2.0F), continentsIsland, erosionDefault, depthCaves, fullRange, 0), biomes.getOrThrow(HolyIslesBiomes.IRRADIATED_UNDERCLOUD)),

                Pair.of(new Climate.ParameterPoint(fullRange, Climate.Parameter.span(-1.5F, 2.0F), continentsIsland, fullRange, depthHestveilCaverns, Climate.Parameter.span(-1.0F, 0.2F), 0), biomes.getOrThrow(HolyIslesBiomes.HESTVEIL_CAVERNS))
        )));
    }
}