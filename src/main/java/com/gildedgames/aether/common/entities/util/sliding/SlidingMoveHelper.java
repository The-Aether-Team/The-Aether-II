package com.gildedgames.aether.common.entities.util.sliding;

import com.gildedgames.aether.common.registry.minecraft.SoundsAether;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class SlidingMoveHelper extends EntityMoveHelper
{

	private Direction direction = Direction.NONE, lastDirection = Direction.NONE;

	private double distanceToSlide, distanceSlided;

	private int moveCooldown;

	private float slideVelocity;

	private boolean missObstacleNextTick = false, missedObstacleLastTick = false, moving;

	public SlidingMoveHelper(EntityLiving entity)
	{
		super(entity);
	}

	public void stop()
	{
		this.moving = false;

		if ((this.missObstacleNextTick | this.direction == this.calculateDirection(this.posX, this.posY, this.posZ)))
		{
			this.moveCooldown = 0;
		}
		else
		{
			this.moveCooldown = 12;
			this.slideVelocity = 0;
		}

		this.distanceToSlide = 0;
		this.distanceSlided = 0;

		this.lastDirection = this.direction;
	}

	public double calculateDistanceToSlide(double x, double y, double z)
	{
		Vec3d diff = new Vec3d(x - MathHelper.floor_double(this.entity.posX), y - MathHelper.floor_double(this.entity.posY), z - MathHelper.floor_double(this.entity.posZ));

		double distanceToPlayer =
		(
				this.direction == Direction.LEFT ? -diff.xCoord :
				this.direction == Direction.RIGHT ? diff.xCoord :
				this.direction == Direction.DOWN ? -diff.yCoord :
				this.direction == Direction.UP ? diff.yCoord :
				this.direction == Direction.BACKWARD ? -diff.zCoord : diff.zCoord
		);

		if (this.missObstacleNextTick)
		{
			distanceToPlayer = 1;
		}

		return distanceToPlayer;
	}

	public boolean willCollideUp()
	{
		boolean collideUp = false;

		List<AxisAlignedBB> boxes = this.entity.worldObj.getCollisionBoxes(this.entity.getEntityBoundingBox().offset(0.0D, this.entity.height + 0.1D, 0.0D));

		for (AxisAlignedBB box : boxes)
		{
			if (box != null)
			{
				collideUp = true;
				break;
			}
		}

		return collideUp;
	}

	public boolean willCollideDown()
	{
		boolean collidingDown = false;

		List<AxisAlignedBB> boxes = this.entity.worldObj.getCollisionBoxes(this.entity.getEntityBoundingBox().offset(0.0D, -0.1D, 0.0D));

		for (AxisAlignedBB box : boxes)
		{
			if (box != null)
			{
				collidingDown = true;
				break;
			}
		}

		return collidingDown;
	}

	public Direction calculateDirection(double x, double y, double z)
	{
		Direction direction = Direction.NONE;
		Vec3d diff = new Vec3d(x - MathHelper.floor_double(this.entity.posX), y - MathHelper.floor_double(this.entity.posY), z - MathHelper.floor_double(this.entity.posZ));

		boolean willCollideUp = this.willCollideUp();
		boolean willCollideDown = this.willCollideDown();

		if (!this.missedObstacleLastTick && ((diff.yCoord >= 1.0f && !willCollideUp) || (diff.yCoord <= -1.0f && !willCollideDown)))
		{
			direction = diff.yCoord < 0 ? Direction.DOWN : Direction.UP;
		}
		else
		{
			if (diff.xCoord == 0)
			{
				direction = diff.zCoord < 0 ? Direction.BACKWARD : Direction.FORWARD;
			}
			else if (diff.zCoord == 0)
			{
				direction = diff.xCoord < 0 ? Direction.LEFT : Direction.RIGHT;
			}
			else
			{
				if (diff.xCoord < 0 && diff.zCoord < 0)
				{
					direction = diff.xCoord < diff.zCoord ? Direction.LEFT : Direction.BACKWARD;
				}
				else if (diff.xCoord > 0 && diff.zCoord < 0)
				{
					direction = diff.xCoord > -diff.zCoord ? Direction.RIGHT : Direction.BACKWARD;
				}
				else if (diff.xCoord > 0 && diff.zCoord > 0)
				{
					direction = diff.xCoord > diff.zCoord ? Direction.RIGHT : Direction.FORWARD;
				}
				else if (diff.xCoord < 0 && diff.zCoord > 0)
				{
					direction = -diff.xCoord > diff.zCoord ? Direction.LEFT : Direction.FORWARD;
				}
			}
		}

		if (this.missObstacleNextTick)
		{
			direction = !willCollideUp ? Direction.UP : Direction.DOWN;
		}

		return direction;
	}

	@Override
	public void onUpdateMoveHelper()
	{
		this.entity.jumpMovementFactor = 0.0F;
		this.entity.renderYawOffset = this.entity.rotationPitch = this.entity.rotationYaw = 0.0F;

		this.entity.rotationYawHead = this.entity.rotationYaw;
		this.entity.renderYawOffset = this.entity.rotationYaw;

		this.entity.fallDistance = 0.0F;

		this.entity.motionX = 0.0F;
		this.entity.motionY = 0.0F;
		this.entity.motionZ = 0.0F;

		this.entity.setMoveForward(0.0F);
		this.entity.setMoveStrafing(0.0F);
		this.entity.setAIMoveSpeed(0.0F);

		if (this.moveCooldown > 0)
		{
			this.moveCooldown--;
			return;
		}

		Vec3d pos = new Vec3d(this.posX, this.posY, this.posZ);

		if (this.moving)
		{
			if (this.slideVelocity < 1.0f)
			{
				this.slideVelocity += 0.045f;
			}
			if (this.slideVelocity > 1.0f)
			{
				this.slideVelocity = 1.0f;
			}

			float add = (float) (this.distanceToSlide - this.distanceSlided);

			switch(this.direction)
			{
				case UP:
				{
					this.entity.motionY = this.slideVelocity;

					this.distanceSlided += this.slideVelocity;

					if (this.distanceSlided >= this.distanceToSlide)
					{
						this.entity.motionY += add;

						this.stop();
					}

					break;
				}
				case DOWN:
				{
					this.entity.motionY = -this.slideVelocity;

					this.distanceSlided += this.slideVelocity;

					if (this.distanceSlided >= this.distanceToSlide)
					{
						this.entity.motionY -= add;

						this.stop();
					}

					break;
				}
				case RIGHT:
				{
					this.entity.motionX = this.slideVelocity;

					this.distanceSlided += this.slideVelocity;

					if (this.distanceSlided >= this.distanceToSlide)
					{
						this.entity.motionX += add;

						this.stop();
					}

					break;
				}
				case LEFT:
				{
					this.entity.motionX = -this.slideVelocity;

					this.distanceSlided += this.slideVelocity;

					if (this.distanceSlided >= this.distanceToSlide)
					{
						this.entity.motionX -= add;

						this.stop();
					}

					break;
				}
				case FORWARD:
				{
					this.entity.motionZ = this.slideVelocity;

					this.distanceSlided += this.slideVelocity;

					if (this.distanceSlided >= this.distanceToSlide)
					{
						this.entity.motionZ += add;

						this.stop();
					}

					break;
				}
				case BACKWARD:
				{
					this.entity.motionZ = -this.slideVelocity;

					this.distanceSlided += this.slideVelocity;

					if (this.distanceSlided >= this.distanceToSlide)
					{
						this.entity.motionZ -= add;

						this.stop();
					}

					break;
				}
			}

			if (this.entity.isCollidedHorizontally)
			{
				if (this.direction != Direction.UP && this.direction != Direction.DOWN)
				{
					this.missObstacleNextTick = true;
					this.stop();
				}
			}
		}
		else if (pos.distanceTo(this.entity.getPositionVector()) < this.entity.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue())
		{
			this.direction = this.calculateDirection(this.posX, this.posY, this.posZ);
			this.distanceToSlide = this.calculateDistanceToSlide(this.posX, this.posY, this.posZ);

			this.moving = true;

			this.distanceSlided = 0;
			this.slideVelocity = 0;

			this.entity.motionX = 0;
			this.entity.motionY = 0;
			this.entity.motionZ = 0;

			this.missedObstacleLastTick = this.missObstacleNextTick;

			this.missObstacleNextTick = false;

			this.entity.playSound(SoundsAether.slider_move, 2.5F, 1.0F / (this.entity.getRNG().nextFloat() * 0.2F + 0.9F));

			//this.entity.setPositionAndUpdate(MathHelper.floor_double(this.entity.posX), MathHelper.floor_double(this.entity.posY), MathHelper.floor_double(this.entity.posZ));
		}
	}

	public enum Direction
	{
		NONE, UP, DOWN, RIGHT, LEFT, FORWARD, BACKWARD;
	}

}

