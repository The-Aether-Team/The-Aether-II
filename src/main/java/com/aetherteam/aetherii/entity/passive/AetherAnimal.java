package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;

/**
 * Overrides ground-related checks for animals to be specific to the Aether.
 */
public abstract class AetherAnimal extends Animal {
    protected AetherAnimal(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    /**
     * [CODE COPY] - {@link Animal#checkAnimalSpawnRules(EntityType, LevelAccessor, EntitySpawnReason, BlockPos, RandomSource)}.
     */
    public static boolean checkAetherAnimalSpawnRules(EntityType<? extends AetherAnimal> animal, LevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(AetherIITags.Blocks.AETHER_ANIMALS_SPAWNABLE_ON)
                && level.getRawBrightness(pos, 0) > 8;
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return level.getBlockState(pos.below()).is(AetherIIBlocks.AETHER_GRASS_BLOCK.get()) ? 10.0F : level.getMaxLocalRawBrightness(pos) - 0.5F;
    }
}
