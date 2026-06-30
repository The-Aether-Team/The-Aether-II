package com.aetherteam.aetherii.entity.ai.brain.behavior.taegore;

import com.aetherteam.aetherii.entity.passive.Taegore;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Predicate;

public class TaegorePanic extends AnimalPanic {
    private final float speedMultiplier;

    public TaegorePanic(float speedMultiplier) {
        super(speedMultiplier);
        this.speedMultiplier = speedMultiplier;
    }

    @Override
    protected void tick(ServerLevel serverLevel, PathfinderMob owner, long gameTime) {
        if (owner instanceof Taegore taegore && taegore.getNavigation().isDone()) {
            Vec3 vec3 = this.getPanicPos(taegore, serverLevel);
            if (vec3 != null) {
                taegore.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(vec3, this.speedMultiplier, 0));
            }
        }
    }

    @Nullable
    private Vec3 getPanicPos(Taegore owner, ServerLevel level) {
        if (owner.isOnFire()) {
            Optional<Vec3> optional = this.lookForWater(level, owner).map(Vec3::atBottomCenterOf);
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return LandRandomPos.getPos(owner, 32, 6);
    }

    private Optional<BlockPos> lookForWater(BlockGetter level, Entity entity) {
        BlockPos blockPos = entity.blockPosition();
        if (!level.getBlockState(blockPos).getCollisionShape(level, blockPos).isEmpty()) {
            return Optional.empty();
        } else {
            Predicate<BlockPos> predicate;
            if (Mth.ceil(entity.getBbWidth()) == 2) {
                predicate = (pos1) -> BlockPos.squareOutSouthEast(pos1).allMatch((pos2) -> level.getFluidState(pos2).is(FluidTags.WATER));
            } else {
                predicate = (pos1) -> level.getFluidState(pos1).is(FluidTags.WATER);
            }
            return BlockPos.findClosestMatch(blockPos, 32, 6, predicate);
        }
    }
}
