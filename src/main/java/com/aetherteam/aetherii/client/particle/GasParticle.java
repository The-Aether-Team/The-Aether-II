package com.aetherteam.aetherii.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class GasParticle extends TextureSheetParticle {
    private final float maxQuadSize;

    protected GasParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.setSprite(spriteSet.get(level.getRandom()));
        this.setSize(0.25F, 0.25F);
        this.quadSize = 0;
        this.maxQuadSize = this.random.nextFloat() * 0.2F + 0.2F;
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
        this.lifetime = 20 + level.getRandom().nextInt(15);
    }

    @Override
    public void tick() {
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            float increment = this.maxQuadSize / this.lifetime;
            if (this.age <= this.lifetime / 2) {
                this.quadSize += increment;
            } else {
                this.quadSize -= increment;
            }
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
            return new GasParticle(level, x, y, z, this.sprites());
        }
    }
}
