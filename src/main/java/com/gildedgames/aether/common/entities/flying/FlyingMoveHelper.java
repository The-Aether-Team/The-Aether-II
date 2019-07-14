package com.gildedgames.aether.common.entities.flying;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.util.math.MathHelper;

public class FlyingMoveHelper extends MovementController
{
	private final EntityFlying entity;

	public FlyingMoveHelper(EntityFlying entity)
	{
		super(entity);
		this.entity = entity;
	}

	@Override
	public void tick()
	{
		if (this.action == MovementController.Action.MOVE_TO && !this.entity.getNavigator().noPath())
		{
			double xDiff = this.posX - this.entity.posX;
			double yDiff = this.posY - this.entity.posY;
			double zDiff = this.posZ - this.entity.posZ;
			double dist = xDiff * xDiff + yDiff * yDiff + zDiff * zDiff;
			dist = (double) MathHelper.sqrt(dist);

			yDiff = yDiff / dist;

			float angle = (float) (MathHelper.atan2(zDiff, xDiff) * (180D / Math.PI)) - 90.0F;

			this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, angle, 90.0F);
			this.entity.renderYawOffset = this.entity.rotationYaw;

			float speed = (float) (this.speed * this.entity.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue());
			this.entity.setAIMoveSpeed(this.entity.getAIMoveSpeed() + (speed - this.entity.getAIMoveSpeed()) * 0.125F);
			double motionScale = 0.007F;
			double motionX = Math.cos((double) (this.entity.rotationYaw * 0.017453292F));
			double motionZ = Math.sin((double) (this.entity.rotationYaw * 0.017453292F));

			double newMotionX = this.entity.getMotion().getX();
			double newMotionY = this.entity.getMotion().getY();
			double newMotionZ = this.entity.getMotion().getZ();

			newMotionX += motionScale * motionX;
			newMotionZ += motionScale * motionZ;
			//d4 = Math.sin((double)(this.entity.ticksExisted + this.entity.getEntityId()) * 0.75D) * 0.05D;
			newMotionY += motionScale * (motionZ + motionX) * 0.25D;
			newMotionY += (double) this.entity.getAIMoveSpeed() * yDiff * 0.1D;

			this.entity.setMotion(newMotionX, newMotionY, newMotionZ);

			LookController controller = this.entity.getLookController();
			double d7 = this.entity.posX + xDiff / dist * 2.0D;
			double d8 = (double) this.entity.getEyeHeight() + this.entity.posY + yDiff / dist;
			double d9 = this.entity.posZ + zDiff / dist * 2.0D;
			double lookPosX = controller.getLookPosX();
			double lookPosY = controller.getLookPosY();
			double lookPosZ = controller.getLookPosZ();

			if (!controller.getIsLooking())
			{
				lookPosX = d7;
				lookPosY = d8;
				lookPosZ = d9;
			}

			this.entity.getLookController().setLookPosition(
					lookPosX + (d7 - lookPosX) * 0.125D, lookPosY + (d8 - lookPosY) * 0.125D, lookPosZ + (d9 - lookPosZ) * 0.125D, 10.0F, 40.0F);
			this.entity.setMoving(true);
		}
		else
		{
			this.entity.setAIMoveSpeed(0.0F);
			this.entity.setMoving(false);
		}
	}
}
