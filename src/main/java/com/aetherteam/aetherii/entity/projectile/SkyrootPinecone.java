package com.aetherteam.aetherii.entity.projectile;

import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class SkyrootPinecone extends ThrowableItemProjectile {
    public SkyrootPinecone(EntityType<? extends SkyrootPinecone> entityType, Level level) {
        super(entityType, level);
    }

    public SkyrootPinecone(Level level, LivingEntity shooter, ItemStack stack) {
        super(AetherIIEntityTypes.SKYROOT_PINECONE.get(), shooter, level, stack);
    }

    public SkyrootPinecone(Level level, double x, double y, double z, ItemStack stack) {
        super(AetherIIEntityTypes.SKYROOT_PINECONE.get(), x, y, z, level, stack);
    }

    @Override
    protected Item getDefaultItem() {
        return AetherIIItems.SKYROOT_PINECONE.get();
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            ParticleOptions particleoptions = new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(AetherIIItems.SKYROOT_PINECONE.get()));
            for (int i = 0; i < 8; ++i) {
                this.level().addParticle(particleoptions, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), (float) 3);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }
}
