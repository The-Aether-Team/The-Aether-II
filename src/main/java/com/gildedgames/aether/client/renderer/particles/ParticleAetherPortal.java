package com.gildedgames.aether.client.renderer.particles;

import net.minecraft.client.particle.ParticlePortal;
import net.minecraft.world.World;

public class ParticleAetherPortal extends ParticlePortal
{
	public ParticleAetherPortal(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ)
	{
		super(world, posX, posY, posZ, motionX, motionY, motionZ);

		float colorMultiplier = this.rand.nextFloat() * 0.6F + 0.4F;

		this.particleRed = this.particleGreen = this.particleBlue = 1.0F * colorMultiplier;
		this.particleRed *= 0.2F;
		this.particleGreen *= 0.2F;
	}
}
