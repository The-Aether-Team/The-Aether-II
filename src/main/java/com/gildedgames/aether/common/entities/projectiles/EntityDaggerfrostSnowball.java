package com.gildedgames.aether.common.entities.projectiles;

import com.gildedgames.aether.common.entities.EntityTypesAether;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityDaggerfrostSnowball extends ProjectileItemEntity
{

	public EntityDaggerfrostSnowball(EntityType<? extends EntityDaggerfrostSnowball> p_i50159_1_, World p_i50159_2_)
	{
		super(p_i50159_1_, p_i50159_2_);
	}

	public EntityDaggerfrostSnowball(World worldIn, LivingEntity throwerIn)
	{
		super(EntityTypesAether.SNOWBALL, throwerIn, worldIn);
	}

	public EntityDaggerfrostSnowball(World worldIn, double x, double y, double z)
	{
		super(EntityTypesAether.SNOWBALL, x, y, z, worldIn);
	}

	@Override
	protected Item func_213885_i()
	{
		return Items.SNOWBALL;
	}

	@OnlyIn(Dist.CLIENT)
	private IParticleData getParticle()
	{
		ItemStack stack = this.func_213882_k();
		return stack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemParticleData(ParticleTypes.ITEM, stack);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void handleStatusUpdate(byte id)
	{
		if (id == 3)
		{
			IParticleData particle = this.getParticle();

			for (int i = 0; i < 8; ++i)
			{
				this.world.addParticle(particle, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			}
		}

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
