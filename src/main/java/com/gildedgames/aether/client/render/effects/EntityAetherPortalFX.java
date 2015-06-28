package com.gildedgames.aether.client.render.effects;

import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.world.World;

public class EntityAetherPortalFX extends EntityPortalFX
{
	public EntityAetherPortalFX(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ)
	{
		super(world, posX, posY, posZ, motionX, motionY, motionZ);

		float colorMultiplier = this.rand.nextFloat() * 0.6F + 0.4F;

		this.particleRed = this.particleGreen = this.particleBlue = 1.0F * colorMultiplier;
		this.particleRed *= 0.2F;
		this.particleGreen *= 0.2F;
	}
}
