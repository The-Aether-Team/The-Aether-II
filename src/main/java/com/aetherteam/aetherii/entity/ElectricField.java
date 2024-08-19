package com.aetherteam.aetherii.entity;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;

public class ElectricField extends AreaEffectCloud {

    public ElectricField(EntityType<? extends AreaEffectCloud> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
    }

    public ElectricField(Level level, double x, double y, double z) {
        this(AetherIIEntityTypes.ELECTRIC_FIELD.get(), level);
        this.setPos(x, y, z);
    }

    @Override
    public void tick() {
        super.tick();
        boolean flag = this.isWaiting();
        float f = this.getRadius();
        if (this.level().isClientSide) {
            if (flag && this.random.nextBoolean()) {
                return;
            }

            ParticleOptions particleoptions = this.getParticle();
            int i;
            float f1;
            if (flag) {
                i = 2;
                f1 = 0.2F;
            } else {
                i = Mth.ceil((float) Math.PI * f * f);
                f1 = f;
            }

            for (int j = 0; j < i; j++) {
                float f2 = this.random.nextFloat() * (float) (Math.PI * 2);
                float f3 = Mth.sqrt(this.random.nextFloat()) * f1;
                float f4 = Mth.sqrt(this.random.nextFloat()) * 1.5f;
                double d0 = this.getX() + (double)(Mth.cos(f2) * f3);
                double d1 = this.getY() + (double)(Mth.cos(f2) * f4);
                double d3 = this.getY() + (double)(Mth.sin(f2) * f4);
                double d4 = this.getZ() + (double)(Mth.sin(f2) * f3);
                this.level().addAlwaysVisibleParticle(particleoptions, d0, this.getY(), d4, (0.5 - this.random.nextDouble()) * 0.15, 0.1F, (0.5 - this.random.nextDouble()) * 0.15);
                this.level().addAlwaysVisibleParticle(particleoptions, d0, d1, d4, (0.5 - this.random.nextDouble()) * 0.15, 0.1F, (0.5 - this.random.nextDouble()) * 0.15);
                this.level().addAlwaysVisibleParticle(particleoptions, d0, d3, d4, (0.5 - this.random.nextDouble()) * 0.15, 0.1F, (0.5 - this.random.nextDouble()) * 0.15);
                this.level().addAlwaysVisibleParticle(particleoptions, d0, this.getY() + 1.2f, d4, (0.5 - this.random.nextDouble()) * 0.15, 0.1F, (0.5 - this.random.nextDouble()) * 0.15);
            }
        }
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return EntityDimensions.scalable(this.getRadius() * 2.0F, 1.5F);
    }
}
