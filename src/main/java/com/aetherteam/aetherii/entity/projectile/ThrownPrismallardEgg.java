package com.aetherteam.aetherii.entity.projectile;

import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.passive.Prismallard;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ThrownPrismallardEgg extends ThrowableItemProjectile {

    public ThrownPrismallardEgg(EntityType<? extends ThrownPrismallardEgg> type, Level level) {
        super(type, level);
    }

    public ThrownPrismallardEgg(Level level, LivingEntity mob, ItemStack itemStack) {
        super(AetherIIEntityTypes.PRISMALLARD_EGG.get(), mob, level, itemStack);
    }

    public ThrownPrismallardEgg(Level level, double x, double y, double z, ItemStack itemStack) {
        super(AetherIIEntityTypes.PRISMALLARD_EGG.get(), x, y, z, level, itemStack);
    }

    public void handleEntityEvent(byte id) {
        if (id == 3) {
            ItemStack item = this.getItem();
            if (!item.isEmpty()) {
                ItemParticleOption breakParticle = new ItemParticleOption(ParticleTypes.ITEM, ItemStackTemplate.fromNonEmptyStack(item));

                for(int i = 0; i < 8; ++i) {
                    this.level().addParticle(breakParticle, this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - (double)0.5F) * 0.08, ((double)this.random.nextFloat() - (double)0.5F) * 0.08, ((double)this.random.nextFloat() - (double)0.5F) * 0.08);
                }
            }
        }

    }

    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        hitResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.0F);
    }

    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide()) {
            if (this.random.nextInt(8) == 0) {
                int count = 1;
                if (this.random.nextInt(32) == 0) {
                    count = 4;
                }

                for(int i = 0; i < count; ++i) {
                    Prismallard prismallard = AetherIIEntityTypes.PRISMALLARD.get().create(this.level(), EntitySpawnReason.TRIGGERED);
                    if (prismallard != null) {
                        prismallard.setAge(-24000);
                        prismallard.snapTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                        this.level().addFreshEntity(prismallard);
                    }
                }
            }

            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

    }

    protected Item getDefaultItem() {
        return AetherIIItems.PRISMALLARD_EGG.get();
    }
}