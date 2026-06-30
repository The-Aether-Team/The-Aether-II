package com.aetherteam.aetherii.entity.ai.brain.behavior.taegore;

import com.aetherteam.aetherii.entity.ai.brain.memory.AetherIIMemoryModuleTypes;
import com.aetherteam.aetherii.entity.passive.Taegore;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;

import java.util.Map;
import java.util.Optional;

public class TaegoreSearching extends Behavior<Taegore> {
    public TaegoreSearching() {
        super(Map.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_PRESENT,
                MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT,
                AetherIIMemoryModuleTypes.TAEGORE_SEARCH_TARGET.get(), MemoryStatus.VALUE_PRESENT
        ), 600);
    }

    protected boolean checkExtraStartConditions(ServerLevel serverLevel, Taegore owner) {
        return owner.canSearch();
    }

    protected boolean canStillUse(ServerLevel serverLevel, Taegore owner, long gameTime) {
        if (!owner.canSearch()) {
            return false;
        } else {
            Optional<BlockPos> hasWalkTarget = owner.getBrain().getMemory(MemoryModuleType.WALK_TARGET).map(WalkTarget::getTarget).map(PositionTracker::currentBlockPosition);
            Optional<BlockPos> hasSearchTarget = owner.getBrain().getMemory(AetherIIMemoryModuleTypes.TAEGORE_SEARCH_TARGET.get());
            return hasWalkTarget.isPresent() && hasSearchTarget.isPresent() && hasSearchTarget.get().equals(hasWalkTarget.get());
        }
    }

    @Override
    protected void tick(ServerLevel level, Taegore owner, long gameTime) {
        super.tick(level, owner, gameTime);
        level.broadcastEntityEvent(owner, (byte) Taegore.SEARCHING_EVENT);
    }

    protected void stop(ServerLevel serverLevel, Taegore owner, long gameTime) {
        if (owner.canDig() && owner.canSearch()) {
            owner.getBrain().setMemory(AetherIIMemoryModuleTypes.TAEGORE_DIGGING.get(), true);
        }
        owner.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
        owner.getBrain().eraseMemory(AetherIIMemoryModuleTypes.TAEGORE_SEARCH_TARGET.get());
    }
}
