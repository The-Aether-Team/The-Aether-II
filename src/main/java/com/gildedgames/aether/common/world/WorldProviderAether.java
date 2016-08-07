package com.gildedgames.aether.common.world;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.DimensionsAether;
import com.gildedgames.aether.common.world.biome.BiomeAether;
import com.gildedgames.aether.common.world.chunk.ChunkGeneratorAether;
import com.gildedgames.aether.common.world.chunk.ChunkGeneratorAetherOld;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderAether extends WorldProvider
{
	private float[] sunriseSunsetColors = new float[4];

	private final DimensionType dimensionType = DimensionsAether.AETHER;

	public WorldProviderAether()
	{
		this.hasNoSky = false;
	}

	@Override
	protected void createBiomeProvider()
	{
		this.biomeProvider = new BiomeProviderSingle(new BiomeAether());
	}

	@Override
	public Biome getBiomeForCoords(BlockPos pos)
	{
		return this.biomeProvider.getBiome(pos);
	}

	@Override
	public IChunkGenerator createChunkGenerator()
	{
		return new ChunkGeneratorAetherOld(this.worldObj, this.worldObj.getSeed());
	}

	public IBlockState getTopBlock(BlockPos pos)
	{
		BlockPos blockpos;

		for (blockpos = new BlockPos(pos.getX(), 0, pos.getZ()); !this.worldObj.isAirBlock(blockpos.up()); blockpos = blockpos.up())
		{
			;
		}

		return this.worldObj.getBlockState(blockpos);
	}

	@Override
	public boolean canCoordinateBeSpawn(int x, int z)
	{
		return this.getTopBlock(new BlockPos(x, 0, z)).getMaterial().isSolid();
	}

	@Override
	public boolean canRespawnHere()
	{
		return true;
	}

	@Override
	public boolean isSurfaceWorld()
	{
		return true;
	}

	@Override
	public int getRespawnDimension(EntityPlayerMP player)
	{
		return 0;
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
	public DimensionType getDimensionType()
	{
		return this.dimensionType;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vec3d getFogColor(float angle, float partialTicks)
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

		return new Vec3d(red, green, blue);
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
