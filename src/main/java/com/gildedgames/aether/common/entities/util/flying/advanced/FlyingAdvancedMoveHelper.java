package com.gildedgames.aether.common.entities.util.flying.advanced;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.util.helpers.MathUtil;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.floats.FloatListIterator;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import javax.vecmath.Point3d;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

public class FlyingAdvancedMoveHelper extends EntityMoveHelper
{
	private final EntityFlyingAdvanced entity;

	private FlightPath flyNav, nextFlyNav;

	private FloatList speedMap = new FloatArrayList(), timeMap = new FloatArrayList();

	public BlockPos next;

	private Random rand;

	public float totalDist = 0;

	public FlyingAdvancedMoveHelper(EntityFlyingAdvanced entity)
	{
		super(entity);
		this.entity = entity;
		this.nextFlyNav = new FlightPath();
		this.rand = entity.world.rand;
	}

	@Override
	public void onUpdateMoveHelper()
	{
		super.onUpdateMoveHelper();
	}

	@Override
	public void setMoveTo(double x, double y, double z, double speedIn)
	{
		if (this.entity.world.isRemote)
		{
			return;
		}

		BlockPos target = new BlockPos((float) x, (float) y, (float) z);

		if (this.entity.getTime() == 0 && this.flyNav == null)
		{
			this.posX = x;
			this.posY = y;
			this.posZ = z;

			this.flyNav = new FlightPath();

			for (int i = 0; i < 5; i++)
			{
				this.flyNav.setStartPos(this.entity.getPosition());
				this.flyNav.setStartHandle(this.entity.getPosition().add(this.rand.nextInt(10) - 5, this.rand.nextInt(10) - 5, this.rand.nextInt(10) - 5));
				this.flyNav.setEndHandle(target.add(this.rand.nextInt(10) - 5, this.rand.nextInt(10) - 5, this.rand.nextInt(10) - 5));
				this.flyNav.setEndPos(target);

				if (this.checkFlightPath(this.flyNav))
				{
					this.entity.updateFlyNav(this.flyNav);

					break;
				}
			}
		}
	}

	public void setNext(FlightPath last, BlockPos target)
	{
		if (this.next != null)
		{
			return;
		}

		if (target == null)
		{
			target = last.getEndPos().add((this.rand.nextBoolean() ? -1 : 1) * (this.rand.nextInt(7) + 3),
					(this.rand.nextBoolean() ? -1 : 1) * (this.rand.nextInt(7) + 3), (this.rand.nextBoolean() ? -1 : 1) * (this.rand.nextInt(7) + 3));
		}

		if (this.nextFlyNav == null)
		{
			this.nextFlyNav = new FlightPath();
		}

		this.next = target;

		Point3d startHandle = last.getEndPosPoint();

		startHandle.sub(last.getEndHandle());

//		startHandle.scale(dist);
		startHandle.add(last.getEndPosPoint());

		Point3d endHandle = last.getEndPosPoint();

		endHandle.add(new Point3d((this.rand.nextBoolean() ? -1 : 1) * (this.rand.nextInt(7) + 3),
				(this.rand.nextBoolean() ? -1 : 1) * (this.rand.nextInt(7) + 3), (this.rand.nextBoolean() ? -1 : 1) * (this.rand.nextInt(7) + 3)));

		this.nextFlyNav.setStartPos(last.getEndPos());
		this.nextFlyNav.setStartHandle(startHandle);
		this.nextFlyNav.setEndHandle(endHandle);
		this.nextFlyNav.setEndPos(target);

		for (int i = 0; i < 6; i++)
		{
			if (this.checkFlightPath(this.nextFlyNav))
			{
				this.entity.updateFutureFlyNav(this.nextFlyNav);

				return;
			}
		}

		this.nextFlyNav = null;
		this.flyNav = null;
	}

	private boolean between(FlightPath path, AxisAlignedBB aabb, boolean first, double dist, float t, float t2, float distStart, float distEnd)
	{
		float t3 = t + (t2 - t) / 2f;

		BlockPos start = MathUtil.getBlockBezier(path, t);
		BlockPos end = MathUtil.getBlockBezier(path, t2);
		BlockPos mid = MathUtil.getBlockBezier(path, t3);

		int x = MathHelper.floor(this.entity.posX);
		int y = MathHelper.floor(this.entity.posY + 0.5f);
		int z = MathHelper.floor(this.entity.posZ);

		if (first && (this.checkBlockCollision(start.getX() - x, start.getY() - y, start.getZ() - z, aabb) || this.checkBlockCollision(end.getX() - x, end.getY() - y, end.getZ() - z, aabb)))
		{
			return false;
		}

		if (this.checkBlockCollision(mid.getX() - x, mid.getY() - y, mid.getZ() - z, aabb))
		{
			return false;
		}

		boolean closeStart = Math.abs(mid.getX() - start.getX()) > dist || Math.abs(mid.getY() - start.getY()) > dist || Math.abs(mid.getZ() - start.getZ()) > dist;
		boolean closeEnd = Math.abs(mid.getX() - end.getX()) > dist || Math.abs(mid.getY() - end.getY()) > dist || Math.abs(mid.getZ() - end.getZ()) > dist;

		float dStart = 0;
		float dEnd = 0;

		if (t2 <= 1)
		{
			dStart = distStart + (float) start.distanceSq(mid);
			dEnd = distEnd + (float) mid.distanceSq(end);

			if (dStart > dist * dist * dist && !timeMap.contains(t))
			{
				this.speedMap.add(dStart);
				this.timeMap.add(t);
				this.totalDist += dStart;

				if (dStart < this.min)
				{
					this.min = dStart;
				}

				if (dStart > this.max)
				{
					this.max = dStart;
				}

				dStart = 0;
			}

			if (dEnd > dist * dist * dist && !timeMap.contains(t3))
			{
				if (dEnd < this.min)
				{
					this.min = dEnd;
				}

				if (dEnd > this.max)
				{
					this.max = dEnd;
				}

				this.speedMap.add(dEnd);
				this.timeMap.add(t3);
				this.totalDist += dEnd;

				dEnd = 0;
			}
		}
		else if (distStart != 0 || distEnd != 0)
		{
			this.speedMap.add(distStart);
			this.speedMap.add(distEnd);
			this.timeMap.add(t);
			this.timeMap.add(t3);
			this.totalDist += distStart + distEnd;
		}

		return !((closeStart && !this.between(path, aabb, false, dist, t, t3, dStart, dEnd)) || (closeEnd && !this.between(path, aabb, false, dist, t3, t2, dStart, dEnd)));
	}

	private float min, max;

	private boolean checkFlightPath(FlightPath path)
	{
		this.speedMap.clear();
		this.timeMap.clear();
		this.totalDist = 0;

		AxisAlignedBB bb = this.entity.getEntityBoundingBox().expand(1, 1, 1);
		final double dist = Math.max(bb.maxX - bb.minX, bb.maxY - bb.minY);

		if (this.between(path, bb, true, dist, 0f, 1.2f, 0, 0))
		{
			if (this.speedMap.size() > 0)
			{
				this.max -= this.min;

				for (int i = 0; i < this.speedMap.size(); i++)
				{
					AetherCore.LOGGER.info(i + ": " + this.timeMap.get(i) + " = " + this.speedMap.get(i));
				}

				AetherCore.LOGGER.info("sorting....");

				for (int i = 0; i < this.timeMap.size(); i++)
				{
					float a = this.timeMap.get(i);
					float m = this.speedMap.get(i);
					int index = 0;

					for (int j = 0; j < this.timeMap.size(); j++)
					{
						float b = this.timeMap.get(j);

						index = j;

						if (a <= b && j != i)
						{
							break;
						}
					}

					this.speedMap.add(index, (m - min) / max);
					this.speedMap.remove(index <= i ? i + 1 : i);
					this.timeMap.add(index, a);
					this.timeMap.remove(index <= i ? i + 1 : i);
				}

				for (int i = 0; i < this.speedMap.size(); i++)
				{
					AetherCore.LOGGER.info(i + ": " + this.timeMap.get(i) + " = " + this.speedMap.get(i));
				}
			}

			this.entity.updateFutureSpeedMap(this.speedMap, this.timeMap);

			return true;
		}
		else
		{
			return false;
		}
	}

	private boolean checkBlockCollision(int x, int y, int z, AxisAlignedBB bb)
	{
		int j2 = MathHelper.floor(bb.minX);
		int k2 = MathHelper.ceil(bb.maxX);
		int l2 = MathHelper.floor(bb.minY);
		int i3 = MathHelper.ceil(bb.maxY);
		int j3 = MathHelper.floor(bb.minZ);
		int k3 = MathHelper.ceil(bb.maxZ);
		BlockPos.PooledMutableBlockPos pos = BlockPos.PooledMutableBlockPos.retain();

		for (int l3 = j2; l3 < k2; ++l3)
		{
			for (int i4 = l2; i4 < i3; ++i4)
			{
				for (int j4 = j3; j4 < k3; ++j4)
				{
					IBlockState state = this.entity.world.getBlockState(pos.setPos(x + l3, y + i4, z +j4));

					if (state.getMaterial() != Material.AIR && state.getMaterial() != Material.GLASS)
					{
						pos.release();
						return true;
					}
					else
					{
//						this.entity.world.setBlockState(pos, Blocks.GLASS.getDefaultState());
					}
				}
			}
		}

		pos.release();
		return false;
	}

	public void next()
	{
		if (this.next != null)
		{
			this.posX = this.next.getX();
			this.posY = this.next.getY();
			this.posZ = this.next.getZ();
			this.flyNav = this.nextFlyNav;
			this.nextFlyNav = new FlightPath();
			this.next = null;
		}
	}
}