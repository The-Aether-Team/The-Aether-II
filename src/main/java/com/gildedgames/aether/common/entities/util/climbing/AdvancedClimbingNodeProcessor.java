package com.gildedgames.aether.common.entities.util.climbing;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class AdvancedClimbingNodeProcessor extends NodeProcessor
{
	protected float avoidsWater;

	private EntityAdvancedClimbing climber;

	public void initClimber(EntityAdvancedClimbing climber)
	{
		this.climber = climber;
	}

	@Override
	public void init(IBlockAccess sourceIn, EntityLiving mob)
	{
		if (mob instanceof EntityAdvancedClimbing)
		{
			this.initClimber((EntityAdvancedClimbing) mob);
		}

		super.init(sourceIn, mob);
		this.avoidsWater = mob.getPathPriority(PathNodeType.WATER);
	}

	@Override
	public void postProcess()
	{
		this.entity.setPathPriority(PathNodeType.WATER, this.avoidsWater);
		super.postProcess();
	}

	@Override
	public PathPoint getStart()
	{
		int i;

		if (this.getCanSwim() && this.entity.isInWater())
		{
			i = (int) this.entity.getEntityBoundingBox().minY;
			BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(MathHelper.floor(this.entity.posX), i,
					MathHelper.floor(this.entity.posZ));

			for (Block block = this.blockaccess.getBlockState(blockpos$mutableblockpos).getBlock();
				 block == Blocks.FLOWING_WATER || block == Blocks.WATER; block = this.blockaccess.getBlockState(blockpos$mutableblockpos).getBlock())
			{
				++i;
				blockpos$mutableblockpos.setPos(MathHelper.floor(this.entity.posX), i, MathHelper.floor(this.entity.posZ));
			}
		}
		else if (this.entity.onGround)
		{
			i = MathHelper.floor(this.entity.getEntityBoundingBox().minY + 0.5D);
		}
		else if (this.climber.isClimbing())
		{
			i = MathHelper.floor(this.entity.getEntityBoundingBox().minY + 0.5D);
		}
		else
		{
			BlockPos blockpos;

			for (blockpos = new BlockPos(this.entity);
				 (this.blockaccess.getBlockState(blockpos).getMaterial() == Material.AIR || this.blockaccess.getBlockState(blockpos).getBlock()
						 .isPassable(this.blockaccess, blockpos)) && blockpos.getY() > 0; blockpos = blockpos.down())
			{
			}

			i = blockpos.up().getY();
		}

		BlockPos blockpos2 = new BlockPos(this.entity);
		PathNodeType pathnodetype1 = this.getPathNodeType(this.entity, blockpos2.getX(), i, blockpos2.getZ());

		if (this.entity.getPathPriority(pathnodetype1) < 0.0F)
		{
			Set<BlockPos> set = Sets.newHashSet();
			set.add(new BlockPos(this.entity.getEntityBoundingBox().minX, (double) i, this.entity.getEntityBoundingBox().minZ));
			set.add(new BlockPos(this.entity.getEntityBoundingBox().minX, (double) i, this.entity.getEntityBoundingBox().maxZ));
			set.add(new BlockPos(this.entity.getEntityBoundingBox().maxX, (double) i, this.entity.getEntityBoundingBox().minZ));
			set.add(new BlockPos(this.entity.getEntityBoundingBox().maxX, (double) i, this.entity.getEntityBoundingBox().maxZ));

			for (BlockPos blockpos1 : set)
			{
				PathNodeType pathnodetype = this.getPathNodeType(this.entity, blockpos1);

				if (this.entity.getPathPriority(pathnodetype) >= 0.0F)
				{
					return this.openPoint(blockpos1.getX(), blockpos1.getY(), blockpos1.getZ());
				}
			}
		}

		return this.openPoint(blockpos2.getX(), i, blockpos2.getZ());
	}

	/**
	 * Returns PathPoint for given coordinates
	 */
	@Override
	public PathPoint getPathPointToCoords(double x, double y, double z)
	{
		return this.openPoint(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
	}

	private boolean isConnectedToLatchedPosition(PathPoint point)
	{
		return true;

		/*for (BlockPos pos : this.climber.getFullBlocksBeside())
		{
			if ((point.y - 1 == this.climber.getCurrent().getY() || point.y + 1 == this.climber.getCurrent().getY() || point.y == this.climber.getCurrent()
					.getY()) && point.y == pos.getY() && (
					point.x == pos.getX() || point.z == pos.getZ()))
			{
				return true;
			}

			if ((point.y + 1 == pos.getY() || point.y - 1 == pos.getY() || pos.getY() == point.y) && pos.getX() == point.x && pos.getZ() == point.z)
			{
				return true;
			}
		}

		return false;*/
	}

	@Override
	public int findPathOptions(PathPoint[] pathOptions, PathPoint currentPoint, PathPoint targetPoint, float maxDistance)
	{
		int i = 0;
		int blockHeightCanStep = 0;
		PathNodeType pathnodetype = this.getPathNodeType(this.entity, currentPoint.x, currentPoint.y + 1, currentPoint.z);

		if (this.entity.getPathPriority(pathnodetype) >= 0.0F)
		{
			blockHeightCanStep = MathHelper.floor(Math.max(1.0F, this.entity.stepHeight));
		}

		BlockPos currPointDown = (new BlockPos(currentPoint.x, currentPoint.y, currentPoint.z)).down();
		double currentYMaxValue =
				(double) currentPoint.y - (1.0D - this.blockaccess.getBlockState(currPointDown).getBoundingBox(this.blockaccess, currPointDown).maxY);

		boolean[] valid = new boolean[EnumFacing.values().length];

		int facingIndex = 0;

		// NORMAL FACINGS
		for (EnumFacing enumfacing : EnumFacing.values())
		{
			PathPoint pathpoint = this.getSafePoint(currentPoint.x + enumfacing.getXOffset(), currentPoint.y + enumfacing.getYOffset(),
					currentPoint.z + enumfacing.getZOffset(), blockHeightCanStep, currentYMaxValue, enumfacing);

			valid[facingIndex] = pathpoint != null && pathpoint.nodeType == PathNodeType.WALKABLE && this.isConnectedToLatchedPosition(pathpoint);

			if (pathpoint != null && valid[facingIndex] && !pathpoint.visited && pathpoint.distanceTo(targetPoint) < maxDistance)
			{
				pathOptions[i++] = pathpoint;
			}

			facingIndex++;
		}

		List<Pair<EnumFacing, EnumFacing>> pairedFaces = Lists.newArrayList();

		// 3 COMBINED FACINGS
		for (EnumFacing base : EnumFacing.values())
		{
			outer1:
			for (EnumFacing layer1 : EnumFacing.values())
			{
				if (base.getOpposite() == layer1 || base == layer1.getOpposite() || layer1 == base)
				{
					continue;
				}

				for (Pair<EnumFacing, EnumFacing> pair : pairedFaces)
				{
					if ((layer1 == pair.getLeft() && base == pair.getRight()) || (base == pair.getLeft() && layer1 == pair.getRight()))
					{
						continue outer1;
					}
				}

				PathPoint pathpoint = this.getSafePoint(currentPoint.x + base.getXOffset() + layer1.getXOffset(),
						currentPoint.y + base.getYOffset() + layer1.getYOffset(),
						currentPoint.z + base.getZOffset() + layer1.getZOffset(), blockHeightCanStep, currentYMaxValue, base);

				if ((valid[base.ordinal()] || valid[layer1.ordinal()]) && pathpoint != null && this.isConnectedToLatchedPosition(pathpoint)
						&& pathpoint.nodeType == PathNodeType.WALKABLE && !pathpoint.visited
						&& pathpoint.distanceTo(targetPoint) < maxDistance)
				{
					pathOptions[i++] = pathpoint;

					outer2:
					for (EnumFacing layer2 : EnumFacing.values())
					{
						if (layer1.getOpposite() == layer2 || layer2 == base || layer2 == layer1 || base.getOpposite() == layer2)
						{
							continue;
						}

						for (Pair<EnumFacing, EnumFacing> pair : pairedFaces)
						{
							if ((layer1 == pair.getLeft() && layer2 == pair.getRight()) || (layer2 == pair.getLeft() && layer1 == pair.getRight()))
							{
								continue outer2;
							}
						}

						pathpoint = this.getSafePoint(currentPoint.x + base.getXOffset() + layer1.getXOffset() + layer2.getXOffset(),
								currentPoint.y + base.getYOffset() + layer1.getYOffset() + layer2.getYOffset(),
								currentPoint.z + base.getZOffset() + layer1.getZOffset() + layer2.getYOffset(), blockHeightCanStep,
								currentYMaxValue, base);

						if (pathpoint != null && this.isConnectedToLatchedPosition(pathpoint) && pathpoint.nodeType == PathNodeType.WALKABLE
								&& !pathpoint.visited
								&& pathpoint.distanceTo(targetPoint) < maxDistance)
						{
							pathOptions[i++] = pathpoint;

							pairedFaces.add(Pair.of(layer1, layer2));
						}
					}
				}
			}
		}

		return i;
	}

	/**
	 * Returns a point that the entity can safely move to
	 */
	@Nullable
	private PathPoint getSafePoint(int x, int y, int z, int blockHeightCanStep, double currentMaxYValue, EnumFacing facing)
	{
		PathPoint pathpoint = null;

		BlockPos pos = new BlockPos(x, y, z);
		BlockPos downPos = pos.down();

		double downCurrentMaxYValue = (double) y - (1.0D - this.blockaccess.getBlockState(downPos).getBoundingBox(this.blockaccess, downPos).maxY);

		//TODO: Check - not sure I understand this yet
		if (downCurrentMaxYValue - currentMaxYValue > 1.125D)
		{
			return null;
		}
		else
		{
			PathNodeType node = this.getPathNodeType(this.entity, x, y, z);
			float priority = this.entity.getPathPriority(node);
			double halfEntityWidth = (double) this.entity.width / 2.0D;

			if (priority >= 0.0F)
			{
				pathpoint = this.openPoint(x, y, z);
				pathpoint.nodeType = node;
				pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);

				PathNodeType nodeDown = this.getPathNodeType(this.entity, x, y - 1, z);

				if (nodeDown == PathNodeType.BLOCKED && pathpoint.nodeType == PathNodeType.OPEN)
				{
					pathpoint.nodeType = PathNodeType.WALKABLE;
					pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
					return pathpoint;
				}

				PathNodeType nodeUp = this.getPathNodeType(this.entity, x, y + 1, z);

				if (nodeUp == PathNodeType.BLOCKED && pathpoint.nodeType == PathNodeType.OPEN)
				{
					pathpoint.nodeType = PathNodeType.WALKABLE;
					pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
					return pathpoint;
				}

				PathNodeType nodeNorth = this.getPathNodeType(this.entity, x, y, z - 1);

				if (nodeNorth == PathNodeType.BLOCKED && pathpoint.nodeType == PathNodeType.OPEN)
				{
					pathpoint.nodeType = PathNodeType.WALKABLE;
					pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
					return pathpoint;
				}

				PathNodeType nodeSouth = this.getPathNodeType(this.entity, x, y, z + 1);

				if (nodeSouth == PathNodeType.BLOCKED && pathpoint.nodeType == PathNodeType.OPEN)
				{
					pathpoint.nodeType = PathNodeType.WALKABLE;
					pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
					return pathpoint;
				}

				PathNodeType nodeEast = this.getPathNodeType(this.entity, x + 1, y, z);

				if (nodeEast == PathNodeType.BLOCKED && pathpoint.nodeType == PathNodeType.OPEN)
				{
					pathpoint.nodeType = PathNodeType.WALKABLE;
					pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
					return pathpoint;
				}

				PathNodeType nodeWest = this.getPathNodeType(this.entity, x - 1, y, z);

				if (nodeWest == PathNodeType.BLOCKED && pathpoint.nodeType == PathNodeType.OPEN)
				{
					pathpoint.nodeType = PathNodeType.WALKABLE;
					pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
					return pathpoint;
				}

				PathNodeType nodeDownWest = this.getPathNodeType(this.entity, x + EnumFacing.WEST.getXOffset() + EnumFacing.DOWN.getXOffset(),
						y + EnumFacing.WEST.getYOffset() + EnumFacing.DOWN.getYOffset(),
						z + EnumFacing.WEST.getZOffset() + EnumFacing.DOWN.getZOffset());

				if (nodeDownWest == PathNodeType.BLOCKED && pathpoint.nodeType == PathNodeType.OPEN)
				{
					pathpoint.nodeType = PathNodeType.WALKABLE;
					pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
					return pathpoint;
				}

				PathNodeType nodeDownEast = this.getPathNodeType(this.entity, x + EnumFacing.EAST.getXOffset() + EnumFacing.DOWN.getXOffset(),
						y + EnumFacing.EAST.getYOffset() + EnumFacing.DOWN.getYOffset(),
						z + EnumFacing.EAST.getZOffset() + EnumFacing.DOWN.getZOffset());

				if (nodeDownEast == PathNodeType.BLOCKED && pathpoint.nodeType == PathNodeType.OPEN)
				{
					pathpoint.nodeType = PathNodeType.WALKABLE;
					pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
					return pathpoint;
				}

				PathNodeType nodeDownSouth = this.getPathNodeType(this.entity, x + EnumFacing.SOUTH.getXOffset() + EnumFacing.DOWN.getXOffset(),
						y + EnumFacing.SOUTH.getYOffset() + EnumFacing.DOWN.getYOffset(),
						z + EnumFacing.SOUTH.getZOffset() + EnumFacing.DOWN.getZOffset());

				if (nodeDownSouth == PathNodeType.BLOCKED && pathpoint.nodeType == PathNodeType.OPEN)
				{
					pathpoint.nodeType = PathNodeType.WALKABLE;
					pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
					return pathpoint;
				}

				PathNodeType nodeDownNorth = this.getPathNodeType(this.entity, x + EnumFacing.NORTH.getXOffset() + EnumFacing.DOWN.getXOffset(),
						y + EnumFacing.NORTH.getYOffset() + EnumFacing.DOWN.getYOffset(),
						z + EnumFacing.NORTH.getZOffset() + EnumFacing.DOWN.getZOffset());

				if (nodeDownNorth == PathNodeType.BLOCKED && pathpoint.nodeType == PathNodeType.OPEN)
				{
					pathpoint.nodeType = PathNodeType.WALKABLE;
					pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
					return pathpoint;
				}

				PathNodeType nodeDownNorthWest = this.getPathNodeType(this.entity,
						x + EnumFacing.WEST.getXOffset() + EnumFacing.NORTH.getXOffset() + EnumFacing.DOWN.getXOffset(),
						y + EnumFacing.WEST.getYOffset() + EnumFacing.NORTH.getYOffset() + EnumFacing.DOWN.getYOffset(),
						z + EnumFacing.WEST.getZOffset() + EnumFacing.NORTH.getZOffset() + EnumFacing.DOWN.getZOffset());

				if (nodeDownNorthWest == PathNodeType.BLOCKED && pathpoint.nodeType == PathNodeType.OPEN)
				{
					pathpoint.nodeType = PathNodeType.WALKABLE;
					pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
					return pathpoint;
				}

				PathNodeType nodeDownNorthEast = this.getPathNodeType(this.entity,
						x + EnumFacing.EAST.getXOffset() + EnumFacing.NORTH.getXOffset() + EnumFacing.DOWN.getXOffset(),
						y + EnumFacing.EAST.getYOffset() + EnumFacing.NORTH.getYOffset() + EnumFacing.DOWN.getYOffset(),
						z + EnumFacing.EAST.getZOffset() + EnumFacing.NORTH.getZOffset() + EnumFacing.DOWN.getZOffset());

				if (nodeDownNorthEast == PathNodeType.BLOCKED && pathpoint.nodeType == PathNodeType.OPEN)
				{
					pathpoint.nodeType = PathNodeType.WALKABLE;
					pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
					return pathpoint;
				}

				PathNodeType nodeDownSouthWest = this.getPathNodeType(this.entity,
						x + EnumFacing.WEST.getXOffset() + EnumFacing.SOUTH.getXOffset() + EnumFacing.DOWN.getXOffset(),
						y + EnumFacing.WEST.getYOffset() + EnumFacing.SOUTH.getYOffset() + EnumFacing.DOWN.getYOffset(),
						z + EnumFacing.WEST.getZOffset() + EnumFacing.SOUTH.getZOffset() + EnumFacing.DOWN.getZOffset());

				if (nodeDownSouthWest == PathNodeType.BLOCKED && pathpoint.nodeType == PathNodeType.OPEN)
				{
					pathpoint.nodeType = PathNodeType.WALKABLE;
					pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
					return pathpoint;
				}

				PathNodeType nodeDownSouthEast = this.getPathNodeType(this.entity,
						x + EnumFacing.EAST.getXOffset() + EnumFacing.SOUTH.getXOffset() + EnumFacing.DOWN.getXOffset(),
						y + EnumFacing.EAST.getYOffset() + EnumFacing.SOUTH.getYOffset() + EnumFacing.DOWN.getYOffset(),
						z + EnumFacing.EAST.getZOffset() + EnumFacing.SOUTH.getZOffset() + EnumFacing.DOWN.getZOffset());

				if (nodeDownSouthEast == PathNodeType.BLOCKED && pathpoint.nodeType == PathNodeType.OPEN)
				{
					pathpoint.nodeType = PathNodeType.WALKABLE;
					pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
					return pathpoint;
				}

				PathNodeType nodeUpWest = this.getPathNodeType(this.entity, x + EnumFacing.WEST.getXOffset() + EnumFacing.UP.getXOffset(),
						y + EnumFacing.WEST.getYOffset() + EnumFacing.UP.getYOffset(),
						z + EnumFacing.WEST.getZOffset() + EnumFacing.UP.getZOffset());

				if (nodeUpWest == PathNodeType.BLOCKED && pathpoint.nodeType == PathNodeType.OPEN)
				{
					pathpoint.nodeType = PathNodeType.WALKABLE;
					pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
					return pathpoint;
				}

				PathNodeType nodeUpEast = this.getPathNodeType(this.entity, x + EnumFacing.EAST.getXOffset() + EnumFacing.UP.getXOffset(),
						y + EnumFacing.EAST.getYOffset() + EnumFacing.UP.getYOffset(),
						z + EnumFacing.EAST.getZOffset() + EnumFacing.UP.getZOffset());

				if (nodeUpEast == PathNodeType.BLOCKED && pathpoint.nodeType == PathNodeType.OPEN)
				{
					pathpoint.nodeType = PathNodeType.WALKABLE;
					pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
					return pathpoint;
				}

				PathNodeType nodeUpSouth = this.getPathNodeType(this.entity, x + EnumFacing.SOUTH.getXOffset() + EnumFacing.UP.getXOffset(),
						y + EnumFacing.SOUTH.getYOffset() + EnumFacing.UP.getYOffset(),
						z + EnumFacing.SOUTH.getZOffset() + EnumFacing.UP.getZOffset());

				if (nodeUpSouth == PathNodeType.BLOCKED && pathpoint.nodeType == PathNodeType.OPEN)
				{
					pathpoint.nodeType = PathNodeType.WALKABLE;
					pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
					return pathpoint;
				}

				PathNodeType nodeUpNorth = this.getPathNodeType(this.entity, x + EnumFacing.NORTH.getXOffset() + EnumFacing.UP.getXOffset(),
						y + EnumFacing.NORTH.getYOffset() + EnumFacing.UP.getYOffset(),
						z + EnumFacing.NORTH.getZOffset() + EnumFacing.UP.getZOffset());

				if (nodeUpNorth == PathNodeType.BLOCKED && pathpoint.nodeType == PathNodeType.OPEN)
				{
					pathpoint.nodeType = PathNodeType.WALKABLE;
					pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
					return pathpoint;
				}

				PathNodeType nodeUpNorthWest = this.getPathNodeType(this.entity,
						x + EnumFacing.WEST.getXOffset() + EnumFacing.NORTH.getXOffset() + EnumFacing.UP.getXOffset(),
						y + EnumFacing.WEST.getYOffset() + EnumFacing.NORTH.getYOffset() + EnumFacing.UP.getYOffset(),
						z + EnumFacing.WEST.getZOffset() + EnumFacing.NORTH.getZOffset() + EnumFacing.UP.getZOffset());

				if (nodeUpNorthWest == PathNodeType.BLOCKED && pathpoint.nodeType == PathNodeType.OPEN)
				{
					pathpoint.nodeType = PathNodeType.WALKABLE;
					pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
					return pathpoint;
				}

				PathNodeType nodeUpNorthEast = this.getPathNodeType(this.entity,
						x + EnumFacing.EAST.getXOffset() + EnumFacing.NORTH.getXOffset() + EnumFacing.UP.getXOffset(),
						y + EnumFacing.EAST.getYOffset() + EnumFacing.NORTH.getYOffset() + EnumFacing.UP.getYOffset(),
						z + EnumFacing.EAST.getZOffset() + EnumFacing.NORTH.getZOffset() + EnumFacing.UP.getZOffset());

				if (nodeUpNorthEast == PathNodeType.BLOCKED && pathpoint.nodeType == PathNodeType.OPEN)
				{
					pathpoint.nodeType = PathNodeType.WALKABLE;
					pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
					return pathpoint;
				}

				PathNodeType nodeUpSouthWest = this.getPathNodeType(this.entity,
						x + EnumFacing.WEST.getXOffset() + EnumFacing.SOUTH.getXOffset() + EnumFacing.UP.getXOffset(),
						y + EnumFacing.WEST.getYOffset() + EnumFacing.SOUTH.getYOffset() + EnumFacing.UP.getYOffset(),
						z + EnumFacing.WEST.getZOffset() + EnumFacing.SOUTH.getZOffset() + EnumFacing.UP.getZOffset());

				if (nodeUpSouthWest == PathNodeType.BLOCKED && pathpoint.nodeType == PathNodeType.OPEN)
				{
					pathpoint.nodeType = PathNodeType.WALKABLE;
					pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
					return pathpoint;
				}

				PathNodeType nodeUpSouthEast = this.getPathNodeType(this.entity,
						x + EnumFacing.EAST.getXOffset() + EnumFacing.SOUTH.getXOffset() + EnumFacing.UP.getXOffset(),
						y + EnumFacing.EAST.getYOffset() + EnumFacing.SOUTH.getYOffset() + EnumFacing.UP.getYOffset(),
						z + EnumFacing.EAST.getZOffset() + EnumFacing.SOUTH.getZOffset() + EnumFacing.UP.getZOffset());

				if (nodeUpSouthEast == PathNodeType.BLOCKED && pathpoint.nodeType == PathNodeType.OPEN)
				{
					pathpoint.nodeType = PathNodeType.WALKABLE;
					pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
					return pathpoint;
				}

			}

			if (node == PathNodeType.WALKABLE)
			{
				return pathpoint;
			}
			else
			{
				// IF WE HAVEN'T FOUND A PATHPOINT WITH PRIORITY AND WE CAN STEP UP, FIND PATHPOINT ONE BLOCK ABOVE
				if (pathpoint == null && blockHeightCanStep > 0 && node != PathNodeType.FENCE && node != PathNodeType.TRAPDOOR)
				{
					pathpoint = this.getSafePoint(x, y + 1, z, blockHeightCanStep - 1, currentMaxYValue, facing);

					// IF NEW PATHPOINT IS OPEN/WALKABLE AND THE ENTITY IS LESS THAN 1 WIDTH, CHECK IF COLLIDES WITH ANY BLOCK. IF SO, DELETE PATHPOINT
					if (pathpoint != null && (pathpoint.nodeType == PathNodeType.OPEN || pathpoint.nodeType == PathNodeType.WALKABLE)
							&& this.entity.width < 1.0F)
					{
						double d2 = (double) (x - facing.getXOffset()) + 0.5D;
						double d3 = (double) (z - facing.getZOffset()) + 0.5D;
						AxisAlignedBB axisalignedbb = new AxisAlignedBB(d2 - halfEntityWidth, (double) y + 0.001D, d3 - halfEntityWidth, d2 + halfEntityWidth,
								(double) ((float) y + this.entity.height), d3 + halfEntityWidth);
						AxisAlignedBB axisalignedbb1 = this.blockaccess.getBlockState(pos).getBoundingBox(this.blockaccess, pos);
						AxisAlignedBB axisalignedbb2 = axisalignedbb.expand(0.0D, axisalignedbb1.maxY - 0.002D, 0.0D);

						if (this.entity.world.collidesWithAnyBlock(axisalignedbb2))
						{
							pathpoint = null;
						}
					}
				}

				// IF WE FOUND A PATHPOINT WITH PRIORITY, SEE IF COLLIDES WITH BLOCKS. IF SO, RETURN NULL.
				if (node == PathNodeType.OPEN)
				{
					AxisAlignedBB axisalignedbb3 = new AxisAlignedBB((double) x - halfEntityWidth + 0.5D, (double) y + 0.001D,
							(double) z - halfEntityWidth + 0.5D,
							(double) x + halfEntityWidth + 0.5D, (double) ((float) y + this.entity.height), (double) z + halfEntityWidth + 0.5D);

					if (this.entity.world.collidesWithAnyBlock(axisalignedbb3))
					{
						return null;
					}

					// IF ENTITY WIDTH IS GREATER THAN 1, CHECK IF NEARBY NODES ARE BLOCKED THEN SET PATHPOINT NODETYPE TO WALKABLE. RETURN PATHPOINT.
					if (this.entity.width >= 1.0F)
					{
						PathNodeType nodeDown = this.getPathNodeType(this.entity, x, y - 1, z);

						if (nodeDown == PathNodeType.BLOCKED)
						{
							if (pathpoint == null)
							{
								pathpoint = this.openPoint(x, y, z);
							}

							pathpoint.nodeType = PathNodeType.WALKABLE;
							pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
							return pathpoint;
						}

						PathNodeType nodeUp = this.getPathNodeType(this.entity, x, y + 1, z);

						if (nodeUp == PathNodeType.BLOCKED)
						{
							if (pathpoint == null)
							{
								pathpoint = this.openPoint(x, y, z);
							}

							pathpoint.nodeType = PathNodeType.WALKABLE;
							pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);

							return pathpoint;
						}

						PathNodeType nodeNorth = this.getPathNodeType(this.entity, x, y, z - 1);

						if (nodeNorth == PathNodeType.BLOCKED)
						{
							if (pathpoint == null)
							{
								pathpoint = this.openPoint(x, y, z);
							}

							pathpoint.nodeType = PathNodeType.WALKABLE;
							pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
							return pathpoint;
						}

						PathNodeType nodeSouth = this.getPathNodeType(this.entity, x, y, z + 1);

						if (nodeSouth == PathNodeType.BLOCKED)
						{
							if (pathpoint == null)
							{
								pathpoint = this.openPoint(x, y, z);
							}

							pathpoint.nodeType = PathNodeType.WALKABLE;
							pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
							return pathpoint;
						}

						PathNodeType nodeEast = this.getPathNodeType(this.entity, x + 1, y, z);

						if (nodeEast == PathNodeType.BLOCKED)
						{
							if (pathpoint == null)
							{
								pathpoint = this.openPoint(x, y, z);
							}

							pathpoint.nodeType = PathNodeType.WALKABLE;
							pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
							return pathpoint;
						}

						PathNodeType nodeWest = this.getPathNodeType(this.entity, x - 1, y, z);

						if (nodeWest == PathNodeType.BLOCKED)
						{
							if (pathpoint == null)
							{
								pathpoint = this.openPoint(x, y, z);
							}

							pathpoint.nodeType = PathNodeType.WALKABLE;
							pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
							return pathpoint;
						}

						PathNodeType nodeDownWest = this.getPathNodeType(this.entity, x + EnumFacing.WEST.getXOffset() + EnumFacing.DOWN.getXOffset(),
								y + EnumFacing.WEST.getYOffset() + EnumFacing.DOWN.getYOffset(),
								z + EnumFacing.WEST.getZOffset() + EnumFacing.DOWN.getZOffset());

						if (nodeDownWest == PathNodeType.BLOCKED)
						{
							if (pathpoint == null)
							{
								pathpoint = this.openPoint(x, y, z);
							}

							pathpoint.nodeType = PathNodeType.WALKABLE;
							pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
							return pathpoint;
						}

						PathNodeType nodeDownEast = this.getPathNodeType(this.entity, x + EnumFacing.EAST.getXOffset() + EnumFacing.DOWN.getXOffset(),
								y + EnumFacing.EAST.getYOffset() + EnumFacing.DOWN.getYOffset(),
								z + EnumFacing.EAST.getZOffset() + EnumFacing.DOWN.getZOffset());

						if (nodeDownEast == PathNodeType.BLOCKED)
						{
							if (pathpoint == null)
							{
								pathpoint = this.openPoint(x, y, z);
							}

							pathpoint.nodeType = PathNodeType.WALKABLE;
							pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
							return pathpoint;
						}

						PathNodeType nodeDownSouth = this
								.getPathNodeType(this.entity, x + EnumFacing.SOUTH.getXOffset() + EnumFacing.DOWN.getXOffset(),
										y + EnumFacing.SOUTH.getYOffset() + EnumFacing.DOWN.getYOffset(),
										z + EnumFacing.SOUTH.getZOffset() + EnumFacing.DOWN.getZOffset());

						if (nodeDownSouth == PathNodeType.BLOCKED)
						{
							if (pathpoint == null)
							{
								pathpoint = this.openPoint(x, y, z);
							}

							pathpoint.nodeType = PathNodeType.WALKABLE;
							pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
							return pathpoint;
						}

						PathNodeType nodeDownNorth = this
								.getPathNodeType(this.entity, x + EnumFacing.NORTH.getXOffset() + EnumFacing.DOWN.getXOffset(),
										y + EnumFacing.NORTH.getYOffset() + EnumFacing.DOWN.getYOffset(),
										z + EnumFacing.NORTH.getZOffset() + EnumFacing.DOWN.getZOffset());

						if (nodeDownNorth == PathNodeType.BLOCKED)
						{
							if (pathpoint == null)
							{
								pathpoint = this.openPoint(x, y, z);
							}

							pathpoint.nodeType = PathNodeType.WALKABLE;
							pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
							return pathpoint;
						}

						PathNodeType nodeDownNorthWest = this.getPathNodeType(this.entity,
								x + EnumFacing.WEST.getXOffset() + EnumFacing.NORTH.getXOffset() + EnumFacing.DOWN.getXOffset(),
								y + EnumFacing.WEST.getYOffset() + EnumFacing.NORTH.getYOffset() + EnumFacing.DOWN.getYOffset(),
								z + EnumFacing.WEST.getZOffset() + EnumFacing.NORTH.getZOffset() + EnumFacing.DOWN.getZOffset());

						if (nodeDownNorthWest == PathNodeType.BLOCKED)
						{
							if (pathpoint == null)
							{
								pathpoint = this.openPoint(x, y, z);
							}

							pathpoint.nodeType = PathNodeType.WALKABLE;
							pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
							return pathpoint;
						}

						PathNodeType nodeDownNorthEast = this.getPathNodeType(this.entity,
								x + EnumFacing.EAST.getXOffset() + EnumFacing.NORTH.getXOffset() + EnumFacing.DOWN.getXOffset(),
								y + EnumFacing.EAST.getYOffset() + EnumFacing.NORTH.getYOffset() + EnumFacing.DOWN.getYOffset(),
								z + EnumFacing.EAST.getZOffset() + EnumFacing.NORTH.getZOffset() + EnumFacing.DOWN.getZOffset());

						if (nodeDownNorthEast == PathNodeType.BLOCKED)
						{
							if (pathpoint == null)
							{
								pathpoint = this.openPoint(x, y, z);
							}

							pathpoint.nodeType = PathNodeType.WALKABLE;
							pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
							return pathpoint;
						}

						PathNodeType nodeDownSouthWest = this.getPathNodeType(this.entity,
								x + EnumFacing.WEST.getXOffset() + EnumFacing.SOUTH.getXOffset() + EnumFacing.DOWN.getXOffset(),
								y + EnumFacing.WEST.getYOffset() + EnumFacing.SOUTH.getYOffset() + EnumFacing.DOWN.getYOffset(),
								z + EnumFacing.WEST.getZOffset() + EnumFacing.SOUTH.getZOffset() + EnumFacing.DOWN.getZOffset());

						if (nodeDownSouthWest == PathNodeType.BLOCKED)
						{
							if (pathpoint == null)
							{
								pathpoint = this.openPoint(x, y, z);
							}

							pathpoint.nodeType = PathNodeType.WALKABLE;
							pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
							return pathpoint;
						}

						PathNodeType nodeDownSouthEast = this.getPathNodeType(this.entity,
								x + EnumFacing.EAST.getXOffset() + EnumFacing.SOUTH.getXOffset() + EnumFacing.DOWN.getXOffset(),
								y + EnumFacing.EAST.getYOffset() + EnumFacing.SOUTH.getYOffset() + EnumFacing.DOWN.getYOffset(),
								z + EnumFacing.EAST.getZOffset() + EnumFacing.SOUTH.getZOffset() + EnumFacing.DOWN.getZOffset());

						if (nodeDownSouthEast == PathNodeType.BLOCKED)
						{
							if (pathpoint == null)
							{
								pathpoint = this.openPoint(x, y, z);
							}

							pathpoint.nodeType = PathNodeType.WALKABLE;
							pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
							return pathpoint;
						}

						PathNodeType nodeUpWest = this.getPathNodeType(this.entity, x + EnumFacing.WEST.getXOffset() + EnumFacing.UP.getXOffset(),
								y + EnumFacing.WEST.getYOffset() + EnumFacing.UP.getYOffset(),
								z + EnumFacing.WEST.getZOffset() + EnumFacing.UP.getZOffset());

						if (nodeUpWest == PathNodeType.BLOCKED)
						{
							if (pathpoint == null)
							{
								pathpoint = this.openPoint(x, y, z);
							}

							pathpoint.nodeType = PathNodeType.WALKABLE;
							pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
							return pathpoint;
						}

						PathNodeType nodeUpEast = this.getPathNodeType(this.entity, x + EnumFacing.EAST.getXOffset() + EnumFacing.UP.getXOffset(),
								y + EnumFacing.EAST.getYOffset() + EnumFacing.UP.getYOffset(),
								z + EnumFacing.EAST.getZOffset() + EnumFacing.UP.getZOffset());

						if (nodeUpEast == PathNodeType.BLOCKED)
						{
							if (pathpoint == null)
							{
								pathpoint = this.openPoint(x, y, z);
							}

							pathpoint.nodeType = PathNodeType.WALKABLE;
							pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
							return pathpoint;
						}

						PathNodeType nodeUpSouth = this.getPathNodeType(this.entity, x + EnumFacing.SOUTH.getXOffset() + EnumFacing.UP.getXOffset(),
								y + EnumFacing.SOUTH.getYOffset() + EnumFacing.UP.getYOffset(),
								z + EnumFacing.SOUTH.getZOffset() + EnumFacing.UP.getZOffset());

						if (nodeUpSouth == PathNodeType.BLOCKED)
						{
							if (pathpoint == null)
							{
								pathpoint = this.openPoint(x, y, z);
							}

							pathpoint.nodeType = PathNodeType.WALKABLE;
							pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
							return pathpoint;
						}

						PathNodeType nodeUpNorth = this.getPathNodeType(this.entity, x + EnumFacing.NORTH.getXOffset() + EnumFacing.UP.getXOffset(),
								y + EnumFacing.NORTH.getYOffset() + EnumFacing.UP.getYOffset(),
								z + EnumFacing.NORTH.getZOffset() + EnumFacing.UP.getZOffset());

						if (nodeUpNorth == PathNodeType.BLOCKED)
						{
							if (pathpoint == null)
							{
								pathpoint = this.openPoint(x, y, z);
							}

							pathpoint.nodeType = PathNodeType.WALKABLE;
							pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
							return pathpoint;
						}

						PathNodeType nodeUpNorthWest = this.getPathNodeType(this.entity,
								x + EnumFacing.WEST.getXOffset() + EnumFacing.NORTH.getXOffset() + EnumFacing.UP.getXOffset(),
								y + EnumFacing.WEST.getYOffset() + EnumFacing.NORTH.getYOffset() + EnumFacing.UP.getYOffset(),
								z + EnumFacing.WEST.getZOffset() + EnumFacing.NORTH.getZOffset() + EnumFacing.UP.getZOffset());

						if (nodeUpNorthWest == PathNodeType.BLOCKED)
						{
							if (pathpoint == null)
							{
								pathpoint = this.openPoint(x, y, z);
							}

							pathpoint.nodeType = PathNodeType.WALKABLE;
							pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
							return pathpoint;
						}

						PathNodeType nodeUpNorthEast = this.getPathNodeType(this.entity,
								x + EnumFacing.EAST.getXOffset() + EnumFacing.NORTH.getXOffset() + EnumFacing.UP.getXOffset(),
								y + EnumFacing.EAST.getYOffset() + EnumFacing.NORTH.getYOffset() + EnumFacing.UP.getYOffset(),
								z + EnumFacing.EAST.getZOffset() + EnumFacing.NORTH.getZOffset() + EnumFacing.UP.getZOffset());

						if (nodeUpNorthEast == PathNodeType.BLOCKED)
						{
							if (pathpoint == null)
							{
								pathpoint = this.openPoint(x, y, z);
							}

							pathpoint.nodeType = PathNodeType.WALKABLE;
							pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
							return pathpoint;
						}

						PathNodeType nodeUpSouthWest = this.getPathNodeType(this.entity,
								x + EnumFacing.WEST.getXOffset() + EnumFacing.SOUTH.getXOffset() + EnumFacing.UP.getXOffset(),
								y + EnumFacing.WEST.getYOffset() + EnumFacing.SOUTH.getYOffset() + EnumFacing.UP.getYOffset(),
								z + EnumFacing.WEST.getZOffset() + EnumFacing.SOUTH.getZOffset() + EnumFacing.UP.getZOffset());

						if (nodeUpSouthWest == PathNodeType.BLOCKED)
						{
							if (pathpoint == null)
							{
								pathpoint = this.openPoint(x, y, z);
							}

							pathpoint.nodeType = PathNodeType.WALKABLE;
							pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
							return pathpoint;
						}

						PathNodeType nodeUpSouthEast = this.getPathNodeType(this.entity,
								x + EnumFacing.EAST.getXOffset() + EnumFacing.SOUTH.getXOffset() + EnumFacing.UP.getXOffset(),
								y + EnumFacing.EAST.getYOffset() + EnumFacing.SOUTH.getYOffset() + EnumFacing.UP.getYOffset(),
								z + EnumFacing.EAST.getZOffset() + EnumFacing.SOUTH.getZOffset() + EnumFacing.UP.getZOffset()
						);

						if (nodeUpSouthEast == PathNodeType.BLOCKED)
						{
							if (pathpoint == null)
							{
								pathpoint = this.openPoint(x, y, z);
							}

							pathpoint.nodeType = PathNodeType.WALKABLE;
							pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
							return pathpoint;
						}
					}

					// Would prevent climbers from attempting to climb down long distances

					/*int i = 0;

					while (y > 0 && node == PathNodeType.OPEN)
					{
						--y;

						*//*if (i++ >= this.entity.getMaxFallHeight())
						{
							return null;
						}*//*

						node = this.getPathNodeType(this.entity, x, y, z);
						priority = this.entity.getPathPriority(node);

						if (node == PathNodeType.OPEN && priority >= 0.0F)
						{
							pathpoint = this.openPoint(x, y, z);
							pathpoint.nodeType = node;
							pathpoint.costMalus = Math.max(pathpoint.costMalus, priority);
							break;
						}

						if (priority < 0.0F)
						{
							return null;
						}
					}*/
				}

				return pathpoint;
			}
		}
	}

	@Override
	public PathNodeType getPathNodeType(IBlockAccess blockaccessIn, int x, int y, int z, EntityLiving entitylivingIn, int xSize, int ySize, int zSize,
			boolean canBreakDoorsIn, boolean canEnterDoorsIn)
	{
		EnumSet<PathNodeType> enumset = EnumSet.noneOf(PathNodeType.class);
		PathNodeType pathnodetype = PathNodeType.BLOCKED;
		double entityHalfWidth = (double) entitylivingIn.width / 2.0D;
		BlockPos entityPos = new BlockPos(entitylivingIn);
		pathnodetype = this.getPathNodeType(blockaccessIn, x, y, z, xSize, ySize, zSize, canBreakDoorsIn, canEnterDoorsIn, enumset, pathnodetype, entityPos);

		if (enumset.contains(PathNodeType.FENCE))
		{
			return PathNodeType.FENCE;
		}
		else
		{
			PathNodeType pathnodetype1 = PathNodeType.BLOCKED;

			for (PathNodeType pathnodetype2 : enumset)
			{
				if (entitylivingIn.getPathPriority(pathnodetype2) < 0.0F)
				{
					return pathnodetype2;
				}

				if (entitylivingIn.getPathPriority(pathnodetype2) >= entitylivingIn.getPathPriority(pathnodetype1))
				{
					pathnodetype1 = pathnodetype2;
				}
			}

			if (pathnodetype == PathNodeType.OPEN && entitylivingIn.getPathPriority(pathnodetype1) == 0.0F)
			{
				return PathNodeType.OPEN;
			}
			else
			{
				return pathnodetype1;
			}
		}
	}

	public PathNodeType getPathNodeType(IBlockAccess access, int x, int y, int z, int xSize, int ySize, int zSize, boolean canOpenDoorsIn,
			boolean canEnterDoorsIn, EnumSet<PathNodeType> addTo, PathNodeType node, BlockPos entityPos)
	{
		for (int i = 0; i < xSize; ++i)
		{
			for (int j = 0; j < ySize; ++j)
			{
				for (int k = 0; k < zSize; ++k)
				{
					int l = i + x;
					int i1 = j + y;
					int j1 = k + z;
					PathNodeType pathnodetype = this.getPathNodeType(access, l, i1, j1);

					if (pathnodetype == PathNodeType.DOOR_WOOD_CLOSED && canOpenDoorsIn && canEnterDoorsIn)
					{
						pathnodetype = PathNodeType.WALKABLE;
					}

					if (pathnodetype == PathNodeType.DOOR_OPEN && !canEnterDoorsIn)
					{
						pathnodetype = PathNodeType.BLOCKED;
					}

					if (pathnodetype == PathNodeType.RAIL && !(access.getBlockState(entityPos).getBlock() instanceof BlockRailBase) && !(access
							.getBlockState(entityPos.down()).getBlock() instanceof BlockRailBase))
					{
						pathnodetype = PathNodeType.FENCE;
					}

					if (i == 0 && j == 0 && k == 0)
					{
						node = pathnodetype;
					}

					addTo.add(pathnodetype);
				}
			}
		}

		return node;
	}

	private PathNodeType getPathNodeType(EntityLiving entitylivingIn, BlockPos pos)
	{
		return this.getPathNodeType(entitylivingIn, pos.getX(), pos.getY(), pos.getZ());
	}

	private PathNodeType getPathNodeType(EntityLiving entitylivingIn, int x, int y, int z)
	{
		return this.getPathNodeType(this.blockaccess, x, y, z, entitylivingIn, this.entitySizeX, this.entitySizeY, this.entitySizeZ, this.getCanOpenDoors(),
				this.getCanEnterDoors());
	}

	@Override
	public PathNodeType getPathNodeType(IBlockAccess blockaccessIn, int x, int y, int z)
	{
		PathNodeType pathnodetype = this.getPathNodeTypeRaw(blockaccessIn, x, y, z);

		if (pathnodetype == PathNodeType.OPEN && y >= 1)
		{
			Block downBlock = blockaccessIn.getBlockState(new BlockPos(x, y - 1, z)).getBlock();

			PathNodeType pathnodetype1 = this.getPathNodeTypeRaw(blockaccessIn, x, y - 1, z);
			pathnodetype = pathnodetype1 != PathNodeType.WALKABLE && pathnodetype1 != PathNodeType.OPEN && pathnodetype1 != PathNodeType.WATER
					&& pathnodetype1 != PathNodeType.LAVA ? PathNodeType.WALKABLE : PathNodeType.OPEN;

			if (pathnodetype1 == PathNodeType.DAMAGE_FIRE || downBlock == Blocks.MAGMA)
			{
				pathnodetype = PathNodeType.DAMAGE_FIRE;
			}

			if (pathnodetype1 == PathNodeType.DAMAGE_CACTUS)
			{
				pathnodetype = PathNodeType.DAMAGE_CACTUS;
			}
		}

		pathnodetype = this.checkNeighborBlocks(blockaccessIn, x, y, z, pathnodetype);
		return pathnodetype;
	}

	public PathNodeType checkNeighborBlocks(IBlockAccess access, int x, int y, int z, PathNodeType node)
	{
		BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();

		if (node == PathNodeType.WALKABLE)
		{
			for (int i = -1; i <= 1; ++i)
			{
				for (int j = -1; j <= 1; ++j)
				{
					if (i != 0 || j != 0)
					{
						Block block = access.getBlockState(blockpos$pooledmutableblockpos.setPos(i + x, y, j + z))
								.getBlock();

						if (block == Blocks.CACTUS)
						{
							node = PathNodeType.DANGER_CACTUS;
						}
						else if (block == Blocks.FIRE)
						{
							node = PathNodeType.DANGER_FIRE;
						}
						else if (block.isBurning(access, blockpos$pooledmutableblockpos))
						{
							node = PathNodeType.DAMAGE_FIRE;
						}
					}
				}
			}
		}

		blockpos$pooledmutableblockpos.release();
		return node;
	}

	protected PathNodeType getPathNodeTypeRaw(IBlockAccess access, int x, int y, int z)
	{
		BlockPos pos = new BlockPos(x, y, z);
		IBlockState iblockstate = access.getBlockState(pos);
		Block block = iblockstate.getBlock();
		Material material = iblockstate.getMaterial();

		PathNodeType type = block.getAiPathNodeType(iblockstate, access, pos);
		if (type != null)
		{
			return type;
		}

		if (material == Material.AIR)
		{
			return PathNodeType.OPEN;
		}
		else if (block != Blocks.TRAPDOOR && block != Blocks.IRON_TRAPDOOR && block != Blocks.WATERLILY)
		{
			if (block == Blocks.FIRE)
			{
				return PathNodeType.DAMAGE_FIRE;
			}
			else if (block == Blocks.CACTUS)
			{
				return PathNodeType.DAMAGE_CACTUS;
			}
			else if (block instanceof BlockDoor && material == Material.WOOD && !iblockstate.getValue(BlockDoor.OPEN).booleanValue())
			{
				return PathNodeType.DOOR_WOOD_CLOSED;
			}
			else if (block instanceof BlockDoor && material == Material.IRON && !iblockstate.getValue(BlockDoor.OPEN).booleanValue())
			{
				return PathNodeType.DOOR_IRON_CLOSED;
			}
			else if (block instanceof BlockDoor && iblockstate.getValue(BlockDoor.OPEN).booleanValue())
			{
				return PathNodeType.DOOR_OPEN;
			}
			else if (block instanceof BlockRailBase)
			{
				return PathNodeType.RAIL;
			}
			else if (!(block instanceof BlockFence) && !(block instanceof BlockWall) && (!(block instanceof BlockFenceGate) || iblockstate
					.getValue(BlockFenceGate.OPEN).booleanValue()))
			{
				if (material == Material.WATER)
				{
					return PathNodeType.WATER;
				}
				else if (material == Material.LAVA)
				{
					return PathNodeType.LAVA;
				}
				else
				{
					return block.isPassable(access, pos) ? PathNodeType.OPEN : PathNodeType.BLOCKED;
				}
			}
			else
			{
				return PathNodeType.FENCE;
			}
		}
		else
		{
			return PathNodeType.TRAPDOOR;
		}
	}
}