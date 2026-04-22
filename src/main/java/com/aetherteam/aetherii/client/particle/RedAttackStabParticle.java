package com.aetherteam.aetherii.client.particle;

import com.aetherteam.aetherii.client.particle.options.AttackStabParticleOption;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.state.level.QuadParticleRenderState;
import net.minecraft.util.ARGB;
import net.minecraft.util.RandomSource;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class RedAttackStabParticle extends AttackSweepParticle {
    private final SpriteSet sprites;

    public RedAttackStabParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, float shade, SpriteSet sprites) {
        super(level, x, y, z, 1.0F, sprites);
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
    protected void extractRotatedQuad(QuadParticleRenderState particleTypeRenderState, Quaternionf rotation, float x, float y, float z, float partialTickTime) {
        int count = this.age * (10 - 1) / this.lifetime;
        if (count % 2 == 1) {
            Vector3f offset = new Vector3f(-0.1F, 0.1F, 0.0F).rotate(rotation).mul(this.getQuadSize(partialTickTime));
            x += offset.x();
            y += offset.y();
            z += offset.z();
        }
        particleTypeRenderState.add(this.getLayer(), x, y, z, rotation.x, rotation.y, rotation.z, rotation.w, this.getQuadSize(partialTickTime), this.getU0(), this.getU1(), this.getV0(), this.getV1(), ARGB.colorFromFloat(this.alpha, this.rCol, this.gCol, this.bCol), this.getLightCoords(partialTickTime));
    }

    @Override
    public int getLightCoords(float partialTick) {
        return 15728880;
    }

    @Override
    public SingleQuadParticle.Layer getLayer() {
        return Layer.OPAQUE;
    }

    public static class Provider implements ParticleProvider<AttackStabParticleOption> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(AttackStabParticleOption options, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource randomSource) {
            return new RedAttackStabParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, options.shade(), this.sprites);
        }
    }
}
