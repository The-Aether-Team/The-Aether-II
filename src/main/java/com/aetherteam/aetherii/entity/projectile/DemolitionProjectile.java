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

public class DemolitionProjectile extends ThrowableProjectile {
    public DemolitionProjectile(EntityType<? extends DemolitionProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public DemolitionProjectile(double x, double y, double z, Level pLevel) {
        super(AetherIIEntityTypes.DEMOLITION_PROJECTILE.get(), x, y, z, pLevel);
    }

    public DemolitionProjectile(LivingEntity shooter, Level level) {
        super(AetherIIEntityTypes.DEMOLITION_PROJECTILE.get(), shooter.getX(), shooter.getEyeY() - 0.1F, shooter.getZ(), level);
        this.setOwner(shooter);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) { }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        this.level().explode(this, this.getX(), this.getY(), this.getZ(), 0.75F, Level.ExplosionInteraction.NONE);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!(result.getEntity() instanceof SentryGolem sentryGolem) || (this.getOwner() != null && sentryGolem.getId() != this.getOwner().getId())) {
            result.getEntity().hurt(this.level().damageSources().explosion(result.getEntity(), result.getEntity()), 3);
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 0.75F, Level.ExplosionInteraction.NONE);
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
