package com.aetherteam.aetherii.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;

public class AmbrosiumParticle extends BaseAshSmokeParticle {
    protected AmbrosiumParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, float quadSizeMultiplier, SpriteSet sprites) {
        super(level, x, y, z, 0.1F, 0.0F, 0.1F, xSpeed, ySpeed, zSpeed, quadSizeMultiplier, sprites, 0.0F, 20, 0.025F, true);
        this.rCol = 1.0F;
        this.gCol = 1.0F;
        this.bCol = 1.0F;
        this.quadSize *= 1.75F;
        this.pickSprite(sprites);
    }

    @Override
    public void tick() {
        super.tick();
        int endingPackedColor = 16052832;
        int startingPackedColor = 16777215;
        float durationRatio = (float) this.age / this.lifetime;
        this.rCol = (FastColor.ARGB32.red(startingPackedColor) + (int) (durationRatio * (FastColor.ARGB32.red(endingPackedColor) - FastColor.ARGB32.red(startingPackedColor)))) / 255.0F;
        this.gCol = (FastColor.ARGB32.green(startingPackedColor) + (int) (durationRatio * (FastColor.ARGB32.green(endingPackedColor) - FastColor.ARGB32.green(startingPackedColor)))) / 255.0F;
        this.bCol = (FastColor.ARGB32.blue(startingPackedColor) + (int) (durationRatio * (FastColor.ARGB32.blue(endingPackedColor) - FastColor.ARGB32.blue(startingPackedColor)))) / 255.0F;
    }

    @Override
    public int getLightColor(float partialTick) {
        float f = ((float)this.age + partialTick) / (float)this.lifetime;
        f = Mth.clamp(f, 0.0F, 1.0F);
        int i = super.getLightColor(partialTick);
        int j = i & 0xFF;
        int k = i >> 16 & 0xFF;
        j += (int)(f * 15.0F * 16.0F);
        if (j > 240) {
            j = 240;
        }

        return j | k << 16;
    }

    @Override
    public void setSpriteFromAge(SpriteSet sprite) {

    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new AmbrosiumParticle(level, x, y, z, 0.0, 0.0, 0.0, 1.0F, this.sprites);
        }
    }
}
