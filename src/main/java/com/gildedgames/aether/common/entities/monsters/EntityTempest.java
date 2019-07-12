package com.gildedgames.aether.common.entities.monsters;

import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.ai.EntityAIForcedWanderAvoidLight;
import com.gildedgames.aether.common.entities.ai.EntityAIHideFromLight;
import com.gildedgames.aether.common.entities.ai.tempest.AIElectricShock;
import com.gildedgames.aether.common.entities.flying.EntityFlyingMob;
import com.gildedgames.aether.common.util.helpers.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityTempest extends EntityFlyingMob
{

	public EntityTempest(final World world)
	{
		super(world);

		this.setSize(1.0F, 1.0F);

		this.experienceValue = 10;
	}

	@Override
	protected Goal createWanderTask()
	{
		final RandomWalkingGoal wander = new EntityAIForcedWanderAvoidLight(this, 0.4D, 5, 5);

		wander.setMutexBits(1);

		return wander;
	}

	@Override
	protected void handleClientAttack()
	{
		final Entity target = this.world.getNearestPlayerNotCreative(this, 20D);

		if (target == null)
		{
			return;
		}

		this.faceEntity(target, 10.0F, 10.0F);

		EntityUtil.spawnParticleLineBetween(this, target, 4D, ParticleTypes.SPELL_INSTANT);
	}

	@Override
	public void livingTick()
	{
		super.livingTick();

		EntityUtil.despawnEntityDuringDaytime(this);
	}

	@Override
	protected void registerGoals()
	{
		super.registerGoals();

		final Goal avoidLight = new EntityAIHideFromLight(this, 0.8F, 5);

		avoidLight.setMutexBits(1);

		this.goalSelector.addGoal(2, new AIElectricShock(this));
		this.goalSelector.addGoal(0, avoidLight);

		this.targetSelector.addGoal(0, new EntityAINearestAttackableTarget<>(this, PlayerEntity.class, true));
	}

	@Override
	protected void registerAttributes()
	{
		super.registerAttributes();

		this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(22.0D);
		this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
		this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(60);

		this.getAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).setBaseValue(10);
		this.getAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).setBaseValue(10);
		this.getAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).setBaseValue(5);
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return new SoundEvent(AetherCore.getResource("mob.tempest.ambient"));
	}

	@Override
	protected SoundEvent getHurtSound(final DamageSource damageSourceIn)
	{
		return new SoundEvent(AetherCore.getResource("mob.tempest.hurt"));
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return new SoundEvent(AetherCore.getResource("mob.tempest.death"));
	}
}
