package com.gildedgames.aether.common.entities.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityDaggerfrostSnowball extends EntitySnowball
{

	public EntityDaggerfrostSnowball(World world)
	{
		super(world);
	}

	public EntityDaggerfrostSnowball(World world, EntityLivingBase thrower)
	{
		super(world, thrower);
	}

	public EntityDaggerfrostSnowball(World world, double x, double y, double z)
	{
		super(world, x, y, z);
	}

	protected void onImpact(MovingObjectPosition pos)
	{
		if (pos.entityHit != null)
		{
			byte b0 = 2;

			if (pos.entityHit instanceof EntityBlaze)
			{
				b0 += 3;
			}

			pos.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float) b0);
		}

		for (int i = 0; i < 8; ++i)
		{
			this.worldObj.spawnParticle(EnumParticleTypes.SNOWBALL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
		}

		if (!this.worldObj.isRemote)
		{
			this.setDead();
		}
	}

}
