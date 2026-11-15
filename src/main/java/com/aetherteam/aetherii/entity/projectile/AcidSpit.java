package com.aetherteam.aetherii.entity.projectile;

import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStackTemplate;
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
            Vec3 movement = this.getDeltaMovement();
            for (int i = 0; i < 15; i++) {
                double xOffset = this.getRandom().nextFloat() * 0.15F * (this.getRandom().nextBoolean() ? -1 : 1);
                double yOffset = this.getRandom().nextFloat() * 0.15F * (this.getRandom().nextBoolean() ? -1 : 1);
                double zOffset = this.getRandom().nextFloat() * 0.15F * (this.getRandom().nextBoolean() ? -1 : 1);

                double multiplier = 0.4 + 0.1 * (double) i;

                this.level().addParticle(AetherIIParticleTypes.ALKAHEST.get(), this.getX() + xOffset, this.getY() + (this.getBbHeight() / 2.0F) + yOffset, this.getZ() + zOffset, movement.x * multiplier, movement.y, movement.z * multiplier);
            }
            if (this.getRandom().nextBoolean()) {
                this.level().addParticle(ParticleTypes.WHITE_SMOKE, this.getRandomX(0.35F), this.getRandomY(0.5F) + (this.getBbHeight() / 1.5F), this.getRandomZ(0.35F), 0, 0.01, 0);
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        Entity entity = hitResult.getEntity();
        int damage = 3;
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

        ParticleOptions particleoptions = new ItemParticleOption(ParticleTypes.ITEM, new ItemStackTemplate(AetherIIItems.SWET_GEL.get()));
        Vec3 offset = new Vec3(0, 0, 1);
        float yRot = this.getYRot();
        if (this.getOwner() != null) {
            offset = new Vec3(0, 0, (this.getOwner().getBbWidth() / 2.0F) * 1.25F);
            yRot = -this.getOwner().getYHeadRot();
        }

        Vec3 pos = new Vec3(this.getX(), this.getY() + (this.getBbHeight() / 2.0F), this.getZ()).add(offset.yRot(yRot * Mth.DEG_TO_RAD));
        for (int i = 0; i < 10; ++i) {
            this.level().addParticle(particleoptions, pos.x(), pos.y(), pos.z(), this.getRandom().nextFloat() * (this.getRandom().nextBoolean() ? -1 : 1) * 0.05, this.getRandom().nextFloat() * 0.1, this.getRandom().nextFloat() * (this.getRandom().nextBoolean() ? -1 : 1) * 0.05);
        }

        this.setDeltaMovement(movement);
    }
}
