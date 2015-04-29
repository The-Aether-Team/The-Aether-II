package com.gildedgames.aether.world;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderAether extends WorldProvider
{
	private float[] skyColors = new float[4];

	@Override
	public float[] calcSunriseSunsetColors(float f, float f1)
	{
		float f2 = 0.4F;
		float f3 = MathHelper.cos(f * 3.141593F * 2.0F) - 0.0F;
		float f4 = -0F;

		if (f3 >= f4 - f2 && f3 <= f4 + f2)
		{
			float f5 = (f3 - f4) / f2 * 0.5F + 0.5F;
			float f6 = 1.0F - (1.0F - MathHelper.sin(f5 * 3.141593F)) * 0.99F;
			f6 *= f6;
			this.skyColors[0] = f5 * 0.3F + 0.1F;
			this.skyColors[1] = f5 * f5 * 0.7F + 0.2F;
			this.skyColors[2] = f5 * f5 * 0.7F + 0.2F;
			this.skyColors[3] = f6;
			return this.skyColors;
		}
		else
		{
			return null;
		}
	}

	@Override
	public int getRespawnDimension(EntityPlayerMP player)
	{
		return 0;
	}

	@Override
	public boolean isSurfaceWorld()
	{
		return true;
	}

	@Override
	public boolean canCoordinateBeSpawn(int i, int j)
	{
		Block k = this.worldObj.getGroundAboveSeaLevel(new BlockPos(i, 0, j));

		return k != Blocks.air && k.getMaterial().isSolid();
	}

	@Override
	public boolean canRespawnHere()
	{
		return true;
	}

	@Override
	public IChunkProvider createChunkGenerator()
	{
		return new ChunkProviderAether(this.worldObj, this.worldObj.getSeed());
	}

	@Override
	public String getDimensionName()
	{
		return "Aether";
	}

	@Override
	public String getInternalNameSuffix()
	{
		return "";
	}

	@Override
	public Vec3 getFogColor(float f, float f1)
	{
		int i = 0x8080a0;

		float f2 = MathHelper.cos(f * 3.141593F * 2.0F) * 2.0F + 0.5F;

		if (f2 < 0.0F)
		{
			f2 = 0.0F;
		}
		else if (f2 > 1.0F)
		{
			f2 = 1.0F;
		}

		float f3 = (i >> 16 & 0xff) / 255F;
		float f4 = (i >> 8 & 0xff) / 255F;
		float f5 = (i & 0xff) / 255F;
		f3 *= f2 * 0.94F + 0.06F;
		f4 *= f2 * 0.94F + 0.06F;
		f5 *= f2 * 0.91F + 0.09F;

		return new Vec3(f3, f4, f5);
	}

	@Override
	public String getSaveFolder()
	{
		return "AETHER";
	}

	@Override
	public double getVoidFogYFactor()
	{
		return 100;
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
	public boolean doesXZShowFog(int x, int z)
	{
		return false;
	}

	@Override
	public boolean isSkyColored()
	{
		return true;
	}

	@Override
	protected void registerWorldChunkManager()
	{
		this.worldChunkMgr = new WorldChunkManagerAether();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IRenderHandler getSkyRenderer()
	{
		return super.getSkyRenderer();
	}

	@Override
	public double getHorizon()
	{
		return 0.0;
	}

	@Override
	public float getCloudHeight()
	{
		return -128;
	}
}
