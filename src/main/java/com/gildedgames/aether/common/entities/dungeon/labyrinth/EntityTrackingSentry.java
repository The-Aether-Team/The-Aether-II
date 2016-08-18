package com.gildedgames.aether.common.entities.dungeon.labyrinth;

import com.gildedgames.aether.common.SoundsAether;
import com.gildedgames.aether.common.entities.ai.dungeon.labyrinth.AIAlarmClose;
import com.gildedgames.aether.common.entities.ai.dungeon.labyrinth.AIDetonateClose;
import com.gildedgames.aether.common.entities.ai.hopping.AIHopFloat;
import com.gildedgames.aether.common.entities.ai.hopping.AIHopFollowAttackTarget;
import com.gildedgames.aether.common.entities.ai.hopping.HoppingMoveHelper;
import com.gildedgames.aether.common.util.TickTimer;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityTrackingSentry extends EntityCreature
{

	private TickTimer timer = new TickTimer();

	public EntityTrackingSentry(World worldIn)
	{
		super(worldIn);

		this.tasks.addTask(0, new AIAlarmClose(this, 80.0D));
		this.tasks.addTask(1, new EntityAIMoveTowardsTarget(this, 0.9D, 25.0F));

		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));

		this.setSize(1.0F, 2.0F);
		this.stepHeight = 1.0F;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
	{

	}

	@Override
	public boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		this.timer.tick();

		if (this.getAttackTarget() != null && this.timer.getTicksPassed() >= 10)
		{
			this.faceEntity(this.getAttackTarget(), 10.0F, 10.0F);

			this.playSound(SoundsAether.tracking_sentry_alarm, 0.5F, (this.getRNG().nextFloat() * 0.1F) + 0.9F);

			this.timer.reset();
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

}
