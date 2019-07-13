package com.gildedgames.aether.common.entities.monsters;

import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.api.entity.effects.EEffectIntensity;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectIntensity;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.ai.EntityAIUnstuckBlueAercloud;
import com.gildedgames.aether.common.entities.ai.cockatrice.EntityAICockatriceHide;
import com.gildedgames.aether.common.entities.ai.cockatrice.EntityAICockatriceSneakAttack;
import com.gildedgames.aether.common.entities.ai.cockatrice.EntityAICockatriceWander;
import com.gildedgames.aether.common.entities.effects.StatusEffectCockatriceVenom;
import com.gildedgames.aether.common.init.LootTablesAether;
import com.gildedgames.aether.common.util.helpers.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.controller.JumpController;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityCockatrice extends EntityAetherMonster
{

	private static final DataParameter<Boolean> IS_HIDING = new DataParameter<>(16, DataSerializers.BOOLEAN);

	private static final DataParameter<Boolean> IS_HIDDEN = new DataParameter<>(17, DataSerializers.BOOLEAN);

	private static final DataParameter<Boolean> IS_ATTACKING = new DataParameter<>(18, DataSerializers.BOOLEAN);

	public EntityCockatrice(final World world)
	{
		super(world);

		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(0, new EntityAIUnstuckBlueAercloud(this));
		this.goalSelector.addGoal(1, new EntityAICockatriceHide(this, PlayerEntity.class, 0.9D));
		this.goalSelector.addGoal(2, new EntityAICockatriceWander(this, 0.35D));

		this.targetSelector.addGoal(0, new EntityAICockatriceSneakAttack(this, PlayerEntity.class));

		this.jumpController = new JumpHelperDisable(this);

		this.setSize(1.0F, 2.0F);
		this.stepHeight = 1.0F;

		this.experienceValue = 7;
	}

	@Override
	protected void jump()
	{

	}

	@Override
	protected void registerData()
	{
		super.registerData();

		this.dataManager.register(EntityCockatrice.IS_HIDING, Boolean.FALSE);
		this.dataManager.register(EntityCockatrice.IS_HIDDEN, Boolean.FALSE);
		this.dataManager.register(EntityCockatrice.IS_ATTACKING, Boolean.FALSE);
	}

	@Override
	protected void registerAttributes()
	{
		super.registerAttributes();

		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(18.0D);
		this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
		this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);

		this.getAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).setBaseValue(8);
		this.getAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).setBaseValue(10);
		this.getAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).setBaseValue(4);
	}

	@Override
	public boolean isPotionApplicable(final PotionEffect potionEffect)
	{
		return potionEffect.getPotion() != Effects.POISON && super.isPotionApplicable(potionEffect);
	}

	@Override
	public boolean attackEntityAsMob(final Entity entity)
	{
		final boolean flag = super.attackEntityAsMob(entity);

		if (flag && entity instanceof LivingEntity)
		{
			final LivingEntity living = (LivingEntity) entity;

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
				int buildup = IAetherStatusEffectIntensity.getBuildupFromEffect(new StatusEffectCockatriceVenom(living), EEffectIntensity.ORDINARY);
				IAetherStatusEffects.applyStatusEffect(living, IAetherStatusEffects.effectTypes.COCKATRICE_VENOM, buildup);
			}
		}
	}

	@Override
	public void livingTick()
	{
		super.livingTick();

		EntityUtil.despawnEntityDuringDaytime(this);
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return new SoundEvent(AetherCore.getResource("mob.cockatrice.ambient"));
	}

	@Override
	protected SoundEvent getHurtSound(final DamageSource damageSourceIn)
	{
		return new SoundEvent(AetherCore.getResource("mob.cockatrice.hurt"));
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return new SoundEvent(AetherCore.getResource("mob.cockatrice.death"));
	}

	public boolean isHiding()
	{
		return this.dataManager.get(EntityCockatrice.IS_HIDING);
	}

	public void setHiding(final boolean isHiding)
	{
		this.dataManager.set(EntityCockatrice.IS_HIDING, isHiding);
	}

	public boolean isHidden()
	{
		return this.dataManager.get(EntityCockatrice.IS_HIDDEN);
	}

	public void setHidden(final boolean isHidden)
	{
		this.dataManager.set(EntityCockatrice.IS_HIDDEN, isHidden);
	}

	public boolean isAttacking()
	{
		return this.dataManager.get(EntityCockatrice.IS_ATTACKING);
	}

	public void setAttacking(final boolean isAttacking)
	{
		this.dataManager.set(EntityCockatrice.IS_ATTACKING, isAttacking);
	}

	@Override
	protected ResourceLocation getLootTable()
	{
		return LootTablesAether.ENTITY_COCKATRICE;
	}

	public static class JumpHelperDisable extends JumpController
	{

		public JumpHelperDisable(final MobEntity entityIn)
		{
			super(entityIn);
		}

		@Override
		public void setJumping()
		{

		}

		@Override
		public void doJump()
		{

		}

	}

}
