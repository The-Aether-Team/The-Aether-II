package com.gildedgames.aether.common.entities.living.enemies;

import com.gildedgames.aether.api.capabilites.entity.properties.ElementalState;
import com.gildedgames.aether.api.capabilites.entity.properties.IEntityProperties;
import com.gildedgames.aether.common.registry.minecraft.SoundsAether;
import com.gildedgames.aether.common.entities.ai.cockatrice.EntityAICockatriceHide;
import com.gildedgames.aether.common.entities.ai.cockatrice.EntityAICockatriceSneakAttack;
import com.gildedgames.aether.common.entities.ai.cockatrice.EntityAICockatriceWander;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityCockatrice extends EntityMob implements IEntityProperties
{

	private static final DataParameter<Boolean> IS_HIDING = new DataParameter<>(16, DataSerializers.BOOLEAN);

	private static final DataParameter<Boolean> IS_HIDDEN = new DataParameter<>(17, DataSerializers.BOOLEAN);

	private static final DataParameter<Boolean> IS_ATTACKING = new DataParameter<>(18, DataSerializers.BOOLEAN);

	public EntityCockatrice(World world)
	{
		super(world);

		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(4, new EntityAICockatriceWander(this, 0.35D));

		this.targetTasks.addTask(2, new EntityAICockatriceHide(this, EntityPlayer.class, 0.9D));
		this.targetTasks.addTask(1, new EntityAICockatriceSneakAttack(this, EntityPlayer.class));

		this.jumpHelper = new JumpHelperDisable(this);

		this.setSize(1.0F, 2.0F);
		this.stepHeight = 1.0F;
	}

	@Override
	protected void jump()
	{

	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		this.dataManager.register(EntityCockatrice.IS_HIDING, Boolean.FALSE);
		this.dataManager.register(EntityCockatrice.IS_HIDDEN, Boolean.FALSE);
		this.dataManager.register(EntityCockatrice.IS_ATTACKING, Boolean.FALSE);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10);
	}

	@Override
	public boolean isPotionApplicable(PotionEffect potionEffect)
	{
		return potionEffect.getPotion() != MobEffects.POISON && super.isPotionApplicable(potionEffect);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		boolean flag = super.attackEntityAsMob(entity);

		if (flag && entity instanceof EntityLivingBase)
		{
			EntityLivingBase living = (EntityLivingBase)entity;

			living.addPotionEffect(new PotionEffect(MobEffects.POISON, 40));
		}

		return flag;
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
	}

	@Override
	protected net.minecraft.util.SoundEvent getAmbientSound()
	{
		return SoundsAether.cockatrice_ambient;
	}

	@Override
	protected net.minecraft.util.SoundEvent getHurtSound()
	{
		return SoundsAether.cockatrice_hurt;
	}

	@Override
	protected net.minecraft.util.SoundEvent getDeathSound()
	{
		return SoundsAether.cockatrice_death;
	}

	public boolean isHiding()
	{
		return this.dataManager.get(EntityCockatrice.IS_HIDING);
	}

	public void setHiding(boolean isHiding)
	{
		this.dataManager.set(EntityCockatrice.IS_HIDING, isHiding);
	}

	public boolean isHidden()
	{
		return this.dataManager.get(EntityCockatrice.IS_HIDDEN);
	}

	public void setHidden(boolean isHidden)
	{
		this.dataManager.set(EntityCockatrice.IS_HIDDEN, isHidden);
	}

	public boolean isAttacking() { return this.dataManager.get(EntityCockatrice.IS_ATTACKING); }

	public void setAttacking(boolean isAttacking) { this.dataManager.set(EntityCockatrice.IS_ATTACKING, isAttacking); }

	@Override
	public ElementalState getElementalState()
	{
		return ElementalState.BLIGHT;
	}

	public static class JumpHelperDisable extends EntityJumpHelper
	{

		public JumpHelperDisable(EntityLiving entityIn)
		{
			super(entityIn);
		}

		public void setJumping()
		{

		}

		public void doJump()
		{

		}

	}

}
