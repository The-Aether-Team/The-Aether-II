package com.gildedgames.aether.common.entities.util.flying;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.init.Blocks;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityFlying extends EntityCreature
{

	private static final DataParameter<Boolean> IS_MOVING = EntityDataManager.createKey(EntityFlying.class, DataSerializers.BOOLEAN);

	private float clientSideTailAnimationSpeed;

	private float clientSideTailAnimation;

	private float clientSideTailAnimationO;

	public EntityFlying(World world)
	{
		super(world);

		this.setSize(0.85F, 0.85F);
		this.moveHelper = new FlyingMoveHelper(this);

		this.clientSideTailAnimation = this.rand.nextFloat();
		this.clientSideTailAnimationO = this.clientSideTailAnimation;
	}

	@Override
	protected void initEntityAI()
	{
		EntityAIMoveTowardsRestriction moveTowardsRestriction = new EntityAIMoveTowardsRestriction(this, 0.4D);
		EntityAIWander wander = new EntityAIWander(this, 0.4D, 3);

		wander.setMutexBits(3);
		moveTowardsRestriction.setMutexBits(3);

		this.tasks.addTask(0, moveTowardsRestriction);
		this.tasks.addTask(1, wander);
	}

	@Override
	protected EntityBodyHelper createBodyHelper()
	{
		return new EntityBodyHelperFlying(this);
	}

	@SideOnly(Side.CLIENT)
	public float getTailAnimation(float deltaTime)
	{
		return this.clientSideTailAnimationO + (this.clientSideTailAnimation - this.clientSideTailAnimationO) * deltaTime;
	}

	public boolean isOnLadder()
	{
		return false;
	}

	@Override
	public float getBlockPathWeight(BlockPos pos)
	{
		return this.worldObj.getBlockState(pos.down()).getBlock() == Blocks.AIR ? 10.0F : this.worldObj.getLightBrightness(pos) - 0.5F;
	}

	@Override
	public boolean getCanSpawnHere()
	{
		IBlockState state = this.worldObj.getBlockState((new BlockPos(this)).down());

		return state.canEntitySpawn(this) && this.getBlockPathWeight(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ)) >= 0.0F;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
	{

	}

	@Override
	protected void jump()
	{

	}

	@Override
	public void fall(float distance, float damageMultiplier)
	{

	}

	@Override
	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos)
	{

	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		this.dataManager.register(EntityFlying.IS_MOVING, false);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		this.fallDistance = 0.0F;

		if (this.onGround)
		{
			this.motionY += 0.10D;
			//this.motionX += (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
			//this.motionZ += (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
			this.rotationYaw = this.rand.nextFloat() * 360.0F;
			this.onGround = false;
			this.isAirBorne = true;
		}

		if (this.worldObj.isRemote)
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

	protected PathNavigate getNewNavigator(World worldIn)
	{
		return new PathNavigateFlyer(this, worldIn);
	}

	public boolean isMoving()
	{
		return this.dataManager.get(EntityFlying.IS_MOVING);
	}

	protected void setMoving(boolean moving)
	{
		this.dataManager.set(EntityFlying.IS_MOVING, moving);
	}

	protected boolean canTriggerWalking()
	{
		return false;
	}

	public boolean isNotColliding()
	{
		return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), this) && this.worldObj.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty();
	}

	public int getVerticalFaceSpeed()
	{
		return 1;
	}

	public int getHorizontalFaceSpeed()
	{
		return 1;
	}

	public void moveEntityWithHeading(float strafe, float forward)
	{
		if (this.isServerWorld())
		{
			this.moveRelative(strafe, forward, 0.1F);
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.8999999761581421D;
			this.motionY *= 0.8999999761581421D;
			this.motionZ *= 0.8999999761581421D;
		}
		else
		{
			super.moveEntityWithHeading(strafe, forward);
		}
	}

}
