package com.gildedgames.aether.common.entities.ai.hopping;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.util.SoundEvent;

import java.util.function.Supplier;

public class HoppingMoveHelper extends MovementController
{

	private final MobEntity entity;

	private float yRot;

	private int jumpDelay;

	private final Supplier<SoundEvent> hoppingSound;

	private HopTimer hopTimer;

	private boolean active;

	public HoppingMoveHelper(MobEntity entity, Supplier<SoundEvent> hoppingSound, HopTimer hopTimer)
	{
		super(entity);

		this.hopTimer = hopTimer;
		this.hoppingSound = hoppingSound;
		this.entity = entity;
		this.yRot = 180.0F * entity.rotationYaw / (float) Math.PI;
		this.active = true;
	}

	public HoppingMoveHelper(final MobEntity entity, Supplier<SoundEvent> hoppingSound)
	{
		this(entity, hoppingSound, () -> entity.getRNG().nextInt(20) + 10);
	}

	public void setDirection(float p_179920_1_)
	{
		this.yRot = p_179920_1_;
	}

	public void setSpeed(double speedIn)
	{
		this.speed = speedIn;
		this.action = Action.MOVE_TO;
	}

	public void setTime(HopTimer timer)
	{
		this.hopTimer = timer;
	}

	public HopTimer getTimer()
	{
		return this.hopTimer;
	}

	@Override
	public void tick()
	{
		this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, this.yRot, 90.0F);
		this.entity.rotationYawHead = this.entity.rotationYaw;
		this.entity.renderYawOffset = this.entity.rotationYaw;

		if (!this.active)
		{
			return;
		}

		if (this.action != Action.MOVE_TO)
		{
			this.entity.setMoveForward(0.0F);
		}
		else
		{
			this.action = Action.WAIT;

			if (this.entity.onGround)
			{
				this.entity.setAIMoveSpeed((float) (this.speed
						* this.entity.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue()));

				if (this.jumpDelay-- <= 0)
				{
					this.jumpDelay = this.hopTimer.jumpDelay();

					this.entity.getJumpController().setJumping();

					this.entity.playSound(this.hoppingSound.get(), 0.5F,
							((this.entity.getRNG().nextFloat() - this.entity.getRNG().nextFloat()) * 0.2F + 1.0F) * 0.8F);
				}
				else
				{
					this.entity.moveStrafing = 0.0F;
					this.entity.moveForward = 0.0F;
					this.entity.setAIMoveSpeed(0.0F);
				}
			}
			else
			{
				this.entity.setAIMoveSpeed((float) (this.speed
						* this.entity.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue()));
			}
		}
	}

	public void setActive(boolean isActive)
	{
		if (!isActive)
		{
			this.entity.setAIMoveSpeed(0.0F);
		}
		else
		{
			this.entity.setAIMoveSpeed((float) (this.speed
					* this.entity.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue()));
		}

		this.active = isActive;
	}

	public interface HopTimer
	{

		int jumpDelay();

	}

}
