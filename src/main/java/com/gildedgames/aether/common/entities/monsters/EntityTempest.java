package com.gildedgames.aether.common.entities.monsters;

import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.api.registrar.SoundsAether;
import com.gildedgames.aether.common.entities.ai.EntityAIForcedWanderAvoidLight;
import com.gildedgames.aether.common.entities.ai.EntityAIHideFromLight;
import com.gildedgames.aether.common.entities.ai.tempest.AIElectricShock;
import com.gildedgames.aether.common.entities.flying.EntityFlyingMob;
import com.gildedgames.aether.common.util.helpers.EntityUtil;
import com.google.common.collect.Maps;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import java.util.Map;

public class EntityTempest extends EntityFlyingMob
{
	protected Map<String, Float> defenseMap = Maps.newHashMap();
	{{
		this.defenseMap.put("Very Weak", 4.0F);
		this.defenseMap.put("Weak", 2.0F);
		this.defenseMap.put("Average", 0.0F);
		this.defenseMap.put("Strong", -2.0F);
		this.defenseMap.put("Very Strong", -4.0F);
	}}

	public EntityTempest(final World world)
	{
		super(world);

		this.setSize(1.0F, 1.0F);

		this.experienceValue = 10;
	}

	@Override
	protected EntityAIBase createWanderTask()
	{
		final EntityAIWander wander = new EntityAIForcedWanderAvoidLight(this, 0.4D, 5, 5);

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

		EntityUtil.spawnParticleLineBetween(this, target, 4D, EnumParticleTypes.SPELL_INSTANT);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		EntityUtil.despawnEntityDuringDaytime(this);
	}

	@Override
	protected void initEntityAI()
	{
		super.initEntityAI();

		final EntityAIBase avoidLight = new EntityAIHideFromLight(this, 0.8F, 5);

		avoidLight.setMutexBits(1);

		this.tasks.addTask(2, new AIElectricShock(this));
		this.tasks.addTask(0, avoidLight);

		this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(26.0D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(60);

		this.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).setBaseValue(0.0f);
		this.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).setBaseValue(2.0f);
		this.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).setBaseValue(-4.0f);
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundsAether.tempest_ambient;
	}

	@Override
	protected SoundEvent getHurtSound(final DamageSource damageSourceIn)
	{
		return SoundsAether.tempest_hurt;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundsAether.tempest_death;
	}
}
