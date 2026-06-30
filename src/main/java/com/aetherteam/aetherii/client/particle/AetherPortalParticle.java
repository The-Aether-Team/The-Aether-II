package com.aetherteam.aetherii.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class AetherPortalParticle extends PortalParticle {
    public AetherPortalParticle(ClientLevel level, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, TextureAtlasSprite spriteSet) {
        super(level, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed);
        this.setSprite(spriteSet);
        float f = this.random.nextFloat() * 0.6F + 0.4F;
        this.rCol = this.gCol = this.bCol = f;
        this.rCol *= 0.2F;
        this.gCol *= 0.2F;
    }

    public record Factory(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            AetherPortalParticle particle = new AetherPortalParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet().get(0, 1));
            return particle;
        }
    }
}