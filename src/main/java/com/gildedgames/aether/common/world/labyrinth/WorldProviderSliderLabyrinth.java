package com.gildedgames.aether.common.world.labyrinth;

import com.gildedgames.aether.common.DimensionsAether;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.chunk.IChunkGenerator;

public class WorldProviderSliderLabyrinth extends WorldProvider
{

	public WorldProviderSliderLabyrinth()
	{
		this.hasNoSky = true;
	}

	@Override
	protected void createBiomeProvider()
	{
		this.biomeProvider = new BiomeProviderSingle(new BiomeSliderLabyrinth());
	}

	@Override
	public Biome getBiomeForCoords(BlockPos pos)
	{
		return this.biomeProvider.getBiome(pos);
	}

	@Override
	public IChunkGenerator createChunkGenerator()
	{
		return new ChunkProviderSliderLabyrinth(this.worldObj, this.worldObj.getSeed());
	}

	@Override
	public boolean canCoordinateBeSpawn(int x, int z)
	{
		return true;
	}

	@Override
	public boolean canRespawnHere()
	{
		return true;
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
	public String getSaveFolder()
	{
		return super.getSaveFolder();
	}

	@Override
	public String getWelcomeMessage()
	{
		return "Descending...";
	}

	@Override
	public String getDepartMessage()
	{
		return "Ascending...";
	}

	@Override
	public double getHorizon()
	{
		return 0.0;
	}

	@Override
	public DimensionType getDimensionType()
	{
		return DimensionsAether.SLIDER_LABYRINTH;
	}

	@Override
	public double getVoidFogYFactor()
	{
		return 100;
	}

	@Override
	public boolean isSkyColored()
	{
		return false;
	}

	@Override
	public Vec3d getSkyColor(Entity par1Entity, float par2)
	{
		return new Vec3d(0.0D, 0.0D, 0.0D);
	}
	
	@Override
	public float[] calcSunriseSunsetColors(float f, float f1)
	{
		return null;
	}

	@Override
	protected void generateLightBrightnessTable()
	{
		super.generateLightBrightnessTable();
	}
	
}
