package com.gildedgames.aether.common.entities.living.mobs;

import com.gildedgames.aether.common.entities.ai.EntityAIHideFromLight;
import com.gildedgames.aether.common.entities.ai.EntityAIHideFromTarget;
import com.gildedgames.aether.common.entities.ai.EntityAIUnstuckBlueAercloud;
import com.gildedgames.aether.common.entities.ai.EntityAIWanderAvoidLight;
import com.gildedgames.aether.common.entities.ai.cockatrice.EntityAICockatriceHide;
import com.gildedgames.aether.common.entities.ai.cockatrice.EntityAICockatriceSneakAttack;
import com.gildedgames.aether.common.entities.ai.cockatrice.EntityAICockatriceWander;
import com.gildedgames.aether.common.registry.content.LootTablesAether;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import com.gildedgames.aether.common.util.helpers.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityVaranys extends EntityAetherMob
{

	private EntityAIHideFromLight lightAI;

	public EntityVaranys(final World world)
	{
		super(world);

		lightAI = new EntityAIHideFromLight(this, 0.8D, 5);

		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(0, new EntityAIUnstuckBlueAercloud(this));
		this.tasks.addTask(1, lightAI);
		this.tasks.addTask(1, new EntityAIWanderAvoidLight(this, 0.8D, 5));
		this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
//		this.tasks.addTask(1, new EntityAIHideFromTarget(this, EntityPlayer.class, 0.8D));

		this.setSize(1.0F, 2.0F);
		this.stepHeight = 1.0F;

		this.experienceValue = 7;
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(26.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
	}

	@Override
	public boolean attackEntityAsMob(final Entity entity)
	{
		final boolean flag = super.attackEntityAsMob(entity);

		if (flag && entity instanceof EntityLivingBase)
		{
			final EntityLivingBase living = (EntityLivingBase) entity;

			if (!living.isActiveItemStackBlocking())
			{
				living.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 40));
			}
		}

		return flag;
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
	}

}
