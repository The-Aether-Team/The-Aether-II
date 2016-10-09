package com.gildedgames.aether.common.entities.util;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.living.mounts.EntityMoa;
import com.gildedgames.aether.common.genes.util.GeneUtil;
import com.gildedgames.util.core.nbt.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class MoaNest
{
	
	public World world;

	public BlockPos pos;

	public int nestSize, familyGeneticSeed;

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

	public void generate(World world, Random rand, int nestSize, IBlockState nestBlock)
	{
		this.nestSize = nestSize;

		if (world.getBlockState(this.pos) != BlocksAether.aether_dirt && world.getBlockState(this.pos) != BlocksAether.aether_grass.getDefaultState())
		{
			return;
		}

		for (BlockPos pos : BlockPos.getAllInBoxMutable(this.pos.add(-this.nestSize, 0, -this.nestSize), this.pos.add(this.nestSize, 0, this.nestSize)))
		{
			if (this.isReplaceable(world, pos))
			{
				world.setBlockState(pos, nestBlock);
			}
		}

		for (BlockPos pos : BlockPos.getAllInBoxMutable(this.pos.add(-this.nestSize, 1, -this.nestSize - 1), this.pos.add(this.nestSize, 1, this.nestSize + 1)))
		{
			if (this.isReplaceable(world, pos))
			{
				world.setBlockState(pos, nestBlock);
			}
		}

		for (BlockPos pos : BlockPos.getAllInBoxMutable(this.pos.add(-this.nestSize - 1, 1, -this.nestSize), this.pos.add(this.nestSize + 1, 1, this.nestSize)))
		{
			if (this.isReplaceable(world, pos))
			{
				world.setBlockState(pos, nestBlock);
			}
		}

		for (BlockPos pos : BlockPos.getAllInBoxMutable(this.pos.add(-this.nestSize, 1, -this.nestSize), this.pos.add(this.nestSize, 1, this.nestSize)))
		{
			if (world.getBlockState(pos) == nestBlock)
			{
				world.setBlockToAir(pos);
			}
		}
		
		this.spawnMoaFamily(world, 2 + world.rand.nextInt(nestSize + 1));
	}
	
	public boolean isReplaceable(World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		
        return block == null ||
				world.isAirBlock(pos) ||
				block.isLeaves(state, world, pos) ||
				state == BlocksAether.aether_grass.getDefaultState() ||
				state == BlocksAether.aether_dirt.getDefaultState() ||
				block.isReplaceable(world, pos) ||
				block.getMaterial(state) == Material.PLANTS ||
				block.getMaterial(state) == Material.VINE;
	}
	
	private void spawnMoaFamily(World world, int familySize)
	{
		this.pack = new EntityGroup(EntityGroup.getNextID());
		this.pack.setOptimalSize(5);
		
		boolean hasExtraChild = world.rand.nextBoolean();
		
		if (hasExtraChild)
		{
			familySize++;
		}

		for (int amount = 0; amount < familySize; amount++)
		{
			EntityMoa moa = new EntityMoa(world, this);
			
			int modifier = world.rand.nextBoolean() ? 1 : -1;
			int scatterValue = (world.rand.nextInt(2) * modifier);
			
			moa.setPosition(this.pos.getX() + scatterValue, this.pos.getY() + 1, this.pos.getZ() + scatterValue);
		
			if (amount == 1)
			{
				moa.setGender(AnimalGender.MALE);
			}
			
			if (amount == 2)
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
		nbt.setTag("pos", NBTHelper.serializeBlockPos(this.pos));
		
		nbt.setInteger("familyGeneticSeed", this.familyGeneticSeed);
		
		nbt.setBoolean("hasInitialized", this.hasInitialized);
		
		this.pack.writeToNBT(nbt);
	}

	public void readFromNBT(NBTTagCompound nbt)
	{
		this.pos =  NBTHelper.readBlockPos(nbt.getCompoundTag("pos"));
		
		this.familyGeneticSeed = nbt.getInteger("familyGeneticSeed");
		
		this.hasInitialized = nbt.getBoolean("hasInitialized");
		
		this.pack.readFromNBT(nbt);
	}
}
