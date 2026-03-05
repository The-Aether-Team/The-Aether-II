package com.aetherteam.aetherii.client.particle;

import com.aetherteam.aetherii.client.particle.options.AttackStabParticleOption;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
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
    protected void renderRotatedQuad(VertexConsumer buffer, Quaternionf quaternion, float x, float y, float z, float partialTicks) {
        float f = this.getQuadSize(partialTicks);
        float f1 = this.getU0();
        float f2 = this.getU1();
        float f3 = this.getV0();
        float f4 = this.getV1();
        int i = this.getLightColor(partialTicks);
        this.renderVertex(buffer, quaternion, x, y, z, 1.0F, -1.0F, f, f2, f4, i);
        this.renderVertex(buffer, quaternion, x, y, z, 1.0F, 1.0F, f, f2, f3, i);
        this.renderVertex(buffer, quaternion, x, y, z, -1.0F, 1.0F, f, f1, f3, i);
        this.renderVertex(buffer, quaternion, x, y, z, -1.0F, -1.0F, f, f1, f4, i);
    }

    private void renderVertex(VertexConsumer buffer, Quaternionf quaternion, float x, float y, float z, float xOffset, float yOffset, float quadSize, float u, float v, int packedLight) {
        int count = this.age * (10 - 1) / this.lifetime;
        if (count % 2 == 1) {
            xOffset -= 0.1F;
            yOffset += 0.1F;
        }
        Vector3f vector3f = new Vector3f(xOffset, yOffset, 0.0F).rotate(quaternion).mul(quadSize).add(x, y, z);
        buffer.addVertex(vector3f.x(), vector3f.y(), vector3f.z()).setUv(u, v).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(packedLight);
    }

    @Override
    public int getLightColor(float partialTick) {
        return 15728880;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Provider implements ParticleProvider<AttackStabParticleOption> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(AttackStabParticleOption options, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed,
                                       RandomSource randomSource) {
            return new RedAttackStabParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, options.shade(), this.sprites);
        }
    }
}
