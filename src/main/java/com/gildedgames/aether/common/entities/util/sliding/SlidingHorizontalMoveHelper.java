package com.gildedgames.aether.common.entities.util.sliding;

import com.gildedgames.aether.common.registry.minecraft.SoundsAether;
import com.gildedgames.aether.common.util.TickTimer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class SlidingHorizontalMoveHelper extends EntityMoveHelper
{

	private Direction direction = Direction.NONE, lastDirection = Direction.NONE;

	private double distanceToSlide, distanceSlided;

	private int moveCooldown;

	private float slideVelocity;

	private boolean missObstacleNextTick = false, missedObstacleLastTick = false, moving;

	private TickTimer moveTimer = new TickTimer();

	private ISlidingEntity slidingEntity;

	public SlidingHorizontalMoveHelper(EntityLiving entity, ISlidingEntity slidingEntity)
	{
		super(entity);

		this.slidingEntity = slidingEntity;
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

		this.moveTimer.reset();
	}

	public double calculateDistanceToSlide(double x, double y, double z)
	{
		Vec3d diff = new Vec3d(x - MathHelper.floor_double(this.entity.posX), y - MathHelper.floor_double(this.entity.posY), z - MathHelper.floor_double(this.entity.posZ));

		double distanceToPlayer =
		(
				this.direction == Direction.LEFT ? -diff.xCoord :
				this.direction == Direction.RIGHT ? diff.xCoord :
				this.direction == Direction.BACKWARD ? -diff.zCoord : diff.zCoord
		);

		if (this.missObstacleNextTick)
		{
			distanceToPlayer = 1;
		}

		return distanceToPlayer;
	}

	public boolean willCollideForward()
	{
		boolean collideForward = false;

		double length = this.entity.getEntityBoundingBox().maxZ - this.entity.getEntityBoundingBox().minZ;

		List<AxisAlignedBB> boxes = this.entity.worldObj.getCollisionBoxes(this.entity.getEntityBoundingBox().offset(0.0D, 0.0D, length + 0.1D));

		for (AxisAlignedBB box : boxes)
		{
			if (box != null)
			{
				collideForward = true;
				break;
			}
		}

		return collideForward;
	}

	public boolean willCollideLeft()
	{
		boolean collidingDown = false;

		List<AxisAlignedBB> boxes = this.entity.worldObj.getCollisionBoxes(this.entity.getEntityBoundingBox().offset(-0.1D, 0.0D, 0.0D));

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

		boolean willCollideLeft = this.willCollideForward();
		boolean willCollideForward = this.willCollideLeft();

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

		if (this.missObstacleNextTick)
		{
			if (this.lastDirection == Direction.BACKWARD || this.lastDirection == Direction.FORWARD)
			{
				direction = !willCollideLeft ? Direction.LEFT : Direction.RIGHT;
			}
			else
			{
				direction = !willCollideForward ? Direction.FORWARD : Direction.BACKWARD;
			}
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
			if (this.moveTimer.getTicksPassed() < this.slidingEntity.getSlideCooldown())
			{
				this.moveTimer.tick();
				return;
			}

			if (this.moveTimer.getTicksPassed() == this.slidingEntity.getSlideCooldown())
			{
				this.slidingEntity.onSlide();
				this.moveTimer.tick();
			}

			this.slidingEntity.onSliding();

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

			if (this.entity.isCollidedHorizontally && !this.missObstacleNextTick)
			{
				this.missObstacleNextTick = true;
				this.stop();
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

			this.slidingEntity.onStartSlideCooldown(this.direction);
		}
	}

	public enum Direction
	{
		NONE, RIGHT, LEFT, FORWARD, BACKWARD;
	}

}

