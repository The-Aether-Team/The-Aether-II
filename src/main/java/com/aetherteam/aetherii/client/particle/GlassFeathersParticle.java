package com.aetherteam.aetherii.client.particle;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GlassFeathersParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    public GlassFeathersParticle(ClientLevel level, double x, double y, double z, double xa, double ya, double za, SpriteSet sprites) {
        super(level, x, y, z);
        this.setSprite(sprites.get(this.random));
        this.gravity = 0.2F;
        this.friction = 0.9F;
        this.sprites = sprites;
        this.xd = xa + ((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F);
        this.yd = ya + ((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F);
        this.zd = za + ((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F);
        this.quadSize = (this.random.nextFloat() * 0.1F) + 0.3F;
        this.lifetime = 8 + this.random.nextInt(10);
        this.setSpriteFromAge(sprites);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(SimpleParticleType options, ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux) {
            return new GlassFeathersParticle(level, x, y, z, xAux, yAux, zAux, this.sprites);
        }
    }
}
