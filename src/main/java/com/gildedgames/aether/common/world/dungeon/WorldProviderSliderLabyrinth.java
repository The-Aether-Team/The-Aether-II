package com.gildedgames.aether.common.world.dungeon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderSliderLabyrinth extends WorldProvider
{
	
	public WorldProviderSliderLabyrinth()
	{
		super();
		
		this.hasNoSky = true;
	}

	@Override
	protected void registerWorldChunkManager()
	{
		this.worldChunkMgr = new WorldChunkManagerSliderLabyrinth();
	}

	@Override
	public IChunkProvider createChunkGenerator()
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
	public String getDimensionName()
	{
		return "Slider's Labyrinth";
	}

	@Override
	public String getInternalNameSuffix()
	{
		return "_slider_labyrinth";
	}

	@Override
	public String getSaveFolder()
	{
		return "SLIDER_LABYRINTH" + dimensionId;
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
	public Vec3 getSkyColor(Entity par1Entity, float par2)
	{
		return new Vec3(0.0D, 0.0D, 0.0D);
	}
	
	@Override
	public float[] calcSunriseSunsetColors(float f, float f1)
	{
		return null;
	}
	
}
