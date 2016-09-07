package com.gildedgames.aether.common.world.spawning.conditions;

import com.gildedgames.aether.common.world.spawning.PosCondition;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class CheckBiome implements PosCondition
{

	private Biome biomeToCheckFor;

	public CheckBiome(Biome biomeToCheckFor)
	{
		this.biomeToCheckFor = biomeToCheckFor;
	}

	@Override
	public boolean isMet(World world, BlockPos spawnAt, BlockPos underneath)
	{
		return world.getBiome(spawnAt) == this.biomeToCheckFor;
	}

}
