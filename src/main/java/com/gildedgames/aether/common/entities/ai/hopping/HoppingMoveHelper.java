package com.gildedgames.aether.common.entities.ai.hopping;

import com.gildedgames.aether.common.SoundsAether;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityMoveHelper;

public class HoppingMoveHelper extends EntityMoveHelper
{

	private float yRot;
	private int jumpDelay;
	private final EntityLiving entity;

	public HoppingMoveHelper(EntityLiving entity)
	{
		super(entity);
		this.entity = entity;
		this.yRot = 180.0F * entity.rotationYaw / (float)Math.PI;
	}

	public void setDirection(float p_179920_1_)
	{
		this.yRot = p_179920_1_;
	}

	public void setSpeed(double speedIn)
	{
		this.speed = speedIn;
		this.action = EntityMoveHelper.Action.MOVE_TO;
	}

	public void onUpdateMoveHelper()
	{
		this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, this.yRot, 90.0F);
		this.entity.rotationYawHead = this.entity.rotationYaw;
		this.entity.renderYawOffset = this.entity.rotationYaw;

		if (this.action != Action.MOVE_TO)
		{
			this.entity.setMoveForward(0.0F);
		}
		else
		{
			this.action = Action.WAIT;

			if (this.entity.onGround)
			{
				this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));

				if (this.jumpDelay-- <= 0)
				{
					this.jumpDelay = this.entity.getRNG().nextInt(20) + 10;

					this.entity.getJumpHelper().setJumping();

					this.entity.playSound(SoundsAether.stone_thud, 0.5F, ((this.entity.getRNG().nextFloat() - this.entity.getRNG().nextFloat()) * 0.2F + 1.0F) * 0.8F);
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
				this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
			}
		}
	}

}
