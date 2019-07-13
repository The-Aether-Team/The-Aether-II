package com.gildedgames.aether.common.entities.projectiles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityDaggerfrostSnowball extends SnowballEntity
{

	public EntityDaggerfrostSnowball(World world)
	{
		super(world);
	}

	public EntityDaggerfrostSnowball(World world, LivingEntity thrower)
	{
		super(world, thrower);
	}

	public EntityDaggerfrostSnowball(World world, double x, double y, double z)
	{
		super(world, x, y, z);
	}

	@Override
	protected void onImpact(RayTraceResult result)
	{
		if (result.getType() == RayTraceResult.Type.ENTITY)
		{
			Entity entity = ((EntityRayTraceResult) result).getEntity();
			int damage = entity instanceof BlazeEntity ? 3 : 0;

			entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float) damage);
		}

		if (!this.world.isRemote)
		{
			this.world.setEntityState(this, (byte) 3);

			this.remove();
		}
	}

}
