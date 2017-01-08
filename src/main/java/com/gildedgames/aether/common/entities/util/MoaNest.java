package com.gildedgames.aether.common.entities.util;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.genes.util.GeneUtil;
import com.gildedgames.aether.common.entities.living.mounts.EntityMoa;
import com.gildedgames.aether.common.util.io.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MoaNest
{

	public World world;

	public BlockPos pos;

	public int familyGeneticSeed;

	public boolean hasInitialized;

	protected EntityGroup pack;

	public MoaNest(World world)
	{
		this.world = world;
		this.pack = new EntityGroup();
	}

	public MoaNest(World world, BlockPos pos)
	{
		this(world, pos, GeneUtil.getRandomSeed(world));
	}

	public MoaNest(World world, BlockPos pos, int familyGeneticSeed)
	{
		this(world);

		this.pos = pos;

		this.hasInitialized = true;

		this.familyGeneticSeed = familyGeneticSeed;
	}

	public boolean isReplaceable(World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();

		return block == null ||
				world.isAirBlock(pos) ||
				block.isLeaves(state, world, pos) ||
				block == BlocksAether.aether_grass ||
				block == BlocksAether.aether_dirt ||
				block.isReplaceable(world, pos) ||
				state.getMaterial() == Material.PLANTS ||
				state.getMaterial() == Material.VINE;
	}

	public void spawnMoaFamily(World world, int initialSize, int optimalSize)
	{
		this.pack = new EntityGroup(EntityGroup.getNextID());
		this.pack.setOptimalSize(optimalSize);

		for (int amount = 0; amount < initialSize; amount++)
		{
			EntityMoa moa = new EntityMoa(world, this);

			int modifier = world.rand.nextBoolean() ? 1 : -1;
			int scatterValue = (world.rand.nextInt(2) * modifier);

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

			world.spawnEntityInWorld(moa);
		}
	}

	public EntityGroup getAnimalPack()
	{
		return this.pack;
	}

	public void writeToNBT(NBTTagCompound nbt)
	{
		nbt.setTag("pos", NBTHelper.writeBlockPos(this.pos));

		nbt.setInteger("familyGeneticSeed", this.familyGeneticSeed);

		nbt.setBoolean("hasInitialized", this.hasInitialized);

		this.pack.writeToNBT(nbt);
	}

	public void readFromNBT(NBTTagCompound nbt)
	{
		this.pos = NBTHelper.readBlockPos(nbt.getCompoundTag("pos"));

		this.familyGeneticSeed = nbt.getInteger("familyGeneticSeed");

		this.hasInitialized = nbt.getBoolean("hasInitialized");

		this.pack.readFromNBT(nbt);
	}
}
