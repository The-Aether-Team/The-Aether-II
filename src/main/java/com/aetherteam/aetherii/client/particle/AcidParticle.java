package com.aetherteam.aetherii.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class AcidParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected AcidParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
        super(level, x, y, z);
        this.setSize(0.02F, 0.02F);
        this.quadSize *= this.random.nextFloat() * 0.6F + 0.2F;
        this.xd = xSpeed * 0.2 + (Math.random() * 2.0 - 1.0) * 0.02;
        this.yd = ySpeed * 0.2 + (Math.random() * 2.0 - 1.0) * 0.02;
        this.zd = zSpeed * 0.2 + (Math.random() * 2.0 - 1.0) * 0.02;
        this.lifetime = 8 + level.getRandom().nextInt(4);
        this.sprites = sprites;
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
            this.yd += 0.002;
            this.move(this.xd, this.yd, this.zd);
            this.xd *= 0.85;
            this.yd *= 0.85;
            this.zd *= 0.85;
            this.setSpriteFromAge(this.sprites);
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public record Provider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new AcidParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprites());
        }
    }
}
