package com.aetherteam.aetherii.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class CopyBlockParticle extends SingleQuadParticle {
    public CopyBlockParticle(ClientLevel level, double xCoord, double yCoord, double zCoord, TextureAtlasSprite spriteSet) {
        super(level, xCoord, yCoord, zCoord, spriteSet);
        this.gravity = 0.0F;
        this.lifetime = 80;
        this.hasPhysics = false;
    }

    @Override
    public SingleQuadParticle.Layer getLayer() {
        return Layer.OPAQUE;
    }

    @Override
    public float getQuadSize(float size) {
        return 0.5F;
    }

    public record Provider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
            CopyBlockParticle particle = new CopyBlockParticle(level, x, y, z, this.spriteSet().get(random));
            return particle;
        }
    }
}
