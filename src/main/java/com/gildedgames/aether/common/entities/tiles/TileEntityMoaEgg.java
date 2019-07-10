package com.gildedgames.aether.common.entities.tiles;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.entities.animals.EntityMoa;
import com.gildedgames.aether.common.entities.genes.AnimalGender;
import com.gildedgames.aether.common.entities.genes.SimpleGeneStorage;
import com.gildedgames.aether.common.entities.genes.moa.MoaGenePool;
import com.gildedgames.aether.common.entities.genes.moa.MoaNest;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class TileEntityMoaEgg extends TileEntitySynced implements ITickable
{

	public int ticksExisted, secondsUntilHatch = -1;

	public MoaNest familyNest;

	public boolean playerPlaced;

	public AnimalGender gender = AnimalGender.FEMALE;

	private final MoaGenePool genePool = new MoaGenePool(new SimpleGeneStorage());

	public TileEntityMoaEgg()
	{
		this.familyNest = new MoaNest(this.world);
	}

	public MoaGenePool getGenePool()
	{
		return this.genePool;
	}

	@Override
	public void update()
	{
		if (this.world.isRemote)
		{
			return;
		}

		if (this.playerPlaced)
		{
			return;
		}

		if (this.secondsUntilHatch <= -1)
		{
			this.secondsUntilHatch = 100 + this.world.rand.nextInt(100);
		}

		this.ticksExisted++;

		if (this.ticksExisted % 20 == 0)
		{
			if (this.secondsUntilHatch > 0)
			{
				this.secondsUntilHatch--;
			}
			else if (this.hatchConditionsMet())
			{
				this.hatchEgg();
			}
		}
	}

	public boolean hatchConditionsMet()
	{
		boolean atMaxFamilySize = this.familyNest.getAnimalPack() != null
				&& this.familyNest.getAnimalPack().getSize() >= this.familyNest.getAnimalPack().getOptimalSize();

		return this.world.getBlockState(this.getPos().add(0, -1, 0)) == BlocksAether.woven_sticks.getDefaultState() && !atMaxFamilySize;
	}

	public void hatchEgg()
	{
		EntityMoa babyMoa = new EntityMoa(this.world, this.familyNest, this.genePool.getStorage().getFatherSeed(), this.genePool.getStorage().getMotherSeed());

		babyMoa.setGrowingAge(-24000);
		babyMoa.setPosition(this.getPos().getX() + 0.5D, this.getPos().getY(), this.getPos().getZ() + 0.5D);
		babyMoa.setGender(this.gender);
		babyMoa.setAnimalPack(this.familyNest.getAnimalPack());

		this.world.spawnEntity(babyMoa);

		this.world.destroyBlock(this.getPos(), false);
	}

	public AnimalGender getGender()
	{
		return this.gender;
	}

	public void setGender(AnimalGender gender)
	{
		this.gender = gender;
	}

	public void setFamilyNest(MoaNest familyNest)
	{
		this.familyNest = familyNest;
	}

	public void setPlayerPlaced()
	{
		this.playerPlaced = true;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);

		this.genePool.read(nbt.getCompoundTag("genePool"));

		this.secondsUntilHatch = nbt.getInteger("secondsUntilHatch");

		this.gender = AnimalGender.get(nbt.getString("creatureGender"));

		this.familyNest.readFromNBT(nbt);

		this.playerPlaced = nbt.getBoolean("playerPlaced");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);

		NBTTagCompound genePoolTag = new NBTTagCompound();

		this.genePool.write(genePoolTag);

		nbt.setTag("genePool", genePoolTag);

		nbt.setInteger("secondsUntilHatch", this.secondsUntilHatch);

		if (this.gender != null)
		{
			nbt.setString("creatureGender", this.gender.name());
		}

		this.familyNest.writeToNBT(nbt);

		nbt.setBoolean("playerPlaced", this.playerPlaced);

		return nbt;
	}

}
