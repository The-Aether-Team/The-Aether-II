package com.gildedgames.aether.common.entities.util.climbing;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.util.math.MathHelper;

public class AdvancedClimbingMoveHelper extends EntityMoveHelper
{
	/*public static final EntityMoveHelper.Action CLIMB_UP = EnumHelper.addEnum(EntityMoveHelper.Action.class, "CLIMB_UP", new Class[0]);

	public static final EntityMoveHelper.Action CLIMB_DOWN = EnumHelper.addEnum(EntityMoveHelper.Action.class, "CLIMB_DOWN", new Class[0]);*/

	private final EntityAdvancedClimbing entity;

	private int moveYTick;

	public AdvancedClimbingMoveHelper(EntityAdvancedClimbing entity)
	{
		super(entity);
		this.entity = entity;
	}

	@Override
	public void setMoveTo(double x, double y, double z, double speedIn)
	{
		super.setMoveTo(x, y, z, speedIn);

		this.moveYTick = 0;
	}

	@Override
	public void onUpdateMoveHelper()
	{
		if (this.action == EntityMoveHelper.Action.MOVE_TO)
		{
			this.action = EntityMoveHelper.Action.WAIT;

			/*if (this.entity.ticksExisted % 10 == 0)
			{
				this.entity.setPositionAndUpdate(this.posX, this.posY, this.posZ);
			}*/

			double xDif = this.posX - this.entity.posX;
			double zDif = this.posZ - this.entity.posZ;
			double yDif = this.posY - this.entity.posY;
			double sqDist = xDif * xDif + yDif * yDif + zDif * zDif;

			double sqDist2D = xDif * xDif + zDif * zDif;

			if (sqDist < 2.500000277905201E-7D)
			{
				this.entity.setMoveVertical(0.0F);
				this.entity.setMoveForward(0.0F);
				return;
			}

			if (yDif * yDif < 2.500000277905201E-7D)
			{
				this.entity.setMoveVertical(0.0F);
			}

			if (sqDist2D < 2.500000277905201E-7D)
			{
				this.entity.setMoveForward(0.0F);
			}

			float angle = (float) (MathHelper.atan2(zDif, xDif) * (180D / Math.PI)) - 90.0F;

			float speed = (float) (this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());

			this.entity.setAIMoveSpeed(speed);

			this.entity.rotationYaw = angle;

			if (sqDist2D < (double) Math.max(1.0F, this.entity.width))
			{
				this.entity.setMoveVertical(yDif > 0.0D ? speed : (yDif < 0.0D ? -speed : 0.0F));

				this.entity.setClimbing(true);

				if (this.entity.getFullBlocksBeside().isEmpty())
				{
					if (this.entity.hasNoGravity())
					{
						this.entity.setNoGravity(false);

						if (this.posY > this.entity.posY)
						{
							this.entity.motionY = 0.42F;
						}
					}
				}
				else
				{
					this.entity.setNoGravity(true);
				}
			}
		}
		else if (this.action == EntityMoveHelper.Action.JUMPING)
		{
			this.entity.setMoveVertical(7.0F);
			this.entity.setAIMoveSpeed((float) (this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));

			if (this.entity.onGround)
			{
				this.action = EntityMoveHelper.Action.WAIT;
			}
		}
		else
		{
			this.entity.setMoveForward(0.0F);
		}
	}
}
