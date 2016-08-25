package com.gildedgames.aether.common.tile_entities;

import com.gildedgames.aether.common.genes.util.SimpleGeneStorage;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.genes.moa.MoaGenePool;
import com.gildedgames.aether.common.entities.living.mounts.EntityMoa;
import com.gildedgames.aether.common.entities.util.MoaNest;
import com.gildedgames.aether.common.entities.util.AnimalGender;
import com.gildedgames.aether.common.tile_entities.util.TileEntitySynced;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityMoaEgg extends TileEntitySynced implements ITickable
{

	public int ticksExisted, secondsUntilHatch = -1;

	public MoaNest familyNest;

	public boolean playerPlaced;

	public AnimalGender gender = AnimalGender.FEMALE;

	private MoaGenePool genePool = new MoaGenePool(new SimpleGeneStorage());

	public TileEntityMoaEgg()
	{
		this.familyNest = new MoaNest(this.worldObj);
	}

	public MoaGenePool getGenePool()
	{
		return this.genePool;
	}

	@Override
	public void update()
	{
		if (this.worldObj.isRemote)
		{
			return;
		}

		if (this.secondsUntilHatch <= -1)
		{
			this.secondsUntilHatch = 100 + this.worldObj.rand.nextInt(100);
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
		boolean hasWarmth = false;
		boolean onWovenSticks = this.worldObj.getBlockState(this.getPos().add(0, -1, 0)) == BlocksAether.woven_skyroot_sticks.getDefaultState();

		for (BlockPos pos : BlockPos.getAllInBoxMutable(this.getPos().add(-1, -1, -1), this.getPos().add(1, 1, 1)))
		{
			if (this.worldObj.getLight(pos) >= 14)
			{
				hasWarmth = true;
				break;
			}
		}

		return hasWarmth && onWovenSticks;
	}

	public void hatchEgg()
	{
		EntityMoa babyMoa = new EntityMoa(this.worldObj, this.familyNest, this.genePool.getStorage().getFatherSeed(), this.genePool.getStorage().getMotherSeed());

		babyMoa.setGrowingAge(-24000);
		babyMoa.setPosition(this.getPos().getX() + 0.5D, this.getPos().getY(), this.getPos().getZ() + 0.5D);
		babyMoa.setGender(this.gender);
		babyMoa.setRaisedByPlayer(true);
		babyMoa.setAnimalPack(this.familyNest.getAnimalPack());

		this.worldObj.spawnEntityInWorld(babyMoa);

		this.worldObj.destroyBlock(this.getPos(), false);
	}

	public void setGender(AnimalGender gender)
	{
		this.gender = gender;
	}

	public AnimalGender getGender()
	{
		return this.gender;
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

		return nbt;
	}

}
