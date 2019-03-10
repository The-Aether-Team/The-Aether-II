package com.gildedgames.aether.common.entities.util;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.genes.util.GeneUtil;
import com.gildedgames.aether.common.entities.living.mounts.EntityMoa;
import com.gildedgames.orbis.lib.util.mc.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MoaNest
{

	public final World world;

	public BlockPos pos;

	public int familyGeneticSeed;

	public boolean hasInitialized;

	protected EntityGroup pack;

	public MoaNest(final World world)
	{
		this.world = world;
		this.pack = new EntityGroup();
	}

	public MoaNest(final World world, final BlockPos pos)
	{
		this(world, pos, GeneUtil.getRandomSeed(world));
	}

	public MoaNest(final World world, final BlockPos pos, final int familyGeneticSeed)
	{
		this(world);

		this.pos = pos;

		this.hasInitialized = true;

		this.familyGeneticSeed = familyGeneticSeed;
	}

	public boolean isReplaceable(final World world, final BlockPos pos)
	{
		final IBlockState state = world.getBlockState(pos);
		final Block block = state.getBlock();

		return world.isAirBlock(pos) ||
				block.isLeaves(state, world, pos) ||
				block == BlocksAether.aether_grass ||
				block == BlocksAether.aether_dirt ||
				block.isReplaceable(world, pos) ||
				state.getMaterial() == Material.PLANTS ||
				state.getMaterial() == Material.VINE;
	}

	public void spawnMoaFamily(final World world, final int initialSize, final int optimalSize)
	{
		this.pack = new EntityGroup(EntityGroup.getNextID());
		this.pack.setOptimalSize(optimalSize);

		for (int amount = 0; amount < initialSize; amount++)
		{
			final EntityMoa moa = new EntityMoa(world, this);

			final int modifier = world.rand.nextBoolean() ? 1 : -1;
			final int scatterValue = (world.rand.nextInt(2) * modifier);

			moa.setPosition(this.pos.getX() + scatterValue, this.pos.getY() + 1, this.pos.getZ() + scatterValue);

			if (amount == 1)
			{
				moa.setGender(AnimalGender.MALE);
			}

			if (amount >= 2)
			{
				moa.setGrowingAge(-24000);
			}

			moa.setAnimalPack(this.pack);

			world.spawnEntity(moa);
		}
	}

	public EntityGroup getAnimalPack()
	{
		return this.pack;
	}

	public void writeToNBT(final NBTTagCompound nbt)
	{
		nbt.setTag("pos", NBTHelper.writeBlockPos(this.pos));

		nbt.setInteger("familyGeneticSeed", this.familyGeneticSeed);

		nbt.setBoolean("hasInitialized", this.hasInitialized);

		this.pack.writeToNBT(nbt);
	}

	public void readFromNBT(final NBTTagCompound nbt)
	{
		this.pos = NBTHelper.readBlockPos(nbt.getCompoundTag("pos"));

		this.familyGeneticSeed = nbt.getInteger("familyGeneticSeed");

		this.hasInitialized = nbt.getBoolean("hasInitialized");

		this.pack.readFromNBT(nbt);
	}
}
