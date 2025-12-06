package com.aetherteam.aetherii.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class SpecialDungeonBlockParticle extends TextureSheetParticle {
    public SpecialDungeonBlockParticle(ClientLevel level, double xCoord, double yCoord, double zCoord) {
        super(level, xCoord, yCoord, zCoord);
        this.gravity = 0.0F;
        this.lifetime = 80;
        this.hasPhysics = false;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public float getQuadSize(float size) {
        return 0.5F;
    }

    public record Provider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            SpecialDungeonBlockParticle particle = new SpecialDungeonBlockParticle(level, x, y, z);
            particle.pickSprite(this.spriteSet());
            return particle;
        }
    }
}
