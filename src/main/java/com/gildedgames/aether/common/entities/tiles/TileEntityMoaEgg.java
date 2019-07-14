package com.gildedgames.aether.common.entities.tiles;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.entities.TileEntityTypesAether;
import com.gildedgames.aether.common.entities.animals.EntityMoa;
import com.gildedgames.aether.common.entities.genes.AnimalGender;
import com.gildedgames.aether.common.entities.genes.SimpleGeneStorage;
import com.gildedgames.aether.common.entities.genes.moa.MoaGenePool;
import com.gildedgames.aether.common.entities.genes.moa.MoaNest;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class TileEntityMoaEgg extends TileEntitySynced implements ITickableTileEntity
{

	public int ticksExisted, secondsUntilHatch = -1;

	public MoaNest familyNest;

	public boolean playerPlaced;

	public AnimalGender gender = AnimalGender.FEMALE;

	private final MoaGenePool genePool = new MoaGenePool(new SimpleGeneStorage());

	public TileEntityMoaEgg()
	{
		super(TileEntityTypesAether.MOA_EGG);

		this.familyNest = new MoaNest(this.world);
	}

	public MoaGenePool getGenePool()
	{
		return this.genePool;
	}

	@Override
	public void tick()
	{
		if (this.world.isRemote())
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

		this.world.addEntity(babyMoa);

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
	public void read(CompoundNBT nbt)
	{
		super.read(nbt);

		this.genePool.read(nbt.getCompound("genePool"));

		this.secondsUntilHatch = nbt.getInt("secondsUntilHatch");

		this.gender = AnimalGender.get(nbt.getString("creatureGender"));

		this.familyNest.readFromNBT(nbt);

		this.playerPlaced = nbt.getBoolean("playerPlaced");
	}

	@Override
	public CompoundNBT write(CompoundNBT nbt)
	{
		super.write(nbt);

		CompoundNBT genePoolTag = new CompoundNBT();

		this.genePool.write(genePoolTag);

		nbt.put("genePool", genePoolTag);

		nbt.putInt("secondsUntilHatch", this.secondsUntilHatch);

		if (this.gender != null)
		{
			nbt.putString("creatureGender", this.gender.name());
		}

		this.familyNest.writeToNBT(nbt);

		nbt.putBoolean("playerPlaced", this.playerPlaced);

		return nbt;
	}

}
