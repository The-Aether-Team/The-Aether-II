package com.gildedgames.aether.common.entities.living.dungeon.labyrinth;

import com.gildedgames.aether.common.entities.ai.dungeon.labyrinth.AIPounceClose;
import com.gildedgames.aether.common.entities.ai.hopping.AIHopFloat;
import com.gildedgames.aether.common.entities.ai.hopping.AIHopFollowAttackTarget;
import com.gildedgames.aether.common.entities.ai.hopping.HoppingMoveHelper;
import com.gildedgames.aether.common.entities.living.mobs.EntityAetherMob;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.world.World;

public class EntityBattleSentry extends EntityAetherMob
{

	public EntityBattleSentry(World world)
	{
		super(world);

		HoppingMoveHelper hoppingMoveHelper = new HoppingMoveHelper(this, SoundsAether.stone_thud, new HoppingMoveHelper.HopTimer()
		{
			private int hops;

			@Override
			public int jumpDelay()
			{
				this.hops++;

				int jumpDelay;

				if (this.hops >= 3)
				{
					jumpDelay = 30;
					this.hops = 0;
				}
				else
				{
					jumpDelay = EntityBattleSentry.this.getRNG().nextInt(3) + 2;
				}

				return jumpDelay;
			}
		});

		this.moveHelper = hoppingMoveHelper;

		this.tasks.addTask(0, new AIPounceClose(this, hoppingMoveHelper, 5D));
		this.tasks.addTask(1, new AIHopFloat(this, hoppingMoveHelper));
		this.tasks.addTask(2, new AIHopFollowAttackTarget(this, hoppingMoveHelper, 1.5D));

		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true, true));

		this.setSize(0.8F, 0.8F);

		this.experienceValue = 3;
	}

	@Override
	protected void jump()
	{
		if (this.getMoveHelper().getSpeed() >= 4.0D)
		{
			this.playSound(SoundsAether.battle_sentry_pounce, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
			this.playSound(SoundsAether.sentry_hurt, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
		}

		this.motionY = 0.41999998688697815D;
		this.isAirBorne = true;
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer entityIn)
	{
		if (!this.isDead && this.getHealth() > 0 && this.getMoveHelper().getSpeed() >= 4.0D)
		{
			this.dealDamage(entityIn);
		}
	}

	protected void dealDamage(EntityLivingBase entityIn)
	{
		if (this.attackEntityAsMob(entityIn))
		{
			this.playSound(SoundEvents.ENTITY_GENERIC_BIG_FALL, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
		}
	}

	@Override
	protected net.minecraft.util.SoundEvent getAmbientSound()
	{
		return SoundsAether.sentry_ambient;
	}

	@Override
	protected net.minecraft.util.SoundEvent getHurtSound()
	{
		return SoundsAether.sentry_hurt;
	}

	@Override
	protected net.minecraft.util.SoundEvent getDeathSound()
	{
		return SoundsAether.sentry_death;
	}

	public int getVerticalFaceSpeed()
	{
		return 0;
	}

	@Override
	public boolean canDespawn()
	{
		return false;
	}

}
