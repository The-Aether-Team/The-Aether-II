package com.aetherteam.aetherii.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.FastColor;

public class AmbrosiumTorchParticle extends BaseAshSmokeParticle {
    protected AmbrosiumTorchParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, float quadSizeMultiplier, SpriteSet sprites) {
        super(level, x, y, z, 0.1F, 0.0F, 0.1F, xSpeed, ySpeed, zSpeed, quadSizeMultiplier, sprites, 0.0F, 20, 0.025F, false);
        float f = Math.max(level.getRandom().nextFloat(), 0.65F);
        this.rCol = ((float) FastColor.ARGB32.red(16711631) / 255.0F) * f;
        this.gCol = ((float) FastColor.ARGB32.green(16711631) / 255.0F) * f;
        this.bCol = ((float) FastColor.ARGB32.blue(16711631) / 255.0F) * f;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet pSprites) {
            this.sprites = pSprites;
        }

        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new AmbrosiumTorchParticle(level, x, y, z, 0.0, 0.0, 0.0, 1.0F, this.sprites);
        }
    }
}
