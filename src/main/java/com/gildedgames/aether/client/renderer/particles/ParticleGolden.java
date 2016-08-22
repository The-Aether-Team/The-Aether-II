package com.gildedgames.aether.client.renderer.particles;

import net.minecraft.world.World;

public class ParticleGolden extends ParticleAetherPortal
{
	public ParticleGolden(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ)
	{
		super(world, posX, posY, posZ, motionX, motionY, motionZ);

		this.particleBlue = 0.0f;
		this.particleRed = 0.976f;
		this.particleGreen = 0.745f;
	}
}
