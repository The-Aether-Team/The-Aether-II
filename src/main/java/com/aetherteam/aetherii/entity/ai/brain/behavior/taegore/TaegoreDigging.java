package com.aetherteam.aetherii.entity.ai.brain.behavior.taegore;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.ai.brain.TaegoreAi;
import com.aetherteam.aetherii.entity.ai.brain.memory.AetherIIMemoryModuleTypes;
import com.aetherteam.aetherii.entity.passive.Taegore;
import com.aetherteam.aetherii.loot.AetherIILoot;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootDataId;
import net.minecraft.world.level.storage.loot.LootDataType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import java.util.Map;

public class TaegoreDigging extends Behavior<Taegore> {
    private final int duration;
    private long endTimestamp;

    public TaegoreDigging(int duration) {
        super(Map.of(
                MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT,
                MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                AetherIIMemoryModuleTypes.TAEGORE_DIGGING.get(), MemoryStatus.VALUE_PRESENT,
                AetherIIMemoryModuleTypes.TAEGORE_SEARCH_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT
        ), duration, duration);
        this.duration = duration;
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
        this.endTimestamp = gameTime + this.duration;
    }

    protected void stop(ServerLevel serverLevel, Taegore owner, long gameTime) {
        boolean finished = this.timedOut(gameTime);
        if (finished) {
            owner.getBrain().setMemoryWithExpiry(AetherIIMemoryModuleTypes.TAEGORE_SEARCH_COOLDOWN.get(), Unit.INSTANCE, 9600L);
        } else {
            TaegoreAi.resetSearch(serverLevel, owner);
        }
    }

    @Override
    protected void tick(ServerLevel serverLevel, Taegore owner, long gameTime) {
        serverLevel.broadcastEntityEvent(owner, (byte) Taegore.DIGGING_TICK_EVENT);
        if (gameTime == this.endTimestamp - 25) {
            this.dropSeed(owner);
        }
    }

    private void dropSeed(Taegore owner) {
        Level level = owner.level();
        if (level instanceof ServerLevel serverlevel) {
            Vec3 pos = owner.getHeadPosition();
            LootTable lootTable = serverlevel.getServer().getLootData().getElement(new LootDataId<>(LootDataType.TABLE, AetherIILoot.TAEGORE_DIGGING.location()));
            if (lootTable == null) {
                AetherII.LOGGER.warn("Skipping Taegore digging drops because loot table {} is missing", AetherIILoot.TAEGORE_DIGGING.location());
                return;
            }
            LootParams params = new LootParams.Builder(serverlevel)
                    .withParameter(LootContextParams.ORIGIN, pos)
                    .withParameter(LootContextParams.THIS_ENTITY, owner)
                    .create(LootContextParamSets.GIFT);
            lootTable.getRandomItems(params, stack -> {
                ItemEntity itemEntity = new ItemEntity(level, pos.x(), pos.y(), pos.z(), stack);
                itemEntity.setDefaultPickUpDelay();
                serverlevel.addFreshEntity(itemEntity);
            });
            owner.playSound(AetherIISoundEvents.ENTITY_TAEGORE_DROP_SEED.get(), 1.0F, 1.0F);
        }
    }
}
