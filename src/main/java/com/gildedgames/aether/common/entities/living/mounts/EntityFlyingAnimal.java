package com.gildedgames.aether.common.entities.living.mounts;

import com.gildedgames.aether.common.entities.living.EntityAetherAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class EntityFlyingAnimal extends EntityAetherAnimal
{
	@SideOnly(Side.CLIENT)
	public float wingFold, wingAngle;

	public EntityFlyingAnimal(World world)
	{
		super(world);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		this.dataWatcher.addObject(16, (byte) 0);
	}

	@Override
	public void onUpdate()
	{
		this.fallDistance = 0;

		this.motionY *= 0.6D;

		if (this.worldObj.isRemote)
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
	public boolean interact(EntityPlayer player)
	{
		if (super.interact(player))
		{
			return true;
		}

		if (!this.worldObj.isRemote)
		{
			if (this.isSaddled() && this.riddenByEntity == null)
			{
				player.mountEntity(this);

				return true;
			}
			else if (player.getHeldItem() != null && player.getHeldItem().getItem() == Items.saddle)
			{
				this.setIsSaddled(true);

				if (!player.capabilities.isCreativeMode)
				{
					player.getHeldItem().stackSize--;
				}
			}
		}

		return false;
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

		this.dropSaddle();
	}

	protected void dropSaddle()
	{
		if (this.isSaddled())
		{
			this.dropItem(Items.saddle, 1);
		}
	}

	public boolean isSaddled()
	{
		return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	public void setIsSaddled(boolean isSaddled)
	{
		this.dataWatcher.updateObject(16, (byte) (isSaddled ? 1 : 0));
	}
}
