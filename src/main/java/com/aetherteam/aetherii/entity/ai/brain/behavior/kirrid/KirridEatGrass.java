package com.aetherteam.aetherii.entity.ai.brain.behavior.kirrid;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.entity.ai.brain.KirridAi;
import com.aetherteam.aetherii.entity.ai.brain.memory.AetherIIMemoryModuleTypes;
import com.aetherteam.aetherii.entity.passive.Kirrid;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.Block;

public class KirridEatGrass extends Behavior<Kirrid> {
    private int eatTick;

    public KirridEatGrass() {
        super(ImmutableMap.of(
                MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                AetherIIMemoryModuleTypes.EAT_GRASS_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT
        ), 60);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel serverLevel, Kirrid owner) {
        return this.isValidTarget(serverLevel, owner);
    }

    @Override
    protected boolean canStillUse(ServerLevel serverLevel, Kirrid owner, long gameTime) {
        return this.eatTick < 60;
    }

    @Override
    protected void start(ServerLevel serverLevel, Kirrid owner, long gameEntity) {
        this.eatTick = 0;
        serverLevel.broadcastEntityEvent(owner, (byte) Kirrid.EAT_START_EVENT);
    }

    @Override
    protected void tick(ServerLevel serverLevel, Kirrid owner, long gameTime) {
        if (this.eatTick == 45) {
            if (this.isValidTarget(serverLevel, owner)) {
                this.finishEat(serverLevel, owner);
            }
        }
        this.eatTick++;
    }

    private boolean isValidTarget(ServerLevel serverLevel, Kirrid owner) {
        return serverLevel.getBlockState(owner.blockPosition().below()).is(AetherIIBlocks.AETHER_GRASS_BLOCK.get());
    }

    protected void finishEat(ServerLevel serverLevel, Kirrid owner) {
        serverLevel.levelEvent(2001, owner.blockPosition().below(), Block.getId(AetherIIBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState()));
        serverLevel.setBlock(owner.blockPosition().below(), AetherIIBlocks.AETHER_DIRT.get().defaultBlockState(), 3);
        owner.getBrain().setMemory(AetherIIMemoryModuleTypes.EAT_GRASS_COOLDOWN.get(), KirridAi.TIME_BETWEEN_EAT.sample(serverLevel.getRandom()));
        owner.ate();
    }
}
