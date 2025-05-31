package com.aetherteam.aetherii.entity.ai.brain.behavior.taegore;

import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.ai.brain.TaegoreAi;
import com.aetherteam.aetherii.entity.ai.brain.memory.AetherIIMemoryModuleTypes;
import com.aetherteam.aetherii.entity.passive.Taegore;
import com.aetherteam.aetherii.loot.AetherIILoot;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;

import java.util.Map;

public class TaegoreDigging extends Behavior<Taegore> {
    public TaegoreDigging(int minDuration, int maxDuration) {
        super(Map.of(
                MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT,
                MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                AetherIIMemoryModuleTypes.TAEGORE_DIGGING.get(), MemoryStatus.VALUE_PRESENT,
                AetherIIMemoryModuleTypes.TAEGORE_SEARCH_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT
        ),minDuration,maxDuration);
    }

    protected boolean checkExtraStartConditions(ServerLevel serverLevel, Taegore owner) {
        return owner.canSearch();
    }

    protected boolean canStillUse(ServerLevel serverLevel, Taegore owner, long gameTime) {
        return owner.getBrain().getMemory(AetherIIMemoryModuleTypes.TAEGORE_DIGGING.get()).isPresent() && owner.canDig() && !owner.isInLove();
    }

    @Override
    protected void start(ServerLevel serverLevel, Taegore owner, long gameTime) {
        serverLevel.broadcastEntityEvent(owner, (byte) Taegore.DIGGING_START_EVENT);
    }

    protected void stop(ServerLevel serverLevel, Taegore owner, long gameTime) {
        boolean finished = this.timedOut(gameTime);
        if (finished) {
            owner.getBrain().setMemoryWithExpiry(AetherIIMemoryModuleTypes.TAEGORE_SEARCH_COOLDOWN.get(), Unit.INSTANCE, 9600L);
            this.dropSeed(owner);
        } else {
            TaegoreAi.resetSearch(serverLevel, owner);
        }
    }

    @Override
    protected void tick(ServerLevel serverLevel, Taegore owner, long gameTime) {
        serverLevel.broadcastEntityEvent(owner, (byte) Taegore.DIGGING_TICK_EVENT);
    }

    private void dropSeed(Taegore owner) {
        Level level = owner.level();
        if (level instanceof ServerLevel serverlevel) {
            BlockPos blockPos = owner.getHeadBlock();
            owner.dropFromGiftLootTable(serverlevel, AetherIILoot.TAEGORE_DIGGING, (tableLevel, stack) -> {
                ItemEntity itemEntity = new ItemEntity(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), stack);
                itemEntity.setDefaultPickUpDelay();
                tableLevel.addFreshEntity(itemEntity);
            });
            owner.playSound(AetherIISoundEvents.ENTITY_TAEGORE_DROP_SEED.get(), 1.0F, 1.0F);
        }
    }
}
