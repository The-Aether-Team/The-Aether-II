package com.aetherteam.aetherii.entity.ai.brain.behavior.taegore;

import com.aetherteam.aetherii.entity.ai.brain.memory.AetherIIMemoryModuleTypes;
import com.aetherteam.aetherii.entity.passive.Taegore;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

public class TaegoreBeginSearch extends Behavior<Taegore> {
    public TaegoreBeginSearch(int minDuration, int maxDuration) {
        super(Map.of(
                MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                AetherIIMemoryModuleTypes.TAEGORE_SEARCH_TARGET.get(), MemoryStatus.VALUE_ABSENT,
                AetherIIMemoryModuleTypes.TAEGORE_SEARCH_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT
        ), minDuration, maxDuration);
    }

    protected boolean checkExtraStartConditions(ServerLevel serverLevel, Taegore owner) {
        return !owner.isBaby() && owner.canSearch();
    }

    protected boolean canStillUse(ServerLevel serverLevel, Taegore owner, long gameTime) {
        return owner.canSearch();
    }

    protected void stop(ServerLevel serverLevel, Taegore owner, long gameTime) {
        boolean finished = this.timedOut(gameTime);
        if (finished) {
            this.calculateDigPosition(owner).ifPresent(pos -> {
                owner.getBrain().setMemory(AetherIIMemoryModuleTypes.TAEGORE_SEARCH_TARGET.get(), pos);
                owner.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(pos, 0.75F, 0));
            });
        }
    }

    protected Optional<BlockPos> calculateDigPosition(Taegore owner) {
        return IntStream.range(0, 5)
                .mapToObj((integer) -> LandRandomPos.getPos(owner, 10 + 2 * integer, 3))
                .filter(Objects::nonNull)
                .map(BlockPos::containing)
                .filter((pos) -> owner.level().getWorldBorder().isWithinBounds(pos))
                .map(BlockPos::below).filter(owner::canDig).findFirst();
    }
}
