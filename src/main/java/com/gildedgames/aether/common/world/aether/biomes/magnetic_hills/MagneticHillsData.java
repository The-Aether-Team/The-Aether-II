package com.gildedgames.aether.common.world.aether.biomes.magnetic_hills;

import com.gildedgames.orbis.lib.util.io.NBTFunnel;
import com.gildedgames.orbis.lib.util.mc.NBT;
import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public class MagneticHillsData implements NBT
{
	private long seed;

	private int shaftCount, spawnRadius;

	private List<MagneticHillPillar> magneticPillars;

	private BlockPos center;

	private MagneticHillsData()
	{

	}

	public MagneticHillsData(final BlockPos center, final long seed, final int shaftCount, final int spawnRadius)
	{
		this.center = center;
		this.seed = seed;
		this.shaftCount = shaftCount;
		this.spawnRadius = spawnRadius;
	}

	public Collection<MagneticHillPillar> getMagneticPillars()
	{
		if (this.magneticPillars == null)
		{
			this.magneticPillars = Lists.newArrayList();

			final Random rand = new Random(this.seed);

			for (int i = 0; i < this.shaftCount; i++)
			{
				final boolean negPillar = rand.nextInt(4) == 0;

				final BlockPos pos = this.center
						.add(rand.nextInt(this.spawnRadius) * (rand.nextBoolean() ? -1 : 1),
								(negPillar ? -rand.nextInt(30) : rand.nextInt(30)) + (negPillar ? -50 : 10),
								rand.nextInt(this.spawnRadius) * (rand.nextBoolean() ? -1 : 1));

				final float elongationMod = (rand.nextFloat() * 0.5F) + 0.25F;
				final int topHeight = rand.nextInt(10) + 30;
				final double radius = (rand.nextDouble() * 20.0D) + 15D;

				final MagneticHillPillar pillar = new MagneticHillPillar(elongationMod, topHeight, pos, radius);

				this.magneticPillars.add(pillar);
			}
		}

		return this.magneticPillars;
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = new NBTFunnel(tag);

		funnel.setPos("center", this.center);
		tag.setLong("seed", this.seed);
		tag.setInteger("shaftCount", this.shaftCount);
		tag.setInteger("spawnRadius", this.spawnRadius);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = new NBTFunnel(tag);

		this.center = funnel.getPos("center");
		this.seed = tag.getLong("seed");
		this.shaftCount = tag.getInteger("shaftCount");
		this.spawnRadius = tag.getInteger("spawnRadius");
	}
}
