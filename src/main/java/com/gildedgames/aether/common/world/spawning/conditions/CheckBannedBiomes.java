package com.gildedgames.aether.common.world.spawning.conditions;

import com.gildedgames.aether.api.world.PosCondition;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class CheckBannedBiomes implements PosCondition
{

	private final Biome[] bannedBiomes;

	public CheckBannedBiomes(Biome... bannedBiomes)
	{
		this.bannedBiomes = bannedBiomes;
	}

	@Override
	public boolean isMet(World world, BlockPos spawnAt, BlockPos underneath)
	{
		Biome biome = world.getBiome(spawnAt);

		if (this.bannedBiomes != null)
		{
			for (Biome banned : this.bannedBiomes)
			{
				if (banned == biome)
				{
					return false;
				}
			}
		}

		return true;
	}

}
