package com.aetherteam.aetherii.entity.ai.brain.behavior;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.entity.ai.brain.KirridAi;
import com.aetherteam.aetherii.entity.ai.memory.AetherIIMemoryModuleTypes;
import com.aetherteam.aetherii.entity.passive.Kirrid;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.Block;

public class KirridEatGrass extends Behavior<Kirrid> {

    private int eatTick;

    public KirridEatGrass() {
        super(ImmutableMap.of(AetherIIMemoryModuleTypes.EAT_GRASS_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT), 60);

    }

    protected boolean checkExtraStartConditions(ServerLevel pLevel, Kirrid pOwner) {
        return this.isValidTarget(pLevel, pOwner);
    }

    private boolean isValidTarget(ServerLevel pLevel, Kirrid pOwner) {
        return pLevel.getBlockState(pOwner.blockPosition().below()).is(AetherIIBlocks.AETHER_GRASS_BLOCK.get());
    }

    protected boolean canStillUse(ServerLevel pLevel, Kirrid pEntity, long pGameTime) {
        return this.eatTick < 60;
    }

    protected void start(ServerLevel pLevel, Kirrid pEntity, long pGameTime) {

        pLevel.broadcastEntityEvent(pEntity, (byte) 64);
    }

    protected void tick(ServerLevel pLevel, Kirrid pOwner, long pGameTime) {
        Brain<?> brain = pOwner.getBrain();

        this.eatTick++;
    }


    protected void finishEat(ServerLevel pLevel, Kirrid pOwner) {
        pLevel.levelEvent(2001, pOwner.blockPosition().below(), Block.getId(AetherIIBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState()));
        pLevel.setBlock(pOwner.blockPosition().below(), AetherIIBlocks.AETHER_DIRT.get().defaultBlockState(), 3);
        pOwner.getBrain().setMemory(AetherIIMemoryModuleTypes.EAT_GRASS_COOLDOWN.get(), KirridAi.TIME_BETWEEN_EAT.sample(pLevel.random));
        pOwner.ate();
    }

    @Override
    protected void stop(ServerLevel pLevel, Kirrid pEntity, long pGameTime) {
        super.stop(pLevel, pEntity, pGameTime);
        if (this.isValidTarget(pLevel, pEntity)) {
            this.finishEat(pLevel, pEntity);
        }
    }
}
