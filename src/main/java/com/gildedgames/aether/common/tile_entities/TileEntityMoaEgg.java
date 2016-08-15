package com.gildedgames.aether.common.tile_entities;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.moa.EntityMoa;
import com.gildedgames.aether.common.entities.moa.MoaGenetics;
import com.gildedgames.aether.common.entities.moa.MoaNest;
import com.gildedgames.aether.common.entities.util.AnimalGender;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class TileEntityMoaEgg extends TileEntity implements ITickable
{

	public int ticksExisted, secondsUntilHatch = -1, motherGeneticSeed, fatherGeneticSeed;

	public MoaGenetics genetics;

	public MoaNest familyNest;

	public boolean playerPlaced;

	public AnimalGender gender = AnimalGender.FEMALE;

	public TileEntityMoaEgg()
	{
		this.familyNest = new MoaNest(this.worldObj);
	}

	@Override
	public void update()
	{
		if (this.secondsUntilHatch <= -1)
		{
			this.secondsUntilHatch = 100 + this.worldObj.rand.nextInt(100);
		}
		
		if (this.genetics == null)
		{
			this.secondsUntilHatch = 100 + this.worldObj.rand.nextInt(100);
			this.genetics = MoaGenetics.getMixedResult(this.worldObj, this.motherGeneticSeed, this.fatherGeneticSeed);
		}

		if (this.worldObj.isRemote)
		{
			return;
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

		return this.genetics != null && hasWarmth && onWovenSticks;
	}

	public void hatchEgg()
	{
		EntityMoa babyMoa = new EntityMoa(this.worldObj, this.familyNest, this.motherGeneticSeed, this.fatherGeneticSeed);
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

	public void setMotherSeed(int seed)
	{
		this.motherGeneticSeed = seed;

		if (!this.worldObj.isRemote)
		{
			this.sendUpdates();
		}

		this.genetics = MoaGenetics.getMixedResult(this.worldObj, this.motherGeneticSeed, this.fatherGeneticSeed);
	}

	public void setFatherSeed(int seed)
	{
		this.fatherGeneticSeed = seed;

		if (!this.worldObj.isRemote)
		{
			this.sendUpdates();
		}

		this.genetics = MoaGenetics.getMixedResult(this.worldObj, this.motherGeneticSeed, this.fatherGeneticSeed);
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

		this.secondsUntilHatch = nbt.getInteger("secondsUntilHatch");
		this.motherGeneticSeed = nbt.getInteger("motherGeneticSeed");
		this.fatherGeneticSeed = nbt.getInteger("fatherGeneticSeed");

		if (this.worldObj != null)
		{
			this.genetics = MoaGenetics.getMixedResult(this.worldObj, this.motherGeneticSeed, this.fatherGeneticSeed);
		}

		this.gender = AnimalGender.get(nbt.getString("creatureGender"));

		this.familyNest.readFromNBT(nbt);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);

		nbt.setInteger("secondsUntilHatch", this.secondsUntilHatch);
		nbt.setInteger("motherGeneticSeed", this.motherGeneticSeed);
		nbt.setInteger("fatherGeneticSeed", this.fatherGeneticSeed);

		if (this.gender != null)
		{
			nbt.setString("creatureGender", this.gender.name());
		}

		this.familyNest.writeToNBT(nbt);

		return nbt;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		NBTTagCompound compound = new NBTTagCompound();
		this.writeToNBT(compound);

		return new SPacketUpdateTileEntity(this.pos, 1, compound);
	}

	@Override
	public void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity packet)
	{
		this.readFromNBT(packet.getNbtCompound());
	}

	private void sendUpdates()
	{
		IBlockState state = this.worldObj.getBlockState(this.pos);

		this.worldObj.notifyBlockUpdate(this.pos, state, state, 3);

		this.markDirty();
	}

}
