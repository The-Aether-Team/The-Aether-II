package com.aetherteam.aetherii.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class AetherLeafParticle extends TextureSheetParticle {
    private float rotSpeed;
    private final float spinAcceleration;
    private final float windBig;
    private final boolean swirl;
    private final boolean flowAway;
    private final double xaFlowScale;
    private final double zaFlowScale;
    private final double swirlPeriod;

    protected AetherLeafParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet, float red, float green, float blue) {
        this(level, x, y, z, spriteSet, 0.07F, 10.0F, true, false, 2.0F, 0.021F, red, green, blue);
    }

    public AetherLeafParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet, float gravityFactor, float windBig, boolean swirl, boolean flowAway, float sizeFactor, float ySpeed, float red, float green, float blue) {
        super(level, x, y, z);
        this.setSprite(spriteSet.get(this.random.nextInt(12), 12));
        this.rotSpeed = (float) Math.toRadians(this.random.nextBoolean() ? -30.0 : 30.0);
        float particleRandom = this.random.nextFloat();
        this.spinAcceleration = (float) Math.toRadians(this.random.nextBoolean() ? -5.0 : 5.0);
        this.windBig = windBig;
        this.swirl = swirl;
        this.flowAway = flowAway;
        this.lifetime = 300;
        this.gravity = gravityFactor * 1.2F * 0.0025F;
        float f = sizeFactor * (this.random.nextBoolean() ? 0.05F : 0.075F);
        this.quadSize = f;
        this.setSize(f, f);
        this.friction = 1.0F;
        this.yd = -ySpeed;
        this.xaFlowScale = Math.cos(Math.toRadians(particleRandom * 60.0F)) * (double) this.windBig;
        this.zaFlowScale = Math.sin(Math.toRadians(particleRandom * 60.0F)) * (double) this.windBig;
        this.swirlPeriod = Math.toRadians(1000.0F + particleRandom * 3000.0F);
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
            double d0 = 0.0;
            double d1 = 0.0;
            if (this.flowAway) {
                d0 += this.xaFlowScale * Math.pow(f1, 1.25);
                d1 += this.zaFlowScale * Math.pow(f1, 1.25);
            }

            if (this.swirl) {
                d0 += (double)f1 * Math.cos((double)f1 * this.swirlPeriod) * (double)this.windBig;
                d1 += (double)f1 * Math.sin((double)f1 * this.swirlPeriod) * (double)this.windBig;
            }

            this.xd += d0 * 0.0024999999441206455;
            this.zd += d1 * 0.0024999999441206455;
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

    public record IrradiatedFactory(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new AetherLeafParticle(level, x, y, z, this.spriteSet(), (float) 208 / 255, (float) 184 / 255, (float) 109 / 255);
        }
    }
}
