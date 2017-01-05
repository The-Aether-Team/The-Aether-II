package com.gildedgames.aether.common.entities.living.mobs;

import com.gildedgames.aether.common.entities.ai.tempest.AIElectricShock;
import com.gildedgames.aether.common.entities.util.flying.EntityFlyingMob;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import com.gildedgames.aether.common.util.helpers.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityTempest extends EntityFlyingMob
{

	public EntityTempest(World world)
	{
		super(world);

		this.setSize(1.0F, 1.0F);

		this.experienceValue = 10;
	}

	@Override
	protected void handleClientAttack()
	{
		Entity target = this.worldObj.getNearestPlayerNotCreative(this, 20D);

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

		this.tasks.addTask(2, new AIElectricShock(this));

		this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(60);
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundsAether.tempest_ambient;
	}

	@Override
	protected SoundEvent getHurtSound()
	{
		return SoundsAether.tempest_hurt;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundsAether.tempest_death;
	}
}
