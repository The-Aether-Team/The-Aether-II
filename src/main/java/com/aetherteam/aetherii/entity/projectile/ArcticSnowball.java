package com.aetherteam.aetherii.entity.projectile;

import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ArcticSnowball extends ThrowableItemProjectile {
    public ArcticSnowball(EntityType<? extends ArcticSnowball> entityType, Level level) {
        super(entityType, level);
    }

    public ArcticSnowball(Level level, LivingEntity shooter, ItemStack stack) {
        super(AetherIIEntityTypes.ARCTIC_SNOWBALL.get(), shooter, level, stack);
    }

    public ArcticSnowball(Level level, double x, double y, double z, ItemStack stack) {
        super(AetherIIEntityTypes.ARCTIC_SNOWBALL.get(), x, y, z, level, stack);
    }

    @Override
    protected Item getDefaultItem() {
        return AetherIIItems.ARCTIC_SNOWBALL.get();
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            ParticleOptions particleoptions = new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(AetherIIItems.ARCTIC_SNOWBALL.get()));
            for (int i = 0; i < 8; ++i) {
                this.level().addParticle(particleoptions, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        int i = entity instanceof Blaze ? 3 : 0;
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), (float) i);
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
