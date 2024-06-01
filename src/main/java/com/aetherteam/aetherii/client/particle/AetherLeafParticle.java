package com.aetherteam.aetherii.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class AetherLeafParticle extends TextureSheetParticle {
    private float rotSpeed;
    private final float particleRandom;
    private final float spinAcceleration;

    protected AetherLeafParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet pSpriteSet, float red, float green, float blue) {
        super(pLevel, pX, pY, pZ);
        this.setSprite(pSpriteSet.get(this.random.nextInt(12), 12));
        this.rotSpeed = (float) Math.toRadians(this.random.nextBoolean() ? -30.0 : 30.0);
        this.particleRandom = this.random.nextFloat();
        this.spinAcceleration = (float) Math.toRadians(this.random.nextBoolean() ? -5.0 : 5.0);
        this.lifetime = 300;
        this.gravity = 7.5E-4F;
        float f = this.random.nextBoolean() ? 0.075F : 0.1F;
        this.quadSize = f;
        this.setSize(f, f);
        this.friction = 1.0F;
        this.rCol = red;
        this.gCol = green;
        this.bCol = blue;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.lifetime-- <= 0) {
            this.remove();
        }

        if (!this.removed) {
            float f = (float) (300 - this.lifetime);
            float f1 = Math.min(f / 300.0F, 1.0F);
            double d0 = Math.cos(Math.toRadians(this.particleRandom * 60.0F)) * 2.0 * Math.pow(f1, 1.25);
            double d1 = Math.sin(Math.toRadians(this.particleRandom * 60.0F)) * 2.0 * Math.pow(f1, 1.25);
            this.xd += d0 * 0.0025F;
            this.zd += d1 * 0.0025F;
            this.yd -= this.gravity;
            this.rotSpeed += this.spinAcceleration / 20.0F;
            this.oRoll = this.roll;
            this.roll += this.rotSpeed / 20.0F;
            this.move(this.xd, this.yd, this.zd);
            if (this.onGround || this.lifetime < 299 && (this.xd == 0.0 || this.zd == 0.0)) {
                this.remove();
            }

            if (!this.removed) {
                this.xd *= this.friction;
                this.yd *= this.friction;
                this.zd *= this.friction;
            }
        }
    }

    public record SkyrootFactory(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new AetherLeafParticle(level, x, y, z, this.spriteSet(), (float) 155 / 255, (float) 192 / 255, (float) 109 / 255);
        }
    }

    public record SkyplaneFactory(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new AetherLeafParticle(level, x, y, z, this.spriteSet(), (float) 110 / 255, (float) 121 / 255, (float) 179 / 255);
        }
    }

    public record SkybirchFactory(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new AetherLeafParticle(level, x, y, z, this.spriteSet(), (float) 124 / 255, (float) 169 / 255, (float) 190 / 255);
        }
    }

    public record SkypineFactory(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new AetherLeafParticle(level, x, y, z, this.spriteSet(), (float) 197 / 255, (float) 161 / 255, (float) 216 / 255);
        }
    }

    public record WisprootFactory(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new AetherLeafParticle(level, x, y, z, this.spriteSet(), (float) 158 / 255, (float) 187 / 255, (float) 209 / 255);
        }
    }

    public record WisptopFactory(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new AetherLeafParticle(level, x, y, z, this.spriteSet(), (float) 207 / 255, (float) 188 / 255, (float) 226 / 255);
        }
    }

    public record GreatrootFactory(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new AetherLeafParticle(level, x, y, z, this.spriteSet(), (float) 132 / 255, (float) 148 / 255, (float) 92 / 255);
        }
    }

    public record GreatoakFactory(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new AetherLeafParticle(level, x, y, z, this.spriteSet(), (float) 184 / 255, (float) 151 / 255, (float) 196 / 255);
        }
    }

    public record GreatboaFactory(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new AetherLeafParticle(level, x, y, z, this.spriteSet(), (float) 116 / 255, (float) 117 / 255, (float) 166 / 255);
        }
    }

    public record AmberootFactory(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new AetherLeafParticle(level, x, y, z, this.spriteSet(), (float) 240 / 255, (float) 231 / 255, (float) 101 / 255);
        }
    }
}
