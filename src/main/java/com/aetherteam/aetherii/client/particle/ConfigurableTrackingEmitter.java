package com.aetherteam.aetherii.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class ConfigurableTrackingEmitter extends NoRenderParticle {
    private final Entity entity;
    private final ParticleOptions particleType;
    private final int lifeTime;
    private final int count;
    private int life;

    public ConfigurableTrackingEmitter(ClientLevel level, Entity entity, ParticleOptions particleType, int count) {
        this(level, entity, particleType, 3, entity.getDeltaMovement(), count);
    }

    public ConfigurableTrackingEmitter(ClientLevel level, Entity entity, ParticleOptions particleType, int lifetime, int count) {
        this(level, entity, particleType, lifetime, entity.getDeltaMovement(), count);
    }

    public ConfigurableTrackingEmitter(ClientLevel level, Entity entity, ParticleOptions particleType, int lifetime, Vec3 speedVector, int count) {
        super(level, entity.getX(), entity.getY(0.5), entity.getZ(), speedVector.x, speedVector.y, speedVector.z);
        this.entity = entity;
        this.particleType = particleType;
        this.lifeTime = lifetime;
        this.count = count;
        this.tick();
    }

    public void tick() {
        for (int i = 0; i < this.count; ++i) {
            double d0 = this.random.nextFloat() * 2.0F - 1.0F;
            double d1 = this.random.nextFloat() * 2.0F - 1.0F;
            double d2 = this.random.nextFloat() * 2.0F - 1.0F;
            if (!(d0 * d0 + d1 * d1 + d2 * d2 > 1.0F)) {
                double d3 = this.entity.getX(d0 / 4.0F);
                double d4 = this.entity.getY(0.5F + d1 / 4.0F);
                double d5 = this.entity.getZ(d2 / 4.0F);
                this.level.addParticle(this.particleType, d3, d4, d5, d0, d1 + 0.2, d2);
            }
        }

        ++this.life;
        if (this.life >= this.lifeTime) {
            this.remove();
        }
    }
}
