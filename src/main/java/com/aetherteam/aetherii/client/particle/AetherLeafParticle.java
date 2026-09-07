package com.aetherteam.aetherii.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jspecify.annotations.Nullable;

public class AetherLeafParticle extends SingleQuadParticle {
    private float rotSpeed;
    private final float spinAcceleration;
    private final float windBig;
    private final boolean swirl;
    private final boolean flowAway;
    private final double xaFlowScale;
    private final double zaFlowScale;
    private final double swirlPeriod;

    protected AetherLeafParticle(ClientLevel level, double x, double y, double z, TextureAtlasSprite spriteSet) {
        this(level, x, y, z, spriteSet, 0.07F, 10.0F, true, false, 2.0F, 0.021F);
    }

    public AetherLeafParticle(ClientLevel level, double x, double y, double z, TextureAtlasSprite spriteSet, float gravityFactor, float windBig, boolean swirl, boolean flowAway, float sizeFactor, float ySpeed) {
        super(level, x, y, z, spriteSet);
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

    @Override
    public SingleQuadParticle.Layer getLayer() {
        return Layer.OPAQUE;
    }

    public record Provider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
        @Override
        public @Nullable Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double x, double y, double z, double v3, double v4, double v5, RandomSource randomSource) {
            return new AetherLeafParticle(clientLevel, x, y, z, this.spriteSet().get(randomSource));
        }
    }
}
