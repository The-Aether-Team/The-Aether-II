package com.aetherteam.aetherii.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class IrradiationParticle extends BaseAshSmokeParticle {
    protected IrradiationParticle(
            ClientLevel pLevel,
            double pX,
            double pY,
            double pZ,
            double pXSpeed,
            double pYSpeed,
            double pZSpeed,
            float pQuadSizeMultiplier,
            SpriteSet pSprites
    ) {
        super(pLevel, pX, pY, pZ, 0.1F, -0.1F, 0.1F, pXSpeed, pYSpeed, pZSpeed, pQuadSizeMultiplier, pSprites, 1.0F, 20, 0.0F, false);
        this.rCol = (float) 229 / 255;
        this.gCol = (float) 191 / 255;
        this.bCol = (float) 78 / 255;
        this.gravity = this.random.nextBoolean() ? -0.01F : 0.01F;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Factory(SpriteSet pSprites) {
            this.sprites = pSprites;
        }

        public Particle createParticle(
                SimpleParticleType pType,
                ClientLevel pLevel,
                double pX,
                double pY,
                double pZ,
                double pXSpeed,
                double pYSpeed,
                double pZSpeed
        ) {
            return new IrradiationParticle(pLevel, pX, pY, pZ, 0.0, 0.0, 0.0, 1.0F, this.sprites);
        }
    }
}
