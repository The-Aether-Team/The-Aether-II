package com.gildedgames.aether.common.entities.living.mounts;

import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityFlyingAnimal extends EntityAnimal
{
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

		float aimingForFold = 1.0F;

		if (this.onGround)
		{
			this.wingAngle *= 0.8F;

			aimingForFold = 0.1F;
		}

		this.wingAngle = this.wingFold * (float) Math.sin(this.ticksExisted / 31.83098862F);
		this.wingFold += (aimingForFold - this.wingFold) / 5F;

		super.onUpdate();
	}

	@Override
	public boolean interact(EntityPlayer player)
	{
		if (super.interact(player))
		{
			return true;
		}

		if (this.isSaddled() && !this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == player))
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
	protected Item getDropItem()
	{
		return this.isBurning() ? Items.cooked_porkchop : Items.porkchop;
	}

	@Override
	protected void dropFewItems(boolean p_70628_1_, int looting)
	{
		int amount = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + looting);

		for (int count = 0; count < amount; ++count)
		{
			this.dropItem(this.getDropItem(), 1);
		}

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
