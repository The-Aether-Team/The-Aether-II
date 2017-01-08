package com.gildedgames.aether.common.entities.projectiles;

import com.gildedgames.aether.common.entities.living.dungeon.labyrinth.EntityBattleGolem;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityBattleBomb extends EntityThrowable
{

	public EntityBattleBomb(World worldIn)
	{
		super(worldIn);
	}

	public EntityBattleBomb(World worldIn, EntityLivingBase throwerIn)
	{
		super(worldIn, throwerIn);
	}

	public EntityBattleBomb(World worldIn, double x, double y, double z)
	{
		super(worldIn, x, y, z);
	}

	protected void onImpact(RayTraceResult result)
	{
		if (result.entityHit != null)
		{
			if (result.entityHit instanceof EntityBattleGolem)
			{
				return;
			}

			result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 4.0F);
		}

		for (int j = 0; j < 8; ++j)
		{
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY, this.posZ,
					0.1D + (this.rand.nextDouble() * 0.1D), 0.1D + (this.rand.nextDouble() * 0.1D), 0.1D + (this.rand.nextDouble() * 0.1D));
		}

		this.playSound(SoundsAether.detonate, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);

		if (!this.worldObj.isRemote)
		{
			this.setDead();
		}
	}

}
