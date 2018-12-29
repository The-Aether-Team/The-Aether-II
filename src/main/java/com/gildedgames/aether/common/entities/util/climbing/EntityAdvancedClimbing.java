package com.gildedgames.aether.common.entities.util.climbing;

import com.gildedgames.aether.common.entities.living.passive.EntityAetherAnimal;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public abstract class EntityAdvancedClimbing extends EntityAetherAnimal
{
	private boolean climbing;

	private final List<BlockPos> fullBlocksBeside = Lists.newArrayList();

	private BlockPos current;

	public EntityAdvancedClimbing(World world)
	{
		super(world);

		this.setSize(1.0F, 1.0F);

		this.moveHelper = new AdvancedClimbingMoveHelper(this);
	}

	public List<BlockPos> getFullBlocksBeside()
	{
		return this.fullBlocksBeside;
	}

	public BlockPos getCurrent()
	{
		this.refreshLatching();

		return this.current;
	}

	@Override
	public float getBlockPathWeight(BlockPos pos)
	{
		BlockPos d = pos.down();
		BlockPos u = pos.up();
		BlockPos n = pos.north();
		BlockPos s = pos.south();
		BlockPos e = pos.east();
		BlockPos w = pos.west();
		BlockPos north_east = pos.north().east();
		BlockPos north_west = pos.north().west();
		BlockPos south_east = pos.south().east();
		BlockPos south_west = pos.south().west();

		BlockPos down_north = pos.down().north();
		BlockPos down_north_east = pos.down().north().east();
		BlockPos down_north_west = pos.down().north().west();
		BlockPos down_east = pos.down().east();
		BlockPos down_west = pos.down().west();
		BlockPos down_south_east = pos.down().south().east();
		BlockPos down_south_west = pos.down().south().west();
		BlockPos down_south = pos.down().south();

		BlockPos up_north = pos.up().north();
		BlockPos up_north_east = pos.up().north().east();
		BlockPos up_north_west = pos.up().north().west();
		BlockPos up_east = pos.up().east();
		BlockPos up_west = pos.up().west();
		BlockPos up_south_east = pos.up().south().east();
		BlockPos up_south_west = pos.up().south().west();
		BlockPos up_south = pos.up().south();

		boolean north_east_valid = this.world.getBlockState(north_east).isFullBlock();
		boolean north_west_valid = this.world.getBlockState(north_west).isFullBlock();
		boolean south_east_valid = this.world.getBlockState(south_east).isFullBlock();
		boolean south_west_valid = this.world.getBlockState(south_west).isFullBlock();

		boolean down_north_valid = this.world.getBlockState(down_north).isFullBlock();
		boolean down_north_east_valid = this.world.getBlockState(down_north_east).isFullBlock();
		boolean down_north_west_valid = this.world.getBlockState(down_north_west).isFullBlock();
		boolean down_east_valid = this.world.getBlockState(down_east).isFullBlock();
		boolean down_west_valid = this.world.getBlockState(down_west).isFullBlock();
		boolean down_south_east_valid = this.world.getBlockState(down_south_east).isFullBlock();
		boolean down_south_west_valid = this.world.getBlockState(down_south_west).isFullBlock();
		boolean down_south_valid = this.world.getBlockState(down_south).isFullBlock();

		boolean up_north_valid = this.world.getBlockState(up_north).isFullBlock();
		boolean up_north_east_valid = this.world.getBlockState(up_north_east).isFullBlock();
		boolean up_north_west_valid = this.world.getBlockState(up_north_west).isFullBlock();
		boolean up_east_valid = this.world.getBlockState(up_east).isFullBlock();
		boolean up_west_valid = this.world.getBlockState(up_west).isFullBlock();
		boolean up_south_east_valid = this.world.getBlockState(up_south_east).isFullBlock();
		boolean up_south_west_valid = this.world.getBlockState(up_south_west).isFullBlock();
		boolean up_south_valid = this.world.getBlockState(up_south).isFullBlock();

		boolean down = this.world.getBlockState(d).isSideSolid(this.world, d, EnumFacing.UP);
		boolean up = this.world.getBlockState(u).isSideSolid(this.world, u, EnumFacing.DOWN);

		boolean north = this.world.getBlockState(n).isSideSolid(this.world, n, EnumFacing.SOUTH);
		boolean south = this.world.getBlockState(s).isSideSolid(this.world, s, EnumFacing.NORTH);

		boolean east = this.world.getBlockState(e).isSideSolid(this.world, e, EnumFacing.WEST);
		boolean west = this.world.getBlockState(w).isSideSolid(this.world, w, EnumFacing.EAST);

		return down || up || north || south || east || west || north_east_valid || north_west_valid || south_east_valid || south_west_valid || down_north_valid
				|| down_north_east_valid || down_north_west_valid || down_east_valid || down_west_valid || down_south_east_valid || down_south_west_valid
				|| down_south_valid || up_north_valid || up_north_east_valid || up_north_west_valid || up_east_valid || up_west_valid || up_south_east_valid
				|| up_south_west_valid || up_south_valid ? 10.0F : this.world.getLightBrightness(pos) - 0.5F;
	}

	@Override
	public boolean isOnLadder()
	{
		return false;
	}

	@Override
	public void fall(final float distance, final float damageMultiplier)
	{
		super.fall(distance, damageMultiplier);
	}

	@Override
	protected void updateFallState(final double y, final boolean onGroundIn, final IBlockState state, final BlockPos pos)
	{
		super.updateFallState(y, onGroundIn, state, pos);
	}

	private void refreshLatching()
	{
		if (this.current == null || !this.current.equals(this.getPosition()))
		{
			this.current = this.getPosition();
			this.fullBlocksBeside.clear();

			for (BlockPos.MutableBlockPos pos : BlockPos.getAllInBoxMutable(this.current.north().east(), this.current.south().west()))
			{
				if (!pos.equals(this.getPosition()) && this.world.getBlockState(pos).isFullBlock())
				{
					this.fullBlocksBeside.add(pos.toImmutable());
				}
			}
		}
	}

	@Override
	public void onUpdate()
	{
		this.refreshLatching();

		if (!this.getFullBlocksBeside().isEmpty())
		{
			this.setNoGravity(true);
		}

		super.onUpdate();

		if (this.isClimbing())
		{
			this.fallDistance = 0.0F;
		}
	}

	@Override
	protected PathNavigate createNavigator(final World worldIn)
	{
		return new PathNavigateAdvancedClimbing(this, worldIn);
	}

	public boolean isClimbing()
	{
		return this.climbing;
	}

	public void setClimbing(boolean flag)
	{
		this.climbing = flag;
	}

	@Override
	public void travel(float strafe, float vertical, float forward)
	{
		/*if (this.isServerWorld())
		{
			super.moveRelative(0, vertical, 0, 0.1F);
			this.move(MoverType.SELF, 0, this.motionY, 0);

			this.motionY *= 0.8999999761581421D;
		}*/

		super.travel(strafe, vertical, forward);
	}
}
