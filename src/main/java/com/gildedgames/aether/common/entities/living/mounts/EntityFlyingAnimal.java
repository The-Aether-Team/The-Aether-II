package com.gildedgames.aether.common.entities.living.mounts;

import com.gildedgames.aether.api.entity.IMount;
import com.gildedgames.aether.api.entity.IMountProcessor;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.living.passive.EntityAetherAnimal;
import com.gildedgames.aether.common.entities.util.mounts.FlyingMount;
import com.gildedgames.aether.common.entities.util.mounts.IFlyingMountData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public abstract class EntityFlyingAnimal extends EntityAetherAnimal implements IMount, IFlyingMountData
{

	private static final DataParameter<Boolean> SADDLED = EntityDataManager.createKey(EntityFlyingAnimal.class, DataSerializers.BOOLEAN);

	private static final DataParameter<Float> AIRBORNE_TIME = EntityDataManager.createKey(EntityFlyingAnimal.class, DataSerializers.FLOAT);

	private IMountProcessor mountProcessor = new FlyingMount(this);

	@SideOnly(Side.CLIENT)
	public float wingFold, wingAngle;

	public EntityFlyingAnimal(World world)
	{
		super(world);

		this.spawnableBlock = BlocksAether.aether_grass;
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		this.dataManager.register(EntityFlyingAnimal.SADDLED, false);
		this.dataManager.register(EntityFlyingAnimal.AIRBORNE_TIME, this.maxAirborneTime());
	}

	@Override
	public void onUpdate()
	{
		this.fallDistance = 0;

		boolean riderSneaking =
				!this.getPassengers().isEmpty() && this.getPassengers().get(0) != null && this.getPassengers().get(0).isSneaking();

		if (this.motionY < 0.0D && !riderSneaking)
		{
			this.motionY *= 0.6D;
		}

		if (this.world.isRemote)
		{
			float aimingForFold = 1.0F;

			if (this.onGround)
			{
				this.wingAngle *= 0.8F;

				aimingForFold = 0.1F;
			}

			this.wingAngle = this.wingFold * (float) Math.sin(this.ticksExisted / 31.83098862F);
			this.wingFold += (aimingForFold - this.wingFold) / 5F;
		}

		super.onUpdate();
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack)
	{
		if (!super.processInteract(player, hand, stack))
		{
			if (!player.world.isRemote)
			{
				if (!this.isSaddled() && stack != null && stack.getItem() == Items.SADDLE && !this.isChild())
				{
					this.setIsSaddled(true);

					if (!player.capabilities.isCreativeMode)
					{
						stack.stackSize--;
					}

					return true;
				}
				else
				{
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);

		compound.setBoolean("IsSaddled", this.isSaddled());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);

		this.setIsSaddled(compound.getBoolean("IsSaddled"));
	}

	@Override
	protected void dropFewItems(boolean p_70628_1_, int looting)
	{
		super.dropFewItems(p_70628_1_, looting);

		if (this.isSaddled())
		{
			this.dropItem(Items.SADDLE, 1);
		}
	}

	public boolean isSaddled()
	{
		return this.dataManager.get(SADDLED);
	}

	public void setIsSaddled(boolean isSaddled)
	{
		this.dataManager.set(SADDLED, isSaddled);
	}

	@Override
	public IMountProcessor getMountProcessor()
	{
		return this.mountProcessor;
	}

	@Override
	public void resetRemainingAirborneTime()
	{
		this.dataManager.set(EntityFlyingAnimal.AIRBORNE_TIME, this.maxAirborneTime());
	}

	@Override
	public float getRemainingAirborneTime()
	{
		return this.dataManager.get(EntityFlyingAnimal.AIRBORNE_TIME);
	}

	@Override
	public void setRemainingAirborneTime(float set)
	{
		this.dataManager.set(EntityFlyingAnimal.AIRBORNE_TIME, set);
	}

	@Override
	public void addRemainingAirborneTime(float add)
	{
		this.setRemainingAirborneTime(this.getRemainingAirborneTime() + add);
	}

	@Override
	public boolean canBeMounted()
	{
		return this.isSaddled();
	}

	@Override
	public boolean canProcessMountInteraction(EntityPlayer rider, ItemStack stack)
	{
		return !this.isBreedingItem(stack) && (stack == null || stack.getItem() != Items.LEAD);
	}

	public abstract float maxAirborneTime();

}
