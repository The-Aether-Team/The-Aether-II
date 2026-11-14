package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.AetherII;
import com.mojang.serialization.Codec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class AnimalDenFeature extends Feature<NoneFeatureConfiguration> {
    public AnimalDenFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        int index = random.nextInt(3);
        Rotation rotation = Rotation.getRandom(random);
        Identifier identifier = Identifier.fromNamespaceAndPath(AetherII.MODID, "animal_den/den_0" + index);

        StructureTemplate den = level.getLevel().getServer().getStructureManager().getOrCreate(identifier);
        StructurePlaceSettings settings = new StructurePlaceSettings().setRotation(rotation).setRandom(random);
        den.placeInWorld(level, context.origin().below(2), context.origin().below(2), settings, random, 18);
        return true;
    }
}