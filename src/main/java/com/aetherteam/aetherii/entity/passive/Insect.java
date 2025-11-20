package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class Insect extends AmbientCreature {
    @Nullable
    private BlockPos targetPosition;

    public Insect(EntityType<? extends Insect> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 6.0F);
    }

    public void tick() {
        super.tick();
        this.setDeltaMovement(this.getDeltaMovement().multiply(1.0F, 0.6, 1.0F));
    }

    protected void customServerAiStep(ServerLevel p_376388_) {
        super.customServerAiStep(p_376388_);
        BlockPos blockpos = this.blockPosition();
        BlockPos blockpos1 = blockpos.above();
        if (this.targetPosition != null && (!p_376388_.isEmptyBlock(this.targetPosition) || this.targetPosition.getY() <= p_376388_.getMinY())) {
            this.targetPosition = null;
        }

        if (this.targetPosition == null || this.random.nextInt(30) == 0 || this.targetPosition.closerToCenterThan(this.position(), (double)2.0F)) {
            this.targetPosition = BlockPos.containing(this.getX() + (double)this.random.nextInt(7) - (double)this.random.nextInt(7), this.getY() + (double)this.random.nextInt(6) - (double)2.0F, this.getZ() + (double)this.random.nextInt(7) - (double)this.random.nextInt(7));
        }

        double d2 = (double)this.targetPosition.getX() + (double)0.5F - this.getX();
        double d0 = (double)this.targetPosition.getY() + 0.1 - this.getY();
        double d1 = (double)this.targetPosition.getZ() + (double)0.5F - this.getZ();
        Vec3 vec3 = this.getDeltaMovement();
        Vec3 vec31 = vec3.add((Math.signum(d2) * (double)0.5F - vec3.x) * (double)0.1F, (Math.signum(d0) * (double)0.7F - vec3.y) * (double)0.1F, (Math.signum(d1) * (double)0.5F - vec3.z) * (double)0.1F);
        this.setDeltaMovement(vec31);
        float f = (float)(Mth.atan2(vec31.z, vec31.x) * (double)180.0F / (double)(float)Math.PI) - 90.0F;
        float f1 = Mth.wrapDegrees(f - this.getYRot());
        this.zza = 0.5F;
        this.setYRot(this.getYRot() + f1);
//        if (this.random.nextInt(100) == 0 && p_376388_.getBlockState(blockpos1).isRedstoneConductor(p_376388_, blockpos1)) {
//            this.setResting(true);
//        }

    }

    protected Entity.MovementEmission getMovementEmission() {
        return MovementEmission.EVENTS;
    }

    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    public boolean isIgnoringBlockTriggers() {
        return true;
    }

    public boolean isPushable() {
        return false;
    }

    protected void doPush(Entity entity) {
    }

    protected void pushEntities() {
    }
}
