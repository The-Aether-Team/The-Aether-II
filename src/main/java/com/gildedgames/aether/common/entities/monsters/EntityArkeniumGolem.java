package com.gildedgames.aether.common.entities.monsters;

import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.api.entity.effects.EEffectIntensity;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectIntensity;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.SoundsAether;
import com.gildedgames.aether.common.entities.ai.AetherNavigateGround;
import com.gildedgames.aether.common.entities.ai.EntityAIUnstuckBlueAercloud;
import com.gildedgames.aether.common.entities.ai.cockatrice.EntityAICockatriceHide;
import com.gildedgames.aether.common.entities.ai.cockatrice.EntityAICockatriceSneakAttack;
import com.gildedgames.aether.common.entities.ai.cockatrice.EntityAICockatriceWander;
import com.gildedgames.aether.common.entities.effects.StatusEffectCockatriceVenom;
import com.gildedgames.aether.common.init.LootTablesAether;
import com.gildedgames.aether.common.util.helpers.EntityUtil;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;

public class EntityArkeniumGolem extends EntityAetherMob
{
	protected Map<String, Float> defenseMap = Maps.newHashMap();
	{{
		this.defenseMap.put("Very Weak", 4.0F);
		this.defenseMap.put("Weak", 2.0F);
		this.defenseMap.put("Average", 0.0F);
		this.defenseMap.put("Strong", -2.0F);
		this.defenseMap.put("Very Strong", -4.0F);
	}}

	public EntityArkeniumGolem(final World world)
	{
		super(world);

		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(0, new EntityAIUnstuckBlueAercloud(this));
		this.tasks.addTask(7, new EntityAILookIdle(this));

		this.jumpHelper = new JumpHelperDisable(this);

		this.setSize(1.2F, 1.8F);
		this.stepHeight = 1.0F;

		this.experienceValue = 10;
	}

	@Override
	protected PathNavigate createNavigator(final World worldIn)
	{
		AetherNavigateGround navigateGround = new AetherNavigateGround(this, worldIn);

		navigateGround.setAvoidSun(false);

		return navigateGround;
	}

	@Override
	protected void jump()
	{

	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(28.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);

		this.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).setBaseValue(-4.0f);
		this.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).setBaseValue(-2.0f);
		this.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).setBaseValue(2.0f);
	}

	@Override
	public boolean attackEntityAsMob(final Entity entity)
	{
		final boolean flag = super.attackEntityAsMob(entity);

		if (flag && entity instanceof EntityLivingBase)
		{
			final EntityLivingBase living = (EntityLivingBase) entity;

			this.applyStatusEffectOnAttack(entity);
		}

		return flag;
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

	@Override
	protected void playStepSound(final BlockPos pos, final Block blockIn)
	{
		this.playSound(SoundEvents.BLOCK_STONE_STEP, 0.8F, 0.5F);
	}

//	@Override	protected ResourceLocation getLootTable()
	{
	//	return LootTablesAether.ENTITY_ARKENIUM_GOLEM;
	}

	public static class JumpHelperDisable extends EntityJumpHelper
	{

		public JumpHelperDisable(final EntityLiving entityIn)
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
