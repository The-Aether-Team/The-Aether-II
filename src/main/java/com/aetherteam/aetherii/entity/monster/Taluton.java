package com.aetherteam.aetherii.entity.monster;

import com.aetherteam.aetherii.AetherIITags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;

public abstract class Taluton extends Monster {
    protected Taluton(EntityType<? extends Taluton> entityType, Level level) {
        super(entityType, level);
    }

    public static boolean checkTalutonSpawnRules(EntityType<? extends Monster> taluton, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS)
                && level.getDifficulty() != Difficulty.PEACEFUL
                && isDarkEnoughToSpawn((ServerLevelAccessor) level, pos, random)
                && checkMobSpawnRules(taluton, level, reason, pos, random);
    }
}
