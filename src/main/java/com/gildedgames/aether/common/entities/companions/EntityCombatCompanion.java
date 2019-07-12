package com.gildedgames.aether.common.entities.companions;

import com.gildedgames.aether.common.entities.ai.companion.EntityAICompanionOwnerHurt;
import com.gildedgames.aether.common.entities.ai.companion.EntityAICompanionTargetEnemy;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.world.World;

public abstract class EntityCombatCompanion extends EntityCompanion
{

	public EntityCombatCompanion(World worldIn)
	{
		super(worldIn);
	}

	@Override
	protected void registerGoals()
	{
		super.registerGoals();

		Goal attack = new EntityAIAttackMelee(this, 0.7D, true);

		attack.setMutexBits(1);

		this.goalSelector.addGoal(0, attack);

		this.targetSelector.addGoal(0, new EntityAICompanionOwnerHurt(this));
		this.targetSelector.addGoal(1, new EntityAICompanionTargetEnemy(this));
	}

	@Override
	protected void registerAttributes()
	{
		super.registerAttributes();

		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);

		this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(10.0D);
	}

}
