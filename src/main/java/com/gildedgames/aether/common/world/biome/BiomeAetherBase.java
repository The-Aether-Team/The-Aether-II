package com.gildedgames.aether.common.world.biome;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Random;

public abstract class BiomeAetherBase extends Biome
{
	private final BiomeDecoratorAether biomeDecorator = new BiomeDecoratorAether();

	public BiomeAetherBase(int id)
	{
		super(new BiomeProperties("aether_generic").setRainDisabled().setTemperature(0.5f));

		this.setRegistryName(AetherCore.getResource("aether_generic"));

		this.topBlock = BlocksAether.aether_grass.getDefaultState();
		this.fillerBlock = BlocksAether.aether_dirt.getDefaultState();

		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
	}

	@Override
	public int getSkyColorByTemp(float temp)
	{
		return 0xC0C0FF;
	}

	@Override
	public int getWaterColorMultiplier()
	{
		return 0x70DB70;
	}

	@Override
	public void decorate(World world, Random random, BlockPos pos)
	{
		this.biomeDecorator.genDecorations(world, random, pos, this);
	}

}
