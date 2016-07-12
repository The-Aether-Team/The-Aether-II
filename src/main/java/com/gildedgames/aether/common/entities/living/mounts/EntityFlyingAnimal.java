package com.gildedgames.aether.common.entities.living.mounts;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.living.EntityAetherAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public abstract class EntityFlyingAnimal extends EntityAetherAnimal
{
	private static final DataParameter<Boolean> SADDLED = new DataParameter<>(16, DataSerializers.BOOLEAN);

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

		this.dataManager.register(SADDLED, false);
	}

	@Override
	public void onUpdate()
	{
		this.fallDistance = 0;

		if (this.motionY < 0.0D)
		{
			this.motionY *= 0.6D;
		}

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
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, ItemStack stack, EnumHand hand)
	{
		EnumActionResult result = super.applyPlayerInteraction(player, vec, stack, hand);

		if (result == EnumActionResult.SUCCESS)
		{
			return EnumActionResult.SUCCESS;
		}

		if (!this.worldObj.isRemote)
		{
			if (this.isSaddled() && this.isBeingRidden())
			{
				player.startRiding(this, true);

				return EnumActionResult.SUCCESS;
			}
			else if (player.getActiveItemStack() != null && player.getActiveItemStack().getItem() == Items.SADDLE)
			{
				this.setIsSaddled(true);

				if (!player.capabilities.isCreativeMode)
				{
					player.getActiveItemStack().stackSize--;
				}
			}
		}

		return EnumActionResult.FAIL;
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
}
