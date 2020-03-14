package com.gildedgames.aether.client.renderer.particles;

import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;

public class ParticleAetherStatusEffect extends Particle
{
    public ParticleAetherStatusEffect(World worldIn, double posXIn, double posYIn, double posZIn, double motionX, double motionY, double motionZ, float particleRed, float particleGreen, float particleBlue)
    {
        super(worldIn, posXIn, posYIn, posZIn);

        float f = (float)Math.random() * 0.4F + 0.6F;
        this.particleRed = ((float)(Math.random() * 0.20000000298023224D) + 0.8F) * particleRed * f;
        this.particleGreen = ((float)(Math.random() * 0.20000000298023224D) + 0.8F) * particleGreen * f;
        this.particleBlue = ((float)(Math.random() * 0.20000000298023224D) + 0.8F) * particleBlue * f;

        this.motionY = motionY;
        this.motionX = motionX;
        this.motionZ = motionZ;

        this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));

        this.canCollide = false;
    }
}
