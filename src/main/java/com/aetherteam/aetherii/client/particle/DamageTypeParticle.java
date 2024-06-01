package com.aetherteam.aetherii.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class DamageTypeParticle extends TextureSheetParticle {
    public DamageTypeParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, 0.0, 0.0, 0.0);
        this.friction = 0.7F;
        this.gravity = 0.5F;
        this.xd *= 0.1;
        this.yd *= 0.1;
        this.zd *= 0.1;
        this.xd += xSpeed * 0.2;
        this.yd += ySpeed * 0.2;
        this.zd += zSpeed * 0.2;
        float f = (float) (Math.random() * 0.3 + 0.6);
        this.rCol = f;
        this.gCol = f;
        this.bCol = f;
        this.quadSize *= 0.5F;
        this.lifetime = Math.max((int) (6.0 / (Math.random() * 0.8 + 0.6)), 1);
        this.hasPhysics = false;
        this.tick();
    }

    public float getQuadSize(float scaleFactor) {
        return this.quadSize * Mth.clamp(((float) this.age + scaleFactor) / (float) this.lifetime * 32.0F, 0.0F, 1.0F);
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public record Provider(SpriteSet sprite) implements ParticleProvider<SimpleParticleType> {
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            DamageTypeParticle particle = new DamageTypeParticle(level, x, y, z, xSpeed, ySpeed + 1.0, zSpeed);
            particle.setLifetime(20);
            particle.pickSprite(this.sprite);
            return particle;
        }
    }
}
