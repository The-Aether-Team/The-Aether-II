package com.aetherteam.aetherii.entity.projectile;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
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

public class HolystoneRock extends ThrowableItemProjectile {
    public HolystoneRock(EntityType<? extends HolystoneRock> entityType, Level level) {
        super(entityType, level);
    }

    public HolystoneRock(Level level, LivingEntity shooter, ItemStack stack) {
        super(AetherIIEntityTypes.HOLYSTONE_ROCK.get(), shooter, level, stack);
    }

    public HolystoneRock(Level level, double x, double y, double z, ItemStack stack) {
        super(AetherIIEntityTypes.HOLYSTONE_ROCK.get(), x, y, z, level, stack);
    }

    @Override
    protected Item getDefaultItem() {
        return AetherIIBlocks.HOLYSTONE_ROCK.asItem();
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            ParticleOptions particleoptions = new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(AetherIIBlocks.HOLYSTONE_ROCK.asItem()));
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
