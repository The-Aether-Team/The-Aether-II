package com.aetherteam.aetherii.data.resources.builders.highlands;

import com.aetherteam.aetherii.data.resources.registries.AetherIIBiomes;
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
        Climate.Parameter tempHighfields3 = Climate.Parameter.span(0.3F, 0.65F);
        Climate.Parameter tempMagnetic1 = Climate.Parameter.span(-1.5F, -0.25F);
        Climate.Parameter tempMagnetic2 = Climate.Parameter.span(-0.25F, 0.25F);
        Climate.Parameter tempMagnetic3 = Climate.Parameter.span(0.25F, 0.65F);
        Climate.Parameter tempIrradiated = Climate.Parameter.span(0.65F, 1.5F);

        Climate.Parameter continentExpanse = Climate.Parameter.span(-0.125F, 0.125F);
        Climate.Parameter continentA = Climate.Parameter.span(-1.0F, -0.125F);
        Climate.Parameter continentB = Climate.Parameter.span(0.125F, 1.0F);

        Climate.Parameter erosionDefault = Climate.Parameter.span(0.0F, 0.5F);
        Climate.Parameter erosionMagnetic = Climate.Parameter.span(0.5F, 1.5F);

        Climate.Parameter depthArcticLayer1 =Climate.Parameter.span(-1.5F, -0.15F);
        Climate.Parameter depthArcticLayer2 = Climate.Parameter.span(-0.15F, 0.15F);
        Climate.Parameter depthArcticLayer3 = Climate.Parameter.span(0.15F, 1.5F);

        return MultiNoiseBiomeSource.createFromList(new Climate.ParameterList<>(List.of(

                // Arctic
                Pair.of(new Climate.ParameterPoint(tempArctic, Climate.Parameter.span(-1.0F, -0.2F), fullRange, erosionDefault, depthArcticLayer1, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.ENDURING_WOODLAND)),
                Pair.of(new Climate.ParameterPoint(tempArctic, Climate.Parameter.span(-0.2F, 0.15F), fullRange, erosionDefault, depthArcticLayer1, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.SHEER_TUNDRA)),
                Pair.of(new Climate.ParameterPoint(tempArctic, Climate.Parameter.span(0.15F, 1.0F), fullRange, erosionDefault, depthArcticLayer1, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.ENDURING_WOODLAND)),

                Pair.of(new Climate.ParameterPoint(tempArctic, Climate.Parameter.span(-1.0F, -0.25F), fullRange, erosionDefault, depthArcticLayer2, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.ENDURING_WOODLAND)),
                Pair.of(new Climate.ParameterPoint(tempArctic, Climate.Parameter.span(-0.25F, 0.1F), fullRange, erosionDefault, depthArcticLayer2, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.SHEER_TUNDRA)),
                Pair.of(new Climate.ParameterPoint(tempArctic, Climate.Parameter.span(0.1F, 1.0F), fullRange, erosionDefault, depthArcticLayer2, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.FRIGID_SIERRA)),

                Pair.of(new Climate.ParameterPoint(tempArctic, fullRange, fullRange, erosionDefault, depthArcticLayer3, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.FRIGID_SIERRA)),

                // Highfields
                Pair.of(new Climate.ParameterPoint(tempHighfields, fullRange, continentExpanse, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.EXPANSE)),

                Pair.of(new Climate.ParameterPoint(tempHighfields1, Climate.Parameter.span(-1.0F, -0.15F), continentA, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.SHROUDED_FOREST)),
                Pair.of(new Climate.ParameterPoint(tempHighfields1, Climate.Parameter.span(-0.15F, 0.15F), continentA, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.VERDANT_WOODS)),
                Pair.of(new Climate.ParameterPoint(tempHighfields1, Climate.Parameter.span(0.15F, 1.0F), continentA, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.FLOURISHING_FIELD)),

                Pair.of(new Climate.ParameterPoint(tempHighfields2, Climate.Parameter.span(-1.0F, -0.1F), continentA, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.FLOURISHING_FIELD)),
                Pair.of(new Climate.ParameterPoint(tempHighfields2, Climate.Parameter.span(-0.1F, 0.2F), continentA, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.VERDANT_WOODS)),
                Pair.of(new Climate.ParameterPoint(tempHighfields2, Climate.Parameter.span(0.2F, 1.0F), continentA, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.FLOURISHING_FIELD)),

                Pair.of(new Climate.ParameterPoint(tempHighfields3, Climate.Parameter.span(-1.0F, -0.25F), continentA, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.FLOURISHING_FIELD)),
                Pair.of(new Climate.ParameterPoint(tempHighfields3, Climate.Parameter.span(-0.25F, 0.1F), continentA, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.VERDANT_WOODS)),
                Pair.of(new Climate.ParameterPoint(tempHighfields3, Climate.Parameter.span(0.1F, 0.25F), continentA, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.FLOURISHING_FIELD)),
                Pair.of(new Climate.ParameterPoint(tempHighfields3, Climate.Parameter.span(0.25F, 1.0F), continentA, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.VERDANT_WOODS)),


                Pair.of(new Climate.ParameterPoint(tempHighfields1, Climate.Parameter.span(-1.0F, 0.05F), continentB, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.VERDANT_WOODS)),
                Pair.of(new Climate.ParameterPoint(tempHighfields1, Climate.Parameter.span(0.05F, 0.2F), continentB, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.FLOURISHING_FIELD)),
                Pair.of(new Climate.ParameterPoint(tempHighfields1, Climate.Parameter.span(0.2F, 1.0F), continentB, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.VERDANT_WOODS)),

                Pair.of(new Climate.ParameterPoint(tempHighfields2, Climate.Parameter.span(-1.0F, -0.15F), continentB, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.FLOURISHING_FIELD)),
                Pair.of(new Climate.ParameterPoint(tempHighfields2, Climate.Parameter.span(-0.15F, 0.0F), continentB, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.SHROUDED_FOREST)),
                Pair.of(new Climate.ParameterPoint(tempHighfields2, Climate.Parameter.span(0.0F, 0.2F), continentB, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.FLOURISHING_FIELD)),
                Pair.of(new Climate.ParameterPoint(tempHighfields2, Climate.Parameter.span(0.2F, 1.0F), continentB, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.VERDANT_WOODS)),

                Pair.of(new Climate.ParameterPoint(tempHighfields3, Climate.Parameter.span(-1.0F, -0.2F), continentB, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.FLOURISHING_FIELD)),
                Pair.of(new Climate.ParameterPoint(tempHighfields3, Climate.Parameter.span(-0.2F, 0.05F), continentB, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.VERDANT_WOODS)),
                Pair.of(new Climate.ParameterPoint(tempHighfields3, Climate.Parameter.span(0.05F, 0.2F), continentB, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.FLOURISHING_FIELD)),
                Pair.of(new Climate.ParameterPoint(tempHighfields3, Climate.Parameter.span(0.2F, 1.0F), continentB, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.VERDANT_WOODS)),

                // Magnetic
                Pair.of(new Climate.ParameterPoint(tempMagnetic1, Climate.Parameter.span(-1.0F, -0.2F), fullRange, erosionMagnetic, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.VIOLET_HIGHWOODS)),
                Pair.of(new Climate.ParameterPoint(tempMagnetic1, Climate.Parameter.span(-0.2F, 0.15F), fullRange, erosionMagnetic, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.GLISTENING_SWAMP)),
                Pair.of(new Climate.ParameterPoint(tempMagnetic1, Climate.Parameter.span(0.15F, 1.0F), fullRange, erosionMagnetic, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.MAGNETIC_SCAR)),

                Pair.of(new Climate.ParameterPoint(tempMagnetic2, Climate.Parameter.span(-1.0F, -0.15F), fullRange, erosionMagnetic, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.TURQUOISE_FOREST)),
                Pair.of(new Climate.ParameterPoint(tempMagnetic2, Climate.Parameter.span(-0.15F, 0.2F), fullRange, erosionMagnetic, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.MAGNETIC_SCAR)),
                Pair.of(new Climate.ParameterPoint(tempMagnetic2, Climate.Parameter.span(0.2F, 1.0F), fullRange, erosionMagnetic, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.VIOLET_HIGHWOODS)),

                Pair.of(new Climate.ParameterPoint(tempMagnetic3, Climate.Parameter.span(-1.0F, -0.2F), fullRange, erosionMagnetic, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.GLISTENING_SWAMP)),
                Pair.of(new Climate.ParameterPoint(tempMagnetic3, Climate.Parameter.span(-0.2F, 0.1F), fullRange, erosionMagnetic, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.MAGNETIC_SCAR)),
                Pair.of(new Climate.ParameterPoint(tempMagnetic3, Climate.Parameter.span(0.1F, 0.3F), fullRange, erosionMagnetic, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.TURQUOISE_FOREST)),
                Pair.of(new Climate.ParameterPoint(tempMagnetic3, Climate.Parameter.span(0.3F, 1.0F), fullRange, erosionMagnetic, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.MAGNETIC_SCAR)),

                Pair.of(new Climate.ParameterPoint(tempIrradiated, fullRange, fullRange, erosionMagnetic, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.MAGNETIC_SCAR)),

                // Irradiated
                Pair.of(new Climate.ParameterPoint(tempIrradiated, Climate.Parameter.span(-1.0F, -0.15F), fullRange, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.BATTLEGROUND_WASTES)),
                Pair.of(new Climate.ParameterPoint(tempIrradiated, Climate.Parameter.span(-0.15F, 0.2F), fullRange, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.CONTAMINATED_JUNGLE)),
                Pair.of(new Climate.ParameterPoint(tempIrradiated, Climate.Parameter.span(0.2F, 1.0F), fullRange, erosionDefault, fullRange, fullRange, 0), biomes.getOrThrow(AetherIIBiomes.BATTLEGROUND_WASTES))
        )));
    }
}