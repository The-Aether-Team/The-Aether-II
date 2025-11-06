package com.aetherteam.aetherii.entity.projectile;

import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.monster.dungeon.SentryGolem;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class DetonationProjectile extends ThrowableProjectile {
    public DetonationProjectile(EntityType<? extends DetonationProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public DetonationProjectile(double pX, double pY, double pZ, Level pLevel) {
        super(AetherIIEntityTypes.DETONATION_PROJECTILE.get(), pX, pY, pZ, pLevel);
    }

    public DetonationProjectile(LivingEntity shooter, Level level) {
        super(AetherIIEntityTypes.DETONATION_PROJECTILE.get(), shooter.getX(), shooter.getEyeY() - 0.1F, shooter.getZ(), level);
        this.setOwner(shooter);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        this.level().explode(this, this.getX(), this.getY(), this.getZ(), 0.5F, Level.ExplosionInteraction.NONE);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!(result.getEntity() instanceof SentryGolem)) {
            result.getEntity().hurt(this.level().damageSources().explosion(result.getEntity(), result.getEntity()), 3);
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 0.5F, Level.ExplosionInteraction.NONE);
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide()) {
            this.discard();
        }
    }
}
