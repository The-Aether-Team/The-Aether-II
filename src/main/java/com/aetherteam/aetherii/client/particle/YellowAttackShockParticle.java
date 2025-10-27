package com.aetherteam.aetherii.client.particle;

import com.aetherteam.aetherii.client.particle.options.AttackShockParticleOption;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;

public class YellowAttackShockParticle extends TextureSheetParticle {
    private final SpriteSet sprites;
    private final float rotation;

    public YellowAttackShockParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, float rotation, float shade, SpriteSet sprites) {
        super(level, x, y, z);
        this.sprites = sprites;
        this.lifetime = 3;
        this.rCol = shade;
        this.gCol = shade;
        this.bCol = shade;
        this.quadSize = 1.0F;
        this.rotation = rotation;
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
            this.move(this.xd, this.yd, this.zd);
            this.setSpriteFromAge(this.sprites);
        }
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTicks) {
        Quaternionf quaternionf = new Quaternionf();
        quaternionf.rotationYXZ(0.0F, -Mth.HALF_PI, -(this.rotation * Mth.DEG_TO_RAD));
        this.renderRotatedQuad(vertexConsumer, camera, quaternionf, partialTicks);
        quaternionf.rotationYXZ(0.0F, Mth.HALF_PI, ((this.rotation * Mth.DEG_TO_RAD) + Mth.PI));
        this.renderRotatedQuad(vertexConsumer, camera, quaternionf, partialTicks);
    }

    @Override
    public int getLightColor(float partialTick) {
        return 15728880;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Provider implements ParticleProvider<AttackShockParticleOption> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(AttackShockParticleOption options, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new YellowAttackShockParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, options.rotation(), options.shade(), this.sprites);
        }
    }
}
