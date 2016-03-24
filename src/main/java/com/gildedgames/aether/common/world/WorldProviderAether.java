package com.gildedgames.aether.common.world;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderAether extends WorldProvider
{

	private float[] sunriseSunsetColors = new float[4];

	@Override
	protected void registerWorldChunkManager()
	{
		this.worldChunkMgr = new WorldChunkManagerAether();
	}

	@Override
	public IChunkProvider createChunkGenerator()
	{
		return new ChunkProviderAether(this.worldObj, this.worldObj.getSeed());
	}

	@Override
	public boolean canCoordinateBeSpawn(int x, int z)
	{
		return this.worldObj.getGroundAboveSeaLevel(new BlockPos(x, 0, z)).getMaterial().isSolid();
	}

	@Override
	public boolean canRespawnHere()
	{
		return false;
	}

	@Override
	public boolean isSurfaceWorld()
	{
		return false;
	}

	@Override
	public int getRespawnDimension(EntityPlayerMP player)
	{
		return 0;
	}

	@Override
	public String getDimensionName()
	{
		return "Aether";
	}

	@Override
	public String getInternalNameSuffix()
	{
		return "_aether";
	}

	@Override
	public String getSaveFolder()
	{
		return "AETHER";
	}

	@Override
	public String getWelcomeMessage()
	{
		return "Ascending to the Aether";
	}

	@Override
	public String getDepartMessage()
	{
		return "Descending from the Aether";
	}

	@Override
	public double getHorizon()
	{
		return 0.0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vec3 getFogColor(float angle, float partialTicks)
	{
		int color = 0x8080a0;

		float cos = MathHelper.cos(angle * 3.141593F * 2.0F) * 2.0F + 0.5F;

		if (cos < 0.0F)
		{
			cos = 0.0F;
		}
		else if (cos > 1.0F)
		{
			cos = 1.0F;
		}

		float red = (color >> 16 & 0xff) / 255F;
		float green = (color >> 8 & 0xff) / 255F;
		float blue = (color & 0xff) / 255F;

		red *= cos * 0.94F + 0.06F;
		green *= cos * 0.94F + 0.06F;
		blue *= cos * 0.91F + 0.09F;

		return new Vec3(red, green, blue);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float[] calcSunriseSunsetColors(float angle, float partialTicks)
	{
		float f2 = 0.4F;
		float cos = MathHelper.cos(angle * 3.141593F * 2.0F) - 0.0F;
		float f4 = -0F;

		if (cos >= f4 - f2 && cos <= f4 + f2)
		{
			float f5 = (cos - f4) / f2 * 0.5F + 0.5F;
			float f6 = 1.0F - (1.0F - MathHelper.sin(f5 * 3.141593F)) * 0.99F;
			f6 *= f6;

			this.sunriseSunsetColors[0] = f5 * 0.3F + 0.1F;
			this.sunriseSunsetColors[1] = f5 * f5 * 0.7F + 0.2F;
			this.sunriseSunsetColors[2] = f5 * f5 * 0.7F + 0.2F;
			this.sunriseSunsetColors[3] = f6;

			return this.sunriseSunsetColors;
		}
		else
		{
			return null;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getVoidFogYFactor()
	{
		return 128;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getCloudHeight()
	{
		return -128;
	}
}
