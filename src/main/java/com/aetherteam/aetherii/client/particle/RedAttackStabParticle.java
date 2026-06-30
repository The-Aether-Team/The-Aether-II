package com.aetherteam.aetherii.client.particle;

import com.aetherteam.aetherii.client.particle.options.AttackStabParticleOption;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;

public class RedAttackStabParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    public RedAttackStabParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, float shade, SpriteSet sprites) {
        super(level, x, y, z);
        this.sprites = sprites;
        this.lifetime = 7;
        this.rCol = shade;
        this.gCol = shade;
        this.bCol = shade;
        this.quadSize = 0.15F;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
        this.setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.xd *= 0.8;
            this.yd *= 0.8;
            this.zd *= 0.8;
            this.move(this.xd, this.yd, this.zd);
            this.setSpriteFromAge(this.sprites);
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public int getLightColor(float partialTick) {
        return 15728880;
    }

    public static class Provider implements ParticleProvider<AttackStabParticleOption> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(AttackStabParticleOption options, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new RedAttackStabParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, options.shade(), this.sprites);
        }
    }
}
