package com.aetherteam.aetherii.client.particle;

import com.aetherteam.aetherii.client.particle.options.GravityDustParticleOption;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.DustParticleBase;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.RandomSource;
import org.joml.Vector3f;

public class GravityDustParticle extends DustParticleBase<GravityDustParticleOption> {
    public GravityDustParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, GravityDustParticleOption options, SpriteSet sprites) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed, options, sprites);
        float f = this.random.nextFloat() * 0.4F + 0.6F;
        Vector3f vector3f = options.getColor();
        this.rCol = this.randomizeColor(vector3f.x(), f);
        this.gCol = this.randomizeColor(vector3f.y(), f);
        this.bCol = this.randomizeColor(vector3f.z(), f);
        this.gravity = options.getGravity();
        this.hasPhysics = false;
        this.lifetime = this.getLifetime() * 3;
    }

    public static class Provider implements ParticleProvider<GravityDustParticleOption> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(GravityDustParticleOption type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new GravityDustParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, type, this.sprites);
        }
    }
}
