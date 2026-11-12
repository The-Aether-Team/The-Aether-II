package com.aetherteam.aetherii.entity.projectile;

import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class AcidSpit extends ThrowableProjectile {
    public AcidSpit(EntityType<? extends AcidSpit> type, Level level) {
        super(type, level);
    }

    public AcidSpit(double x, double y, double z, Level level) {
        super(AetherIIEntityTypes.ACID_SPIT.get(), x, y, z, level);
    }

    public AcidSpit(LivingEntity owner, Level level) {
        this(owner.getX(), owner.getEyeY() - (double) 0.1F, owner.getZ(), level);
        this.setOwner(owner);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            this.level().addParticle(AetherIIParticleTypes.ALKAHEST.get(), this.getRandomX(0.1F), this.getRandomY(0.1F), this.getRandomZ(0.1F), 0, 0, 0);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        Entity entity = hitResult.getEntity();
        int damage = 2;
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), (float) damage);
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide()) {
            this.discard();
        }
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        Vec3 movement = packet.getMovement();

        for (int i = 0; i < 7; ++i) {
            double k = 0.4 + 0.1 * (double) i;
            this.level().addParticle(AetherIIParticleTypes.ALKAHEST.get(), this.getX(), this.getY(), this.getZ(), movement.x * k, movement.y, movement.z * k);
        }

        this.setDeltaMovement(movement);
    }
}
