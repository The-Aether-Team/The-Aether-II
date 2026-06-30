package com.aetherteam.aetherii.entity.projectile;

import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
    protected void defineSynchedData() { }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        float range = this.getOwner() instanceof Player ? 2.0F : 1.5F;
        this.level().explode(this, this.getX(), this.getY(), this.getZ(), range, Level.ExplosionInteraction.NONE);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        float range = this.getOwner() instanceof Player ? 1.5F : 1.25F;
        this.level().explode(this, this.getX(), this.getY(), this.getZ(), range, Level.ExplosionInteraction.NONE);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide()) {
            this.discard();
        }
    }
}
