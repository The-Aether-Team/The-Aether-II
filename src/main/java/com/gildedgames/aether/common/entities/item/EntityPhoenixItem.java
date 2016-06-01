package com.gildedgames.aether.common.entities.item;

import com.gildedgames.aether.common.items.properties.IPhoenixChillable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityPhoenixItem extends EntityItem
{
	private int ticksInWater = 0;

	public EntityPhoenixItem(World world)
	{
		super(world);
	}

	public EntityPhoenixItem(EntityItem entity)
	{
		super(entity.worldObj, entity.posX, entity.posY, entity.posZ);

		this.motionX = entity.motionX;
		this.motionY = entity.motionY;
		this.motionZ = entity.motionZ;

		// TODO: reflection(?)
		this.setPickupDelay(40);

		this.setEntityItemStack(entity.getEntityItem());
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (!this.worldObj.isRemote)
		{
			if (this.isInWater())
			{
				this.ticksInWater++;
			}
			else
			{
				this.ticksInWater = 0;
			}

			boolean valid = false;

			if (this.getEntityItem().getItem() instanceof IPhoenixChillable)
			{
				IPhoenixChillable chillable = (IPhoenixChillable) this.getEntityItem().getItem();

				if (chillable.canChillItemstack(this.getEntityItem()))
				{
					if (this.ticksInWater++ > 200)
					{
						ItemStack stack = chillable.getChilledItemstack(this.getEntityItem());

						EntityItem item = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ);
						item.motionX = this.motionX;
						item.motionY = this.motionY;
						item.motionZ = this.motionZ;

						item.setEntityItemStack(stack);

						this.worldObj.spawnEntityInWorld(item);
					}
					else
					{
						valid = true;
					}
				}
			}

			if (!valid)
			{
				this.setDead();
			}
		}
		else
		{
			this.onClientUpdate();
		}
	}

	@SideOnly(Side.CLIENT)
	public void onClientUpdate()
	{
		if (this.worldObj.isRemote)
		{
			boolean isFreezing = this.isInWater();

			int count = isFreezing ? 3 : (this.worldObj.rand.nextInt(100) > 85 ? 1 : 0);

			for (int i = 0; i < count; i++)
			{
				double x = this.posX + (this.worldObj.rand.nextDouble() * 0.5D) - 0.25D;
				double y = this.posY + (this.worldObj.rand.nextDouble() * 0.3D);
				double z = this.posZ + (this.worldObj.rand.nextDouble() * 0.5D) - 0.25D;

				this.worldObj.spawnParticle(isFreezing ? EnumParticleTypes.WATER_BUBBLE : EnumParticleTypes.FLAME, x, y, z, 0D, 0.01D, 0D);
			}

			if (isFreezing)
			{
				this.worldObj.playSound(this.posX, this.posY, this.posZ, "random.fizz", 0.2f, 0.2f + (this.worldObj.rand.nextFloat() * 1.7f), false);
			}
		}
	}

	public boolean isEntityInvulnerable(DamageSource source)
	{
		if (source.isFireDamage())
		{
			return true;
		}

		return super.isEntityInvulnerable(source);
	}

	@SideOnly(Side.CLIENT)
	public boolean canRenderOnFire()
	{
		return false;
	}

	public int getTicksInWater()
	{
		return this.ticksInWater;
	}
}
