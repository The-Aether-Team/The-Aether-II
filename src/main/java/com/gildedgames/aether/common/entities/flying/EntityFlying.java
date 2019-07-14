package com.gildedgames.aether.common.entities.flying;

import com.gildedgames.aether.common.entities.ai.EntityAIForcedWander;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.controller.BodyController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.EnumSet;

public class EntityFlying extends CreatureEntity
{

	private static final DataParameter<Boolean> IS_MOVING = EntityDataManager.createKey(EntityFlying.class, DataSerializers.BOOLEAN);

	private final float clientSideTailAnimationO;

	private float clientSideTailAnimationSpeed;

	private float clientSideTailAnimation;

	protected EntityFlying(EntityType<? extends CreatureEntity> type, World world)
	{
		super(type, world);

		this.clientSideTailAnimation = this.rand.nextFloat();
		this.clientSideTailAnimationO = this.clientSideTailAnimation;
	}

	protected Goal createWanderTask()
	{
		final RandomWalkingGoal wander = new EntityAIForcedWander(this, 0.4D, 5);

		wander.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));

		return wander;
	}

	@Override
	protected void registerGoals()
	{
		final MoveTowardsRestrictionGoal moveTowardsRestriction = new MoveTowardsRestrictionGoal(this, 0.4D);

		moveTowardsRestriction.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));

		this.goalSelector.addGoal(1, moveTowardsRestriction);
		this.goalSelector.addGoal(2, this.createWanderTask());

		this.moveController = new FlyingMoveHelper(this);
	}

	@Override
	protected BodyController createBodyController()
	{
		return new EntityBodyHelperFlying(this);
	}

	@OnlyIn(Dist.CLIENT)
	public float getTailAnimation(final float deltaTime)
	{
		return this.clientSideTailAnimationO + (this.clientSideTailAnimation - this.clientSideTailAnimationO) * deltaTime;
	}

	@Override
	public boolean isOnLadder()
	{
		return false;
	}

	@Override
	public float getBlockPathWeight(BlockPos pos, IWorldReader reader)
	{
		return this.world.getBlockState(pos).getMaterial() == Material.AIR ? 10.0F + this.world.getBrightness(pos) - 0.5F : super.getBlockPathWeight(pos);
	}

	@Override
	protected void playStepSound(final BlockPos pos, final BlockState blockIn)
	{

	}

	@Override
	protected void jump()
	{

	}

	@Override
	public void fall(final float distance, final float damageMultiplier)
	{

	}

	@Override
	protected void updateFallState(final double y, final boolean onGroundIn, final BlockState state, final BlockPos pos)
	{

	}

	@Override
	protected void registerData()
	{
		super.registerData();

		this.dataManager.register(EntityFlying.IS_MOVING, false);
	}

	@Override
	protected void registerAttributes()
	{
		super.registerAttributes();

		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
	}

	@Override
	public void tick()
	{
		super.tick();

		this.fallDistance = 0.0F;

		if (this.onGround)
		{
			this.setMotion(this.getMotion().getX(), this.getMotion().getY() + 0.1D, this.getMotion().getZ());
			//this.motionX += (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
			//this.motionZ += (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
			this.rotationYaw = this.rand.nextFloat() * 360.0F;
			this.onGround = false;
			this.isAirBorne = true;
		}

		if (this.collidedHorizontally || this.collidedVertically || !this.isNotColliding())
		{
			double motionX = this.getMotion().getX() + (this.rand.nextBoolean() ? 1.0F : -1.0F) * (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.2F);
			double motionZ = this.getMotion().getZ() + (this.rand.nextBoolean() ? 1.0F : -1.0F) * (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.2F);

			this.setMotion(motionX, this.getMotion().getY(), motionZ);
		}

		if (this.world.isRemote())
		{
			if (this.isMoving())
			{
				if (this.clientSideTailAnimationSpeed < 0.5F)
				{
					this.clientSideTailAnimationSpeed = 4.0F;
				}
				else
				{
					this.clientSideTailAnimationSpeed += (0.5F - this.clientSideTailAnimationSpeed) * 0.1F;
				}
			}
			else
			{
				this.clientSideTailAnimationSpeed += (0.125F - this.clientSideTailAnimationSpeed) * 0.2F;
			}

			this.clientSideTailAnimation += this.clientSideTailAnimationSpeed;
		}
	}

	@Override
	protected PathNavigator createNavigator(final World worldIn)
	{
		return new PathNavigateFlyer(this, worldIn);
	}

	public boolean isMoving()
	{
		return this.dataManager.get(EntityFlying.IS_MOVING);
	}

	protected void setMoving(final boolean moving)
	{
		this.dataManager.set(EntityFlying.IS_MOVING, moving);
	}

	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	public boolean isNotColliding()
	{
		return this.world.checkNoEntityCollision(this.getBoundingBox(), this)
				&& this.world.getCollisionBoxes(this, this.getBoundingBox()).isEmpty();
	}

	@Override
	public int getVerticalFaceSpeed()
	{
		return 1;
	}

	@Override
	public int getHorizontalFaceSpeed()
	{
		return 1;
	}

	@Override
	public void travel(Vec3d vec)
	{
		if (this.isServerWorld())
		{
			super.moveRelative(0.1F, vec);

			this.move(MoverType.SELF, this.getMotion());
			this.setMotion(this.getMotion().mul(0.9D, 0.9D, 0.9D));
		}
		else
		{
			super.travel(vec);
		}
	}

	@Override
	public boolean canDespawn(double closestDistance)
	{
		return false;
	}

}
