package com.aetherteam.aetherii.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import com.aetherteam.aetherii.client.particle.options.ColorParticleOption;
import net.minecraft.util.Mth;

public class EffectBuildupParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected EffectBuildupParticle(ColorParticleOption options, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
        super(level, x, y, z, 0.5 - level.random.nextDouble(), ySpeed, 0.5 - level.random.nextDouble());
        this.friction = 0.96F;
        this.gravity = -0.1F;
        this.speedUpWhenYMotionIsBlocked = true;
        this.sprites = sprites;
        this.yd *= 0.2F;
        if (xSpeed == 0.0 && zSpeed == 0.0) {
            this.xd *= 0.1F;
            this.zd *= 0.1F;
        }
        this.quadSize *= 0.75F;
        this.lifetime = (int) (8.0 / (Math.random() * 0.8 + 0.2));
        this.hasPhysics = false;
        this.setColor(options.getRed(), options.getGreen(), options.getBlue());
        this.setAlpha(options.getAlpha());
        this.setSpriteFromAge(sprites);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
        this.setAlpha(Mth.lerp(0.05F, this.alpha, 1.0F));
    }

    public record Provider(SpriteSet sprites) implements ParticleProvider<ColorParticleOption> {
        @Override
        public Particle createParticle(ColorParticleOption options, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new EffectBuildupParticle(options, level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprites);
        }
    }
}
