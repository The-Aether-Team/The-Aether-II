package com.aetherteam.aetherii.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class TempestSmokeParticle extends TextureSheetParticle {
    public TempestSmokeParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, boolean signal, SpriteSet sprites) {
        super(level, x, y, z);
        this.scale(3.0F);
        this.setSize(0.25F, 0.25F);
        this.gravity = 3.0E-6F;
        this.xd = xSpeed;
        this.yd = ySpeed + this.random.nextFloat() / 500.0F;
        this.zd = zSpeed;
        this.pickSprite(sprites);
        this.lifetime = this.random.nextInt(25) + 40;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ < this.lifetime && !(this.alpha <= 0.0F)) {
            this.xd = this.xd + (double)(this.random.nextFloat() / 5000.0F * (float)(this.random.nextBoolean() ? 1 : -1));
            this.zd = this.zd + (double)(this.random.nextFloat() / 5000.0F * (float)(this.random.nextBoolean() ? 1 : -1));
            this.yd = this.yd - (double)this.gravity;
            this.move(this.xd, this.yd, this.zd);
            if (this.age >= this.lifetime - 10 && this.alpha > 0.01F) {
                this.alpha -= 0.08F;
            }
        } else {
            this.remove();
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            TempestSmokeParticle particle = new TempestSmokeParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, false, this.sprites);
            particle.setAlpha(0.9F);
            return particle;
        }
    }
}
