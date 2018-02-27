package com.gildedgames.aether.common.world.necromancer_tower;

import com.gildedgames.aether.common.registry.content.BiomesAether;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldProviderNecromancerTower extends WorldProviderSurface
{

	public WorldProviderNecromancerTower()
	{

	}

	@Override
	protected void init()
	{
		this.hasSkyLight = false;
		this.biomeProvider = new BiomeProviderSingle(BiomesAether.INSTANCED_ZONE);
	}

	@Override
	public Biome getBiomeForCoords(final BlockPos pos)
	{
		return this.biomeProvider.getBiome(pos);
	}

	@Override
	public IChunkGenerator createChunkGenerator()
	{
		return new ChunkProviderNecromancerTower(this.world, this.world.getSeed());
	}

	@Override
	public boolean canCoordinateBeSpawn(final int x, final int z)
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
	public int getRespawnDimension(final EntityPlayerMP player)
	{
		return 0;
	}

	@Override
	public String getSaveFolder()
	{
		return super.getSaveFolder();
	}

	@Override
	public double getHorizon()
	{
		return 0.0;
	}

	@Override
	public DimensionType getDimensionType()
	{
		return DimensionsAether.NECROMANCER_TOWER;
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
	public Vec3d getSkyColor(final Entity par1Entity, final float par2)
	{
		return new Vec3d(0.0D, 0.0D, 0.0D);
	}

	@Override
	public float[] calcSunriseSunsetColors(final float f, final float f1)
	{
		return null;
	}

	@Override
	protected void generateLightBrightnessTable()
	{
		for (int i = 0; i <= 15; ++i)
		{
			final float factor = 1.0F - (float) i / 15.0F;

			this.lightBrightnessTable[i] = (1.0F - factor) / (factor * 3.0F + 1.0F) * 0.9F + 0.1F;
		}
	}

	@Override
	public boolean canDropChunk(final int x, final int z)
	{
		return true;
	}

	@Override
	public boolean canMineBlock(final EntityPlayer player, final BlockPos pos)
	{
		// Doing this will prevent buckets from being used when not GM1, but will not affect block
		// placement/destruction (only called in World#isBlockModifiable(EntityPlayer, Blockpos) )
		return (player.capabilities.isCreativeMode);
	}

	@Override
	public float calculateCelestialAngle(final long worldTime, final float partialTicks)
	{
		return 0.5F;
	}

}
