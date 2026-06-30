package com.aetherteam.aetherii.entity.ai.brain.behavior.taegore;

import com.aetherteam.aetherii.entity.ai.brain.memory.AetherIIMemoryModuleTypes;
import com.aetherteam.aetherii.entity.passive.Taegore;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TaegoreFinishedDigging extends Behavior<Taegore> {
    public TaegoreFinishedDigging(int duration) {
        super(Map.of(
                MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT,
                MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                AetherIIMemoryModuleTypes.TAEGORE_DIGGING.get(), MemoryStatus.VALUE_PRESENT,
                AetherIIMemoryModuleTypes.TAEGORE_SEARCH_COOLDOWN.get(), MemoryStatus.VALUE_PRESENT
        ), duration, duration);
    }

    protected boolean checkExtraStartConditions(ServerLevel serverLevel, Taegore owner) {
        return true;
    }

    protected boolean canStillUse(ServerLevel serverLevel, Taegore owner, long gameTime) {
        return owner.getBrain().getMemory(AetherIIMemoryModuleTypes.TAEGORE_DIGGING.get()).isPresent();
    }

    @Override
    protected void start(ServerLevel serverLevel, Taegore owner, long gameTime) {
        serverLevel.broadcastEntityEvent(owner, (byte) Taegore.DIGGING_STOP_EVENT);
    }

    protected void stop(ServerLevel serverLevel, Taegore owner, long gameTime) {
        boolean flag = this.timedOut(gameTime);
        this.onDiggingComplete(owner, flag);
        owner.getBrain().eraseMemory(AetherIIMemoryModuleTypes.TAEGORE_DIGGING.get());
    }

    public void onDiggingComplete(Taegore owner, boolean storeExploredPosition) {
        if (storeExploredPosition) {
            this.storeExploredPosition(owner, owner.getOnPos());
        }
    }

    private void storeExploredPosition(Taegore owner, BlockPos pos) {
        List<GlobalPos> list = owner.getExploredPositions().limit(20L).collect(Collectors.toList());
        list.add(0, GlobalPos.of(owner.level().dimension(), pos));
        owner.getBrain().setMemory(AetherIIMemoryModuleTypes.TAEGORE_EXPLORED_POSITIONS.get(), list);
    }
}
