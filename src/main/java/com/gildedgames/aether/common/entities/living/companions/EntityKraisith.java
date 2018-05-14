package com.gildedgames.aether.common.entities.living.companions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityKraisith extends EntityCombatCompanion
{
	public EntityKraisith(World worldIn)
	{
		super(worldIn);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this),
				(float) ((int) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));

		if (flag)
		{
			this.applyEnchantments(this, entity);

			if (entity instanceof EntityLivingBase)
			{
				EntityLivingBase living = (EntityLivingBase) entity;

				living.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20, 3));
			}
		}

		return flag;
	}

}
