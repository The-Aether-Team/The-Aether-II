package com.gildedgames.aether.common.entities.projectiles;

import com.gildedgames.aether.common.util.selectors.RandomCraftedItemSelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySentryVaultbox extends EntityThrowable
{
	private boolean hasHit;

	private double hitX, hitY, hitZ;

	private int partyTimeNeeded = 100, partyTime, presentCount = 10;

	private RandomCraftedItemSelector itemSelector = new RandomCraftedItemSelector();

	public EntitySentryVaultbox(World world)
	{
		super(world);
	}

	public EntitySentryVaultbox(World world, EntityLivingBase thrower)
	{
		super(world, thrower);
	}

	public EntitySentryVaultbox(World world, double x, double y, double z)
	{
		super(world, x, y, z);
	}

	@Override
	public void onUpdate()
	{
		if (this.hasHit)
		{
			this.setPosition(this.hitX, this.hitY, this.hitZ);
			if (this.partyTime < this.partyTimeNeeded)
			{
				if (this.partyTime % (this.partyTimeNeeded / this.presentCount) == 0)
				{
					double motX = (this.rand.nextBoolean() ? -1 : 1) * this.rand.nextDouble();
					double motY = 1;
					double motZ = (this.rand.nextBoolean() ? -1 : 1) * this.rand.nextDouble();

					if (!this.worldObj.isRemote)
					{
						ItemStack reward = this.itemSelector.create(this.rand);

						Entity entity = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, reward);

						entity.motionX = motX;
						entity.motionY = motY;
						entity.motionZ = motZ;

						this.worldObj.spawnEntityInWorld(entity);
					}

					for (int sparkCount = 1; sparkCount <= 10; sparkCount++)
					{
						motX = (this.rand.nextBoolean() ? -1 : 1) * this.rand.nextDouble();
						motY = this.rand.nextDouble();
						motZ = (this.rand.nextBoolean() ? -1 : 1) * this.rand.nextDouble();

						this.worldObj.spawnParticle(EnumParticleTypes.SPELL_MOB_AMBIENT, this.posX, this.posY, this.posZ, motX, motY, motZ);
						this.worldObj.spawnParticle(EnumParticleTypes.CLOUD, this.posX, this.posY, this.posZ, motX / 6, motY / 6, motZ / 6);
					}
				}

				this.partyTime++;
			}
			else
			{
				for (int sparkCount = 1; sparkCount <= 10; sparkCount++)
				{
					double motX = (this.rand.nextBoolean() ? -1 : 1) * this.rand.nextDouble();
					double motY = this.rand.nextDouble();
					double motZ = (this.rand.nextBoolean() ? -1 : 1) * this.rand.nextDouble();

					this.worldObj.spawnParticle(EnumParticleTypes.SPELL_MOB_AMBIENT, this.posX, this.posY, this.posZ, motX, motY, motZ);
					this.worldObj.spawnParticle(EnumParticleTypes.CLOUD, this.posX, this.posY, this.posZ, motX / 6, motY / 6, motZ / 6);
				}

				this.setDead();
			}
		}
		else
		{
			super.onUpdate();
		}
	}

	protected void onImpact(RayTraceResult ray)
	{
		if (!this.hasHit)
		{
			this.hasHit = true;

			this.hitX = this.posX;
			this.hitY = this.posY;
			this.hitZ = this.posZ;
		}
		else
		{
			return;
		}

		if (ray.entityHit != null)
		{
			byte b0 = 0;

			if (ray.entityHit instanceof EntityBlaze)
			{
				b0 = 3;
			}

			ray.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float) b0);
		}
	}
}
