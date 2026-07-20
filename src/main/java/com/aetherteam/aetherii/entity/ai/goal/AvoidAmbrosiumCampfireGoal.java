package com.aetherteam.aetherii.entity.ai.goal;

import com.aetherteam.aetherii.world.AetherIIPoi;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public class AvoidAmbrosiumCampfireGoal extends Goal {
    private final Mob mob;
    private final int avoidRange;
    private final float speed;
    private BlockPos avoidPos;
    private int avoidTick;

    public AvoidAmbrosiumCampfireGoal(Mob mob, int avoidRange, float speed) {
        this.mob = mob;
        this.avoidRange = avoidRange;
        this.speed = speed;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.mob.isAlive()) {
            Optional<BlockPos> optional = getServerLevel(this.mob.level()).getPoiManager().findClosest(poiTypeHolder -> poiTypeHolder.is(AetherIIPoi.ZEPHYR_AVOID.getKey()), this.mob.blockPosition(), this.avoidRange, PoiManager.Occupancy.ANY);
            if (optional.isPresent()) {
                this.avoidPos = optional.get();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return this.avoidTick < 80 || this.avoidPos != null && this.avoidPos.distManhattan(this.mob.blockPosition()) <= 16;
    }

    @Override
    public void tick() {
        super.tick();
        ++this.avoidTick;
        Vec3 dirAway = this.mob.position().subtract(Vec3.atCenterOf(avoidPos));

        Vec3 pos = generateRandomPos(this.mob, () -> {
            BlockPos direction = RandomPos.generateRandomDirectionWithinRadians(this.mob.getRandom(), (double) 0.0F, (double) 16, 7, 0, dirAway.x, dirAway.z, (double) ((float) Math.PI / 2F));
            return direction == null ? null : generateRandomPosTowardDirection(this.mob, direction);
        });
        if (pos != null) {
            this.mob.getMoveControl().setWantedPosition(pos.x, pos.y, pos.z, this.speed);
        }
    }

    public BlockPos getAvoidPos() {
        return avoidPos;
    }

    public static @Nullable Vec3 generateRandomPos(Mob mob, Supplier<@Nullable BlockPos> posSupplier) {
        Objects.requireNonNull(mob);
        return RandomPos.generateRandomPos(posSupplier, (blockpos) -> 0.0F);
    }

    public static BlockPos generateRandomPosTowardDirection(Mob mob, BlockPos direction) {
        double xt = (double) direction.getX();
        double zt = (double) direction.getZ();

        return BlockPos.containing(xt + mob.getX(), (double) direction.getY() + mob.getY(), zt + mob.getZ());
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void stop() {
        super.stop();
        this.avoidTick = 0;
        this.avoidPos = null;
    }
}
