package com.gildedgames.aether.common.entities.companions;

import com.gildedgames.aether.common.capabilities.player.PlayerAetherImpl;
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
	public void tickEffects(PlayerAetherImpl aePlayer)
	{
		// I'm useless!
	}

	@Override
	public void addEffects(PlayerAetherImpl aePlayer)
	{

	}

	@Override
	public void removeEffects(PlayerAetherImpl aePlayer)
	{

	}

	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));

		if (flag)
		{
			this.applyEnchantments(this, entity);

			if (entity instanceof EntityLivingBase)
			{
				EntityLivingBase living = (EntityLivingBase)entity;

				living.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20, 3));
			}
		}

		return flag;
	}

}
