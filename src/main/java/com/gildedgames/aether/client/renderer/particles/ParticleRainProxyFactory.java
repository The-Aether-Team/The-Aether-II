package com.gildedgames.aether.client.renderer.particles;

import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.api.world.islands.precipitation.IPrecipitationManager;
import com.gildedgames.aether.common.util.helpers.AetherHelper;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRain;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

// Ugly hack to modify rain particles in the Aether... probably not very fast, either. Oh well.
public class ParticleRainProxyFactory extends ParticleRain.Factory
{
	@Override
	public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn,
			double zSpeedIn, int... p_178902_15_)
	{
		Particle particle = super.createParticle(particleID, worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, p_178902_15_);

		if (!AetherHelper.isAether(worldIn))
		{
			return particle;
		}

		IPrecipitationManager precipitationManager = worldIn.getCapability(CapabilitiesAether.PRECIPITATION_MANAGER, null);

		if (precipitationManager == null)
		{
			return particle;
		}

		if (worldIn.rand.nextFloat() > precipitationManager.getSkyDarkness())
		{
			return null;
		}

		if (particle != null)
		{
			Biome biome = worldIn.getBiome(new BlockPos(xCoordIn, yCoordIn, zCoordIn));

			int color = biome.getWaterColor();

			float red = ((color & 0xFF0000) >> 16) / 255.0f;
			float blue = ((color & 0xFF00) >> 8) / 255.0f;
			float green = (color & 0xFF) / 255.0f;

			particle.setRBGColorF(red, blue, green);
		}

		return particle;

	}
}
