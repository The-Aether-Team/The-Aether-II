package com.gildedgames.aether.common.entities.util.climbing;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PathNavigateAdvancedClimbing extends PathNavigate
{
	private boolean shouldAvoidSun;

	private final EntityAdvancedClimbing entity;

	public PathNavigateAdvancedClimbing(EntityAdvancedClimbing entity, World worldIn)
	{
		super(entity, worldIn);

		this.entity = entity;
	}

	@Override
	protected PathFinder getPathFinder()
	{
		this.nodeProcessor = new AdvancedClimbingNodeProcessor();
		this.nodeProcessor.setCanEnterDoors(true);
		return new PathFinder(this.nodeProcessor);
	}

	/**
	 * If on ground or swimming and can swim
	 */
	@Override
	protected boolean canNavigate()
	{
		return true;
	}

	@Override
	protected Vec3d getEntityPosition()
	{
		return new Vec3d(this.entity.posX, (double) this.getPathablePosY(), this.entity.posZ);
	}

	/**
	 * Returns path to given BlockPos
	 */
	@Override
	public Path getPathToPos(BlockPos pos)
	{
		if (this.world.getBlockState(pos).getMaterial() == Material.AIR)
		{
			BlockPos blockpos = pos.down();

			while (blockpos.getY() > 0 && this.world.getBlockState(blockpos).getMaterial() == Material.AIR)
			{
				blockpos = blockpos.down();
			}

			if (blockpos.getY() > 0)
			{
				return super.getPathToPos(blockpos.up());
			}

			while (blockpos.getY() < this.world.getHeight() && this.world.getBlockState(blockpos).getMaterial() == Material.AIR)
			{
				blockpos = blockpos.up();
			}

			pos = blockpos;
		}

		if (!this.world.getBlockState(pos).getMaterial().isSolid())
		{
			return super.getPathToPos(pos);
		}
		else
		{
			BlockPos blockpos1 = pos.up();

			while (blockpos1.getY() < this.world.getHeight() && this.world.getBlockState(blockpos1).getMaterial().isSolid())
			{
				blockpos1 = blockpos1.up();
			}

			return super.getPathToPos(blockpos1);
		}
	}

	/**
	 * Returns the path to the given EntityLiving. Args : entity
	 */
	@Override
	public Path getPathToEntityLiving(Entity entityIn)
	{
		return this.getPathToPos(new BlockPos(entityIn));
	}

	/**
	 * Gets the safe pathing Y position for the entity depending on if it can path swim or not
	 */
	private int getPathablePosY()
	{
		if (this.entity.isInWater() && this.getCanSwim())
		{
			int i = (int) this.entity.getEntityBoundingBox().minY;
			Block block = this.world.getBlockState(new BlockPos(MathHelper.floor(this.entity.posX), i, MathHelper.floor(this.entity.posZ))).getBlock();
			int j = 0;

			while (block == Blocks.FLOWING_WATER || block == Blocks.WATER)
			{
				++i;
				block = this.world.getBlockState(new BlockPos(MathHelper.floor(this.entity.posX), i, MathHelper.floor(this.entity.posZ))).getBlock();
				++j;

				if (j > 16)
				{
					return (int) this.entity.getEntityBoundingBox().minY;
				}
			}

			return i;
		}
		else
		{
			return (int) (this.entity.getEntityBoundingBox().minY + 0.5D);
		}
	}

	/**
	 * Trims path data from the end to the first sun covered block
	 */
	@Override
	protected void removeSunnyPath()
	{
		super.removeSunnyPath();

		if (this.shouldAvoidSun)
		{
			if (this.world.canSeeSky(new BlockPos(MathHelper.floor(this.entity.posX), (int) (this.entity.getEntityBoundingBox().minY + 0.5D),
					MathHelper.floor(this.entity.posZ))))
			{
				return;
			}

			for (int i = 0; i < this.currentPath.getCurrentPathLength(); ++i)
			{
				PathPoint pathpoint = this.currentPath.getPathPointFromIndex(i);

				if (this.world.canSeeSky(new BlockPos(pathpoint.x, pathpoint.y, pathpoint.z)))
				{
					this.currentPath.setCurrentPathLength(i - 1);
					return;
				}
			}
		}
	}

	@Override
	public void onUpdateNavigation()
	{
		++this.totalTicks;

		if (this.tryUpdatePath)
		{
			this.updatePath();
		}

		if (!this.noPath())
		{
			if (this.canNavigate())
			{
				this.pathFollow();
			}
			else if (this.currentPath != null && this.currentPath.getCurrentPathIndex() < this.currentPath.getCurrentPathLength())
			{
				Vec3d vec3d = this.getEntityPosition();
				Vec3d vec3d1 = this.currentPath.getVectorFromIndex(this.entity, this.currentPath.getCurrentPathIndex());

				if (vec3d.y >= vec3d1.y && !this.entity.onGround && MathHelper.floor(vec3d.x) == MathHelper.floor(vec3d1.x)
						&& MathHelper.floor(vec3d.z) == MathHelper.floor(vec3d1.z))
				{
					this.currentPath.setCurrentPathIndex(this.currentPath.getCurrentPathIndex() + 1);
				}
			}

			this.debugPathFinding();

			if (!this.noPath())
			{
				Vec3d vec3d2 = this.currentPath.getPosition(this.entity);
				BlockPos blockpos = (new BlockPos(vec3d2)).down();
				AxisAlignedBB axisalignedbb = this.world.getBlockState(blockpos).getBoundingBox(this.world, blockpos);
				vec3d2 = vec3d2.subtract(0.0D, 1.0D - axisalignedbb.maxY, 0.0D);
				this.entity.getMoveHelper().setMoveTo(vec3d2.x, vec3d2.y, vec3d2.z, this.speed);
			}
		}
	}

	/*@Override
	protected boolean isDirectPathBetweenPoints(final Vec3d posVec31, final Vec3d posVec32, final int sizeX, final int sizeY, final int sizeZ)
	{
		RayTraceResult raytraceresult = this.world
				.rayTraceBlocks(posVec31, new Vec3d(posVec32.x, posVec32.y + (double) this.entity.height * 0.5D, posVec32.z), false, true, false);
		return raytraceresult == null || raytraceresult.typeOfHit == RayTraceResult.Type.MISS;
	}*/

	/**
	 * Checks if the specified entity can safely walk to the specified location.
	 */
	@Override
	protected boolean isDirectPathBetweenPoints(Vec3d posVec31, Vec3d posVec32, int sizeX, int sizeY, int sizeZ)
	{
		int startVecFlooredX = MathHelper.floor(posVec31.x);
		int startVecFlooredY = MathHelper.floor(posVec31.y);
		int startVecFlooredZ = MathHelper.floor(posVec31.z);

		double xDif = posVec32.x - posVec31.x;
		double yDif = posVec32.y - posVec31.y;
		double zDif = posVec32.z - posVec31.z;

		double sqDist = xDif * xDif + yDif * yDif + zDif * zDif;

		if (sqDist < 1.0E-8D)
		{
			return false;
		}
		else
		{
			double d3 = 1.0D / Math.sqrt(sqDist);

			xDif = xDif * d3;
			yDif = yDif * d3;
			zDif = zDif * d3;

			sizeX = sizeX + 2;
			sizeY = sizeY + 2;
			sizeZ = sizeZ + 2;

			if (!this.isSafeToStandAt(startVecFlooredX, startVecFlooredY, startVecFlooredZ, sizeX, sizeY, sizeZ, posVec31, xDif, yDif, zDif))
			{
				return false;
			}
			else
			{
				sizeX = sizeX - 2;
				sizeY = sizeY - 2;
				sizeZ = sizeZ - 2;

				double xStepProgressIncrement = 1.0D / Math.abs(xDif);
				double yStepProgressIncrement = 1.0D / Math.abs(yDif);
				double zStepProgressIncrement = 1.0D / Math.abs(zDif);

				double xStepPos = (double) startVecFlooredX - posVec31.x;
				double yStepPos = (double) startVecFlooredY - posVec31.y;
				double zStepPos = (double) startVecFlooredZ - posVec31.z;

				if (xDif >= 0.0D)
				{
					++xStepPos;
				}

				if (yDif >= 0.0D)
				{
					++yStepPos;
				}

				if (zDif >= 0.0D)
				{
					++zStepPos;
				}

				xStepPos = xStepPos / xDif;
				yStepPos = yStepPos / yDif;
				zStepPos = zStepPos / zDif;

				int xStepNegMod = xDif < 0.0D ? -1 : 1;
				int yStepNegMod = yDif < 0.0D ? -1 : 1;
				int zStepNegMod = zDif < 0.0D ? -1 : 1;

				int targetVecFlooredX = MathHelper.floor(posVec32.x);
				int targetVecFlooredY = MathHelper.floor(posVec32.y);
				int targetVecFlooredZ = MathHelper.floor(posVec32.z);

				int xDifFloored = targetVecFlooredX - startVecFlooredX;
				int yDifFloored = targetVecFlooredY - startVecFlooredY;
				int zDifFloored = targetVecFlooredZ - startVecFlooredZ;

				while (xDifFloored * xStepNegMod > 0 || yDifFloored * yStepNegMod > 0 || zDifFloored * zStepNegMod > 0)
				{
					if (xStepPos < zStepPos)
					{
						xStepPos += xStepProgressIncrement;
						startVecFlooredX += xStepNegMod;
						xDifFloored = targetVecFlooredX - startVecFlooredX;
					}
					else if (zStepPos < yStepPos)
					{
						zStepPos += zStepProgressIncrement;
						startVecFlooredZ += zStepNegMod;
						zDifFloored = targetVecFlooredZ - startVecFlooredZ;
					}
					else
					{
						yStepPos += yStepProgressIncrement;
						startVecFlooredY += yStepNegMod;
						yDifFloored = targetVecFlooredY - startVecFlooredY;
					}

					if (!this.isSafeToStandAt(startVecFlooredX, startVecFlooredY, startVecFlooredZ, sizeX, sizeY, sizeZ, posVec31, xDif, yDif, zDif))
					{
						return false;
					}
				}

				return true;
			}
		}
	}

	/**
	 * Returns true when an entity could stand at a position, including solid blocks under the entire entity.
	 */
	private boolean isSafeToStandAt(int x, int y, int z, int sizeX, int sizeY, int sizeZ, Vec3d vec31, double xDif, double yDif, double zDif)
	{
		int i = x - sizeX / 2;
		int j = y - sizeY / 2;
		int k = z - sizeZ / 2;

		if (!this.isPositionClear(i, j, k, sizeX, sizeY, sizeZ, vec31, xDif, yDif, zDif))
		{
			return false;
		}
		else
		{
			for (int l = i; l < i + sizeX; ++l)
			{
				for (int m = j; m < j + sizeY; ++m)
				{
					for (int o = k; o < k + sizeZ; ++o)
					{
						double d0 = (double) l + 0.5D - vec31.x;
						double d1 = (double) m + 0.5D - vec31.y;
						double d2 = (double) o + 0.5D - vec31.z;

						if (d0 * xDif + d1 * yDif + d2 * zDif >= 0.0D)
						{
							PathNodeType downNode = this.nodeProcessor.getPathNodeType(this.world, l, k - 1, k, this.entity, sizeX, sizeY, sizeZ, true, true);
							PathNodeType upNode = this.nodeProcessor.getPathNodeType(this.world, l, k + 1, k, this.entity, sizeX, sizeY, sizeZ, true, true);
							PathNodeType northNode = this.nodeProcessor.getPathNodeType(this.world, l + 1, k, k, this.entity, sizeX, sizeY, sizeZ, true, true);
							PathNodeType southNode = this.nodeProcessor.getPathNodeType(this.world, l - 1, k, k, this.entity, sizeX, sizeY, sizeZ, true, true);
							PathNodeType eastNode = this.nodeProcessor.getPathNodeType(this.world, l, k, k + 1, this.entity, sizeX, sizeY, sizeZ, true, true);
							PathNodeType westNode = this.nodeProcessor.getPathNodeType(this.world, l, k, k - 1, this.entity, sizeX, sizeY, sizeZ, true, true);
							PathNodeType northEastNode = this.nodeProcessor
									.getPathNodeType(this.world, l + 1, k, k + 1, this.entity, sizeX, sizeY, sizeZ, true, true);
							PathNodeType northWestNode = this.nodeProcessor
									.getPathNodeType(this.world, l + 1, k, k - 1, this.entity, sizeX, sizeY, sizeZ, true, true);
							PathNodeType southEastNode = this.nodeProcessor
									.getPathNodeType(this.world, l - 1, k, k + 1, this.entity, sizeX, sizeY, sizeZ, true, true);
							PathNodeType southWestNode = this.nodeProcessor
									.getPathNodeType(this.world, l - 1, k, k - 1, this.entity, sizeX, sizeY, sizeZ, true, true);

							PathNodeType downEastNode = this.nodeProcessor
									.getPathNodeType(this.world, l, k - 1, k + 1, this.entity, sizeX, sizeY, sizeZ, true, true);
							PathNodeType downWestNode = this.nodeProcessor
									.getPathNodeType(this.world, l, k - 1, k - 1, this.entity, sizeX, sizeY, sizeZ, true, true);
							PathNodeType downSouthNode = this.nodeProcessor
									.getPathNodeType(this.world, l - 1, k - 1, k, this.entity, sizeX, sizeY, sizeZ, true, true);
							PathNodeType downNorthNode = this.nodeProcessor
									.getPathNodeType(this.world, l + 1, k - 1, k, this.entity, sizeX, sizeY, sizeZ, true, true);
							PathNodeType downNorthEastNode = this.nodeProcessor
									.getPathNodeType(this.world, l + 1, k - 1, k + 1, this.entity, sizeX, sizeY, sizeZ, true, true);
							PathNodeType downNorthWestNode = this.nodeProcessor
									.getPathNodeType(this.world, l + 1, k - 1, k - 1, this.entity, sizeX, sizeY, sizeZ, true, true);
							PathNodeType downSouthEastNode = this.nodeProcessor
									.getPathNodeType(this.world, l - 1, k - 1, k + 1, this.entity, sizeX, sizeY, sizeZ, true, true);
							PathNodeType downSouthWestNode = this.nodeProcessor
									.getPathNodeType(this.world, l - 1, k - 1, k - 1, this.entity, sizeX, sizeY, sizeZ, true, true);

							PathNodeType upEastNode = this.nodeProcessor
									.getPathNodeType(this.world, l, k + 1, k + 1, this.entity, sizeX, sizeY, sizeZ, true, true);
							PathNodeType upWestNode = this.nodeProcessor
									.getPathNodeType(this.world, l, k + 1, k - 1, this.entity, sizeX, sizeY, sizeZ, true, true);
							PathNodeType upSouthNode = this.nodeProcessor
									.getPathNodeType(this.world, l - 1, k + 1, k, this.entity, sizeX, sizeY, sizeZ, true, true);
							PathNodeType upNorthNode = this.nodeProcessor
									.getPathNodeType(this.world, l + 1, k + 1, k, this.entity, sizeX, sizeY, sizeZ, true, true);
							PathNodeType upNorthEastNode = this.nodeProcessor
									.getPathNodeType(this.world, l + 1, k + 1, k + 1, this.entity, sizeX, sizeY, sizeZ, true, true);
							PathNodeType upNorthWestNode = this.nodeProcessor
									.getPathNodeType(this.world, l + 1, k + 1, k - 1, this.entity, sizeX, sizeY, sizeZ, true, true);
							PathNodeType upSouthEastNode = this.nodeProcessor
									.getPathNodeType(this.world, l - 1, k + 1, k + 1, this.entity, sizeX, sizeY, sizeZ, true, true);
							PathNodeType upSouthWestNode = this.nodeProcessor
									.getPathNodeType(this.world, l - 1, k + 1, k - 1, this.entity, sizeX, sizeY, sizeZ, true, true);

							if (!this.checkNodeValid(upNode) && !this.checkNodeValid(downNode) && !this.checkNodeValid(northNode) && !this
									.checkNodeValid(southNode)
									&& !this.checkNodeValid(westNode) && !this.checkNodeValid(eastNode) && !this.checkNodeValid(northEastNode) && !this
									.checkNodeValid(northWestNode) && !this
									.checkNodeValid(southEastNode) && !this
									.checkNodeValid(southWestNode) && !this
									.checkNodeValid(downEastNode) && !this
									.checkNodeValid(downWestNode) && !this
									.checkNodeValid(downSouthNode) && !this
									.checkNodeValid(downNorthNode) && !this
									.checkNodeValid(downNorthEastNode) && !this
									.checkNodeValid(downNorthWestNode) && !this
									.checkNodeValid(downSouthEastNode) && !this
									.checkNodeValid(downSouthWestNode) && !this
									.checkNodeValid(upEastNode) && !this
									.checkNodeValid(upWestNode) && !this
									.checkNodeValid(upSouthNode) && !this
									.checkNodeValid(upNorthNode) && !this
									.checkNodeValid(upNorthEastNode) && !this
									.checkNodeValid(upNorthWestNode) && !this
									.checkNodeValid(upSouthEastNode) && !this
									.checkNodeValid(upSouthWestNode))
							{
								return false;
							}
						}
					}
				}
			}

			return true;
		}
	}

	private boolean checkNodeValid(PathNodeType node)
	{
		if (node == PathNodeType.WATER)
		{
			return false;
		}

		if (node == PathNodeType.LAVA)
		{
			return false;
		}

		if (node == PathNodeType.OPEN)
		{
			return false;
		}

		float f = this.entity.getPathPriority(node);

		if (f < 0.0F || f >= 8.0F)
		{
			return false;
		}

		return node != PathNodeType.DAMAGE_FIRE && node != PathNodeType.DANGER_FIRE && node != PathNodeType.DAMAGE_OTHER;
	}

	/**
	 * Returns true if an entity does not collide with any solid blocks at the position.
	 */
	private boolean isPositionClear(int x, int y, int z, int sizeX, int sizeY, int sizeZ, Vec3d vec3d, double xDif, double yDif, double zDif)
	{
		for (BlockPos blockpos : BlockPos.getAllInBox(new BlockPos(x, y, z), new BlockPos(x + sizeX - 1, y + sizeY - 1, z + sizeZ - 1)))
		{
			double d0 = (double) blockpos.getX() + 0.5D - vec3d.x;
			double d2 = (double) blockpos.getZ() + 0.5D - vec3d.z;

			if (d0 * xDif + d2 * zDif >= 0.0D)
			{
				Block block = this.world.getBlockState(blockpos).getBlock();

				if (!block.isPassable(this.world, blockpos))
				{
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public boolean canEntityStandOnPos(BlockPos pos)
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
				|| up_south_west_valid || up_south_valid;
	}

	public void setBreakDoors(boolean canBreakDoors)
	{
		this.nodeProcessor.setCanOpenDoors(canBreakDoors);
	}

	public boolean getEnterDoors()
	{
		return this.nodeProcessor.getCanEnterDoors();
	}

	public void setEnterDoors(boolean enterDoors)
	{
		this.nodeProcessor.setCanEnterDoors(enterDoors);
	}

	public boolean getCanSwim()
	{
		return this.nodeProcessor.getCanSwim();
	}

	public void setCanSwim(boolean canSwim)
	{
		this.nodeProcessor.setCanSwim(canSwim);
	}

	public void setAvoidSun(boolean avoidSun)
	{
		this.shouldAvoidSun = avoidSun;
	}
}