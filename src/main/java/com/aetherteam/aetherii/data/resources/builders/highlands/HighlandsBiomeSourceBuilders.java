package com.aetherteam.aetherii.data.resources.builders.highlands;

import com.aetherteam.aetherii.data.resources.registries.highlands.HighlandsBiomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;

import java.util.List;

public class HighlandsBiomeSourceBuilders {
    public static BiomeSource buildHighlandsBiomeSource(HolderGetter<Biome> biomes) {
        Climate.Parameter fullRange = Climate.Parameter.span(-1.5F, 1.5F);

        Climate.Parameter tempArctic = Climate.Parameter.span(-1.5F, -0.4F);
        Climate.Parameter tempHighfields = Climate.Parameter.span(-0.4F, 0.65F);
        Climate.Parameter tempHighfields1 = Climate.Parameter.span(-0.4F, -0.1F);
        Climate.Parameter tempHighfields2 = Climate.Parameter.span(-0.1F, 0.3F);
        Climate.Parameter tempHighfields3 = Climate.Parameter.span(0.3F, 0.6F);
        Climate.Parameter tempMagnetic1 = Climate.Parameter.span(-1.5F, -0.25F);
        Climate.Parameter tempMagnetic2 = Climate.Parameter.span(-0.25F, 0.25F);
        Climate.Parameter tempMagnetic3 = Climate.Parameter.span(0.25F, 0.6F);
        Climate.Parameter tempIrradiated = Climate.Parameter.span(0.6F, 1.5F);

        Climate.Parameter continentLand = Climate.Parameter.span(0.0F, 0.2F);
        Climate.Parameter continentExpanse = Climate.Parameter.span(0.2F, 1.0F);

        Climate.Parameter erosionDefault = Climate.Parameter.span(0.0F, 0.55F);
        Climate.Parameter erosionMagnetic = Climate.Parameter.span(0.55F, 1.5F);

        Climate.Parameter depthArcticLayer1 = Climate.Parameter.span(-1.5F, -0.15F);
        Climate.Parameter depthArcticLayer2 = Climate.Parameter.span(-0.15F, 0.15F);
        Climate.Parameter depthArcticLayer3 = Climate.Parameter.span(0.15F, 1.5F);

        Climate.Parameter ridgeLand = Climate.Parameter.span(-1.0F, 0.3F);
        Climate.Parameter ridgeLake = Climate.Parameter.span(0.3F, 1.0F);

        return MultiNoiseBiomeSource.createFromList(new Climate.ParameterList<>(List.of(

                // Arctic
                Pair.of(new Climate.ParameterPoint(tempArctic, fullRange, fullRange, erosionDefault, fullRange, ridgeLake, 0), biomes.getOrThrow(HighlandsBiomes.FROZEN_LAKES)),

                Pair.of(new Climate.ParameterPoint(tempArctic, Climate.Parameter.span(-1.0F, -0.3F), fullRange, erosionDefault, depthArcticLayer1, ridgeLand, 0), biomes.getOrThrow(HighlandsBiomes.ENDURING_WOODLAND)),
                Pair.of(new Climate.ParameterPoint(tempArctic, Climate.Parameter.span(-0.3F, 0.2F), fullRange, erosionDefault, depthArcticLayer1, ridgeLand, 0), biomes.getOrThrow(HighlandsBiomes.SHEER_TUNDRA)),
                Pair.of(new Climate.ParameterPoint(tempArctic, Climate.Parameter.span(0.2F, 1.0F), fullRange, erosionDefault, depthArcticLayer1, ridgeLand, 0), biomes.getOrThrow(HighlandsBiomes.ENDURING_WOODLAND)),

                Pair.of(new Climate.ParameterPoint(tempArctic, Climate.Parameter.span(-1.0F, -0.1F), fullRange, erosionDefault, depthArcticLayer2, ridgeLand, 0), biomes.getOrThrow(HighlandsBiomes.FRIGID_SIERRA)),
                Pair.of(new Climate.ParameterPoint(tempArctic, Climate.Parameter.span(-0.1F, 0.25F), fullRange, erosionDefault, depthArcticLayer2, ridgeLand, 0), biomes.getOrThrow(HighlandsBiomes.SHEER_TUNDRA)),
                Pair.of(new Climate.ParameterPoint(tempArctic, Climate.Parameter.span(0.25F, 1.0F), fullRange, erosionDefault, depthArcticLayer2, ridgeLand, 0), biomes.getOrThrow(HighlandsBiomes.ENDURING_WOODLAND)),

                Pair.of(new Climate.ParameterPoint(tempArctic, fullRange, fullRange, erosionDefault, depthArcticLayer3, ridgeLand, 0), biomes.getOrThrow(HighlandsBiomes.FRIGID_SIERRA)),

                // Highfields
                Pair.of(new Climate.ParameterPoint(tempHighfields, fullRange, continentExpanse, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(HighlandsBiomes.EXPANSE)),

                Pair.of(new Climate.ParameterPoint(tempHighfields, fullRange, continentLand, erosionDefault, fullRange, ridgeLake, 0), biomes.getOrThrow(HighlandsBiomes.SHIMMERING_BASIN)),

                Pair.of(new Climate.ParameterPoint(tempHighfields1, Climate.Parameter.span(-1.0F, -0.1F), continentLand, erosionDefault, fullRange, ridgeLand, 0), biomes.getOrThrow(HighlandsBiomes.FLOURISHING_FIELD)),
                Pair.of(new Climate.ParameterPoint(tempHighfields1, Climate.Parameter.span(-0.1F, 0.2F), continentLand, erosionDefault, fullRange, ridgeLand, 0), biomes.getOrThrow(HighlandsBiomes.VERDANT_WOODS)),
                Pair.of(new Climate.ParameterPoint(tempHighfields1, Climate.Parameter.span(0.2F, 1.0F), continentLand, erosionDefault, fullRange, ridgeLand, 0), biomes.getOrThrow(HighlandsBiomes.FLOURISHING_FIELD)),
                Pair.of(new Climate.ParameterPoint(tempHighfields1, Climate.Parameter.span(1.0F, 2.0F), continentLand, erosionDefault, fullRange, ridgeLand, 0), biomes.getOrThrow(HighlandsBiomes.SHROUDED_FOREST)),

                Pair.of(new Climate.ParameterPoint(tempHighfields2, Climate.Parameter.span(-1.0F, -0.15F), continentLand, erosionDefault, fullRange, ridgeLand, 0), biomes.getOrThrow(HighlandsBiomes.VERDANT_WOODS)),
                Pair.of(new Climate.ParameterPoint(tempHighfields2, Climate.Parameter.span(-0.15F, 1.0F), continentLand, erosionDefault, fullRange, ridgeLand, 0), biomes.getOrThrow(HighlandsBiomes.FLOURISHING_FIELD)),
                Pair.of(new Climate.ParameterPoint(tempHighfields2, Climate.Parameter.span(1.0F, 2.0F), continentLand, erosionDefault, fullRange, ridgeLand, 0), biomes.getOrThrow(HighlandsBiomes.SHROUDED_FOREST)),

                Pair.of(new Climate.ParameterPoint(tempHighfields3, Climate.Parameter.span(-1.0F, -0.25F), continentLand, erosionDefault, fullRange, ridgeLand, 0), biomes.getOrThrow(HighlandsBiomes.FLOURISHING_FIELD)),
                Pair.of(new Climate.ParameterPoint(tempHighfields3, Climate.Parameter.span(-0.25F, 0.1F), continentLand, erosionDefault, fullRange, ridgeLand, 0), biomes.getOrThrow(HighlandsBiomes.VERDANT_WOODS)),
                Pair.of(new Climate.ParameterPoint(tempHighfields3, Climate.Parameter.span(0.1F, 0.25F), continentLand, erosionDefault, fullRange, ridgeLand, 0), biomes.getOrThrow(HighlandsBiomes.FLOURISHING_FIELD)),
                Pair.of(new Climate.ParameterPoint(tempHighfields3, Climate.Parameter.span(0.25F, 1.0F), continentLand, erosionDefault, fullRange, ridgeLand, 0), biomes.getOrThrow(HighlandsBiomes.VERDANT_WOODS)),

                // Magnetic
                Pair.of(new Climate.ParameterPoint(Climate.Parameter.span(-1.5F, 0.6F), fullRange, fullRange, erosionMagnetic, fullRange, ridgeLake, 0), biomes.getOrThrow(HighlandsBiomes.GLISTENING_SWAMP)),

                Pair.of(new Climate.ParameterPoint(tempMagnetic1, Climate.Parameter.span(-1.0F, -0.1F), fullRange, erosionMagnetic, fullRange, ridgeLand, 0), biomes.getOrThrow(HighlandsBiomes.VIOLET_HIGHWOODS)),
                Pair.of(new Climate.ParameterPoint(tempMagnetic1, Climate.Parameter.span(-0.1F, 1.0F), fullRange, erosionMagnetic, fullRange, ridgeLand, 0), biomes.getOrThrow(HighlandsBiomes.MAGNETIC_SCAR)),

                Pair.of(new Climate.ParameterPoint(tempMagnetic2, Climate.Parameter.span(-1.0F, -0.15F), fullRange, erosionMagnetic, fullRange, ridgeLand, 0), biomes.getOrThrow(HighlandsBiomes.TURQUOISE_FOREST)),
                Pair.of(new Climate.ParameterPoint(tempMagnetic2, Climate.Parameter.span(-0.15F, 0.2F), fullRange, erosionMagnetic, fullRange, ridgeLand, 0), biomes.getOrThrow(HighlandsBiomes.MAGNETIC_SCAR)),
                Pair.of(new Climate.ParameterPoint(tempMagnetic2, Climate.Parameter.span(0.2F, 1.0F), fullRange, erosionMagnetic, fullRange, ridgeLand, 0), biomes.getOrThrow(HighlandsBiomes.VIOLET_HIGHWOODS)),

                Pair.of(new Climate.ParameterPoint(tempMagnetic3, Climate.Parameter.span(-1.0F, -0.1F), fullRange, erosionMagnetic, fullRange, ridgeLand, 0), biomes.getOrThrow(HighlandsBiomes.MAGNETIC_SCAR)),
                Pair.of(new Climate.ParameterPoint(tempMagnetic3, Climate.Parameter.span(-0.1F, 0.3F), fullRange, erosionMagnetic, fullRange, ridgeLand, 0), biomes.getOrThrow(HighlandsBiomes.TURQUOISE_FOREST)),
                Pair.of(new Climate.ParameterPoint(tempMagnetic3, Climate.Parameter.span(0.3F, 1.0F), fullRange, erosionMagnetic, fullRange, ridgeLand, 0), biomes.getOrThrow(HighlandsBiomes.MAGNETIC_SCAR)),

                Pair.of(new Climate.ParameterPoint(tempIrradiated, fullRange, fullRange, erosionMagnetic, fullRange, fullRange, 0), biomes.getOrThrow(HighlandsBiomes.MAGNETIC_SCAR)),

                // Irradiated
                Pair.of(new Climate.ParameterPoint(tempIrradiated, Climate.Parameter.span(-1.0F, -0.15F), fullRange, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(HighlandsBiomes.BATTLEGROUND_WASTES)),
                Pair.of(new Climate.ParameterPoint(tempIrradiated, Climate.Parameter.span(-0.15F, 0.2F), fullRange, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(HighlandsBiomes.CONTAMINATED_JUNGLE)),
                Pair.of(new Climate.ParameterPoint(tempIrradiated, Climate.Parameter.span(0.2F, 1.0F), fullRange, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(HighlandsBiomes.BATTLEGROUND_WASTES))
        )));
    }
}