package com.gildedgames.aether.world.biome;

import com.gildedgames.aether.world.features.WorldGenAetherTallGrass;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Random;

public class BiomeDecoratorAether extends BiomeDecorator
{
	private static final int TALL_GRASS_PER_CHUNK = 1;

	private WorldGenAetherTallGrass genAetherGrass;

	public void decorate(World world, Random random, BiomeGenBase genBase, BlockPos pos)
	{
		if (this.currentWorld != null)
		{
			throw new RuntimeException("Already decorating!");
		}

		this.currentWorld = world;
		this.randomGenerator = random;
		this.field_180294_c = pos;

		this.genAetherGrass = new WorldGenAetherTallGrass();

		this.genDecorations(genBase);

		this.randomGenerator = null;
		this.currentWorld = null;
	}

	protected void genDecorations(BiomeGenBase genBase)
	{
		int count;

		int x, y, z;

		for (count = 0; count < TALL_GRASS_PER_CHUNK; count++)
		{
			x = this.randomGenerator.nextInt(16) + 8;
			z = this.randomGenerator.nextInt(16) + 8;
			y = nextInt(this.currentWorld.getHeight(this.field_180294_c.add(x, 0, z)).getY() * 2);

			this.genAetherGrass.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(x, y, z));
		}
	}

	protected int nextInt(int i)
	{
		if (i <= 1)
		{
			return 0;
		}

		return this.randomGenerator.nextInt(i);
	}

	protected void generateOres()
	{

	}
}
