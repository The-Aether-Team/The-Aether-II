package com.gildedgames.aether.common.entities.util;

import com.gildedgames.aether.api.entity.genes.IGenePool;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public abstract class EntityGeneticAnimal<T extends IGenePool> extends EntityAnimal
{

	private static final DataParameter<Integer> SEED = EntityDataManager.createKey(EntityGeneticAnimal.class, DataSerializers.VARINT);

	private static final DataParameter<Integer> FATHER_SEED = EntityDataManager.createKey(EntityGeneticAnimal.class, DataSerializers.VARINT);

	private static final DataParameter<Integer> MOTHER_SEED = EntityDataManager.createKey(EntityGeneticAnimal.class, DataSerializers.VARINT);

	private static final DataParameter<Boolean> SHOULD_RETRANSFORM = EntityDataManager.createKey(EntityGeneticAnimal.class, DataSerializers.BOOLEAN);

	private T genePool;

	public EntityGeneticAnimal(World world)
	{
		super(world);

		this.genePool = this.createNewGenePool();
	}

	public abstract T createNewGenePool();

	public T getGenePool()
	{
		return this.genePool;
	}

	@Override
	public void entityInit()
	{
		super.entityInit();

		this.dataManager.register(SEED, 0);
		this.dataManager.register(FATHER_SEED, 0);
		this.dataManager.register(MOTHER_SEED, 0);
		this.dataManager.register(SHOULD_RETRANSFORM, false);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (this.shouldRetransform() && this.world.isRemote)
		{
			if (this.getSeed() == this.getFatherSeed() && this.getSeed() == this.getMotherSeed())
			{
				this.getGenePool().transformFromSeed(this.getSeed());
			}
			else
			{
				this.getGenePool().transformFromParents(this.getSeed(), this.getFatherSeed(), this.getMotherSeed());
			}

			this.setShouldRetransform(false);
		}
	}

	public int getSeed()
	{
		return this.getDataManager().get(EntityGeneticAnimal.SEED);
	}

	protected void setSeed(int seed)
	{
		this.getDataManager().set(EntityGeneticAnimal.SEED, seed);
	}

	public int getFatherSeed()
	{
		return this.getDataManager().get(EntityGeneticAnimal.FATHER_SEED);
	}

	protected void setFatherSeed(int seed)
	{
		this.getDataManager().set(EntityGeneticAnimal.FATHER_SEED, seed);
	}

	public int getMotherSeed()
	{
		return this.getDataManager().get(EntityGeneticAnimal.MOTHER_SEED);
	}

	protected void setMotherSeed(int seed)
	{
		this.getDataManager().set(EntityGeneticAnimal.MOTHER_SEED, seed);
	}

	protected boolean shouldRetransform()
	{
		return this.getDataManager().get(EntityGeneticAnimal.SHOULD_RETRANSFORM);
	}

	protected void setShouldRetransform(boolean flag)
	{
		this.getDataManager().set(EntityGeneticAnimal.SHOULD_RETRANSFORM, flag);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		super.writeEntityToNBT(tag);

		tag.setInteger("seed", this.getSeed());
		tag.setInteger("fatherSeed", this.getFatherSeed());
		tag.setInteger("motherSeed", this.getMotherSeed());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tag)
	{
		super.readEntityFromNBT(tag);

		int seed = tag.getInteger("seed");
		int fatherSeed = tag.getInteger("fatherSeed");
		int motherSeed = tag.getInteger("motherSeed");

		if (seed == fatherSeed && seed == motherSeed)
		{
			this.getGenePool().transformFromSeed(seed);
		}
		else
		{
			this.getGenePool().transformFromParents(seed, fatherSeed, motherSeed);
		}
	}

}
