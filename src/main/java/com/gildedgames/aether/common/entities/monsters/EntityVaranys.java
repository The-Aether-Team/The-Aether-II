package com.gildedgames.aether.common.entities.monsters;

import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.api.entity.effects.EEffectIntensity;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectIntensity;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.common.entities.ai.EntityAIHideFromLight;
import com.gildedgames.aether.common.entities.ai.EntityAIUnstuckBlueAercloud;
import com.gildedgames.aether.common.entities.ai.EntityAIWanderAvoidLight;
import com.gildedgames.aether.common.entities.effects.StatusEffectBleed;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class EntityVaranys extends EntityAetherMonster
{
	private EntityAIHideFromLight lightAI;

	public EntityVaranys(EntityType<? extends MonsterEntity> type, World world)
	{
		super(type, world);

		this.stepHeight = 1.0F;
	}

	@Override
	protected void registerGoals()
	{
		this.lightAI = new EntityAIHideFromLight(this, 0.8F, 5);

		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(0, new EntityAIUnstuckBlueAercloud(this));
		this.goalSelector.addGoal(1, this.lightAI);
		this.goalSelector.addGoal(1, new EntityAIWanderAvoidLight(this, 0.8D, 5));
		this.goalSelector.addGoal(2, new LeapAtTargetGoal(this, 0.4F));
		this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1D, false));
		this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));

	}

	@Override
	protected void registerData()
	{

	}

	@Override
	protected void registerAttributes()
	{
		super.registerAttributes();

		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
		this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.5D);
		this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);

		this.getAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).setBaseValue(15);
		this.getAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).setBaseValue(8);
		this.getAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).setBaseValue(4);
	}

	@Override
	public boolean attackEntityAsMob(final Entity entity)
	{
		final boolean flag = super.attackEntityAsMob(entity);

		if (flag && entity instanceof LivingEntity)
		{
			this.applyStatusEffectOnAttack(entity);
		}

		return flag;
	}

	@Override
	protected void applyStatusEffectOnAttack(final Entity target)
	{
		if (target instanceof LivingEntity)
		{
			final LivingEntity living = (LivingEntity) target;

			if (!living.isActiveItemStackBlocking())
			{
				int buildup = IAetherStatusEffectIntensity.getBuildupFromEffect(new StatusEffectBleed(living), EEffectIntensity.MINOR);
				IAetherStatusEffects.applyStatusEffect(living, IAetherStatusEffects.effectTypes.BLEED, buildup);
			}
		}
	}

	@Override
	public void livingTick()
	{
		super.livingTick();

		this.lightAI.setEnabled(this.getAttackTarget() == null);
	}

}
