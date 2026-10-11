package com.aetherteam.aetherii.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class HestveilParticle extends SingleQuadParticle {
    private final float maxQuadSize;

    protected HestveilParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet, TextureAtlasSprite atlasSprite) {
        super(level, x, y, z, atlasSprite);
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
    public SingleQuadParticle.Layer getLayer() {
        return Layer.OPAQUE;
    }

    public record Provider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
            return new HestveilParticle(level, x, y, z, this.sprites(), this.sprites().get(random));
        }
    }
}
