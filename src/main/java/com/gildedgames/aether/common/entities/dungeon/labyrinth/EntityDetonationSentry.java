package com.gildedgames.aether.common.entities.dungeon.labyrinth;

import com.gildedgames.aether.common.registry.minecraft.SoundsAether;
import com.gildedgames.aether.common.entities.ai.dungeon.labyrinth.AIDetonateClose;
import com.gildedgames.aether.common.entities.ai.hopping.AIHopFloat;
import com.gildedgames.aether.common.entities.ai.hopping.AIHopFollowAttackTarget;
import com.gildedgames.aether.common.entities.ai.hopping.HoppingMoveHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityDetonationSentry extends EntityMob
{

	public EntityDetonationSentry(World worldIn)
	{
		super(worldIn);

		HoppingMoveHelper hoppingMoveHelper = new HoppingMoveHelper(this, SoundsAether.stone_thud);

		this.moveHelper = hoppingMoveHelper;

		this.tasks.addTask(0, new AIDetonateClose(this, 1.5D));
		this.tasks.addTask(1, new AIHopFloat(this, hoppingMoveHelper));
		this.tasks.addTask(2, new AIHopFollowAttackTarget(this, hoppingMoveHelper, 1.0D));

		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));

		this.setSize(1.0F, 1.0F);
	}

	@Override
	protected void jump()
	{
		this.motionY = 0.41999998688697815D;
		this.isAirBorne = true;
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16);
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
