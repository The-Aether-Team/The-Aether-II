package com.aetherteam.aetherii.entity.monster;

import com.aetherteam.aetherii.AetherIITags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;

public abstract class Taloton extends Monster {
    protected Taloton(EntityType<? extends Taloton> entityType, Level level) {
        super(entityType, level);
    }

    public static boolean checkTalotonSpawnRules(EntityType<? extends Monster> taloton, LevelAccessor level, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS)
                && level.getDifficulty() != Difficulty.PEACEFUL
                && isDarkEnoughToSpawn((ServerLevelAccessor) level, pos, random)
                && checkMobSpawnRules(taloton, level, reason, pos, random);
    }
}
