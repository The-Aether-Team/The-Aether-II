package com.gildedgames.aether.common.entities.genes.moa;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.entities.animals.EntityMoa;
import com.gildedgames.aether.common.entities.genes.AnimalGender;
import com.gildedgames.aether.common.entities.genes.GeneUtil;
import com.gildedgames.aether.common.entities.util.groups.EntityGroup;
import com.gildedgames.orbis.lib.util.mc.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
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
		final BlockState state = world.getBlockState(pos);
		final Block block = state.getBlock();
		final Material material = state.getMaterial();

		return material.isReplaceable() || material == Material.LEAVES || material == Material.PLANTS ||
				block == BlocksAether.aether_grass ||
				block == BlocksAether.aether_dirt;
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

			world.addEntity(moa);
		}
	}

	public EntityGroup getAnimalPack()
	{
		return this.pack;
	}

	public void writeToNBT(final CompoundNBT nbt)
	{
		nbt.put("pos", NBTHelper.writeBlockPos(this.pos));

		nbt.putInt("familyGeneticSeed", this.familyGeneticSeed);

		nbt.putBoolean("hasInitialized", this.hasInitialized);

		this.pack.writeToNBT(nbt);
	}

	public void readFromNBT(final CompoundNBT nbt)
	{
		this.pos = NBTHelper.readBlockPos(nbt.getCompound("pos"));

		this.familyGeneticSeed = nbt.getInt("familyGeneticSeed");

		this.hasInitialized = nbt.getBoolean("hasInitialized");

		this.pack.readFromNBT(nbt);
	}
}
