package com.gildedgames.aether.common.entities.companions;

import com.gildedgames.aether.common.entities.ai.companion.EntityAICompanionOwnerHurt;
import com.gildedgames.aether.common.entities.ai.companion.EntityAICompanionTargetEnemy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class EntityCombatCompanion extends EntityCompanion
{

	public EntityCombatCompanion(World worldIn)
	{
		super(worldIn);
	}

	@Override
	protected void initEntityAI()
	{
		super.initEntityAI();

		EntityAIBase attack = new EntityAIAttackMelee(this, 0.7D, true);

		attack.setMutexBits(1);

		this.tasks.addTask(0, attack);

		this.targetTasks.addTask(0, new EntityAICompanionOwnerHurt(this));
		this.targetTasks.addTask(1, new EntityAICompanionTargetEnemy(this));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);

		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(10.0D);
	}

}
