package com.aetherteam.aetherii.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class AetherMushroomBlock extends MushroomBlock {
    private final ResourceKey<ConfiguredFeature<?, ?>> feature;

    public AetherMushroomBlock(ResourceKey<ConfiguredFeature<?, ?>> feature, Properties properties) {
        super(feature, properties);
        this.feature = feature;
    }

    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        if (level instanceof ServerLevel serverLevel) {
            if (serverLevel.registryAccess().lookupOrThrow(Registries.CONFIGURED_FEATURE).get(this.feature).isPresent()) {
                int minHeight = 8;
                return level.isInsideBuildHeight(pos.above(minHeight));
            }
        }
        return false;
    }
}
