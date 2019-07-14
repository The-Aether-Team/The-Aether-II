package com.gildedgames.aether.common.entities.companions;

import com.gildedgames.aether.common.entities.ai.companion.EntityAICompanionOwnerHurt;
import com.gildedgames.aether.common.entities.ai.companion.EntityAICompanionTargetEnemy;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.World;

import java.util.EnumSet;

public abstract class EntityCombatCompanion extends EntityCompanion
{

	protected EntityCombatCompanion(EntityType<? extends CreatureEntity> type, World worldIn)
	{
		super(type, worldIn);
	}

	@Override
	protected void registerGoals()
	{
		super.registerGoals();

		Goal attack = new MeleeAttackGoal(this, 0.7D, true);

		attack.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));

		this.goalSelector.addGoal(0, attack);

		this.targetSelector.addGoal(0, new EntityAICompanionOwnerHurt(this));
		this.targetSelector.addGoal(1, new EntityAICompanionTargetEnemy(this));
	}

	@Override
	protected void registerAttributes()
	{
		super.registerAttributes();

		this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);

		this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(10.0D);
	}

}
