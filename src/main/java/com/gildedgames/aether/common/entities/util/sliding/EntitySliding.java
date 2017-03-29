package com.gildedgames.aether.common.entities.util.sliding;

import com.gildedgames.aether.common.entities.util.flying.PathNavigateFlyer;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntitySliding extends EntityCreature implements ISlidingEntity
{

	public EntitySliding(World worldIn)
	{
		super(worldIn);

		this.moveHelper = new SlidingHorizontalMoveHelper(this, this);
	}

	@Override
	protected PathNavigate createNavigator(World worldIn)
	{
		return new PathNavigateFlyer(this, worldIn);
	}

	@Override
	public float getBlockPathWeight(BlockPos pos)
	{
		return this.world.getBlockState(pos.down()).getBlock() == Blocks.AIR ? 10.0F : this.world.getLightBrightness(pos) - 0.5F;
	}

	@Override
	public boolean getCanSpawnHere()
	{
		IBlockState state = this.world.getBlockState((new BlockPos(this)).down());

		return state.canEntitySpawn(this)
				&& this.getBlockPathWeight(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ)) >= 0.0F;
	}

	@Override
	public void onUpdate()
	{
		this.jumpMovementFactor = 0.0F;
		this.renderYawOffset = this.rotationPitch = this.rotationYaw = 0.0F;

		super.onUpdate();

		this.jumpMovementFactor = 0.0F;
		this.renderYawOffset = this.rotationPitch = this.rotationYaw = 0.0F;
	}

	@Override
	public void onLivingUpdate()
	{
		this.jumpMovementFactor = 0.0F;
		this.renderYawOffset = this.rotationPitch = this.rotationYaw = 0.0F;

		super.onLivingUpdate();
	}

	@Override
	public boolean hasNoGravity()
	{
		return true;
	}

	@Override
	public void fall(float distance, float damageMultiplier)
	{

	}

	@Override
	public void addVelocity(double x, double y, double z)
	{

	}

	@Override
	public void setVelocity(double x, double y, double z)
	{

	}

	@Override
	public boolean isOnLadder()
	{
		return false;
	}

	@Override
	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos)
	{

	}

	@Override
	public boolean canBePushed()
	{
		return false;
	}

	@Override
	protected void jump()
	{

	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
	{

	}

	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	public boolean shouldSlide()
	{
		return false;
	}

	@Override
	public void onSlide()
	{

	}

	@Override
	public void onSliding()
	{

	}

	@Override
	public void onStartSlideCooldown(SlidingHorizontalMoveHelper.Direction direction)
	{

	}

	@Override
	public int getSlideCooldown()
	{
		return 0;
	}

}
