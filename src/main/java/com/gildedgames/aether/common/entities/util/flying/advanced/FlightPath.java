package com.gildedgames.aether.common.entities.util.flying.advanced;

import it.unimi.dsi.fastutil.floats.FloatList;
import net.minecraft.util.math.BlockPos;

import javax.vecmath.Point3d;

public class FlightPath
{

	public BlockPos start, end;

	public Point3d startHandle, endHandle;

	public static final Point3d ZERO = new Point3d(0, 0, 0);

	public FlightPath()
	{
		this(BlockPos.ORIGIN, ZERO, ZERO, BlockPos.ORIGIN);
	}

	public FlightPath(BlockPos start, Point3d startHandle, Point3d endHandle, BlockPos end)
	{
		this.start = start;
		this.startHandle = startHandle;
		this.endHandle = endHandle;
		this.end = end;
	}

	public FlightPath(BlockPos start, double x1, double y1, double z1, double x2, double y2, double z2, BlockPos end)
	{
		this(start, new Point3d(x1, y1, z1), new Point3d(x2, y2, z2), end);
	}

	public BlockPos getStartPos()
	{
		return this.start;
	}

	public Point3d getStartHandle()
	{
		return this.startHandle;
	}

	public BlockPos getEndPos()
	{
		return this.end;
	}

	public Point3d getEndHandle()
	{
		return this.endHandle;
	}

	public void setStartPos(BlockPos pos)
	{
		this.start = pos;
	}

	public void setStartHandle(Point3d pos)
	{
		this.startHandle = pos;
	}

	public void setEndPos(BlockPos pos)
	{
		this.end = pos;
	}

	public void setEndHandle(Point3d pos)
	{
		this.endHandle = pos;
	}

	public void setStartHandle(BlockPos pos)
	{
		this.startHandle = new Point3d(pos.getX(), pos.getY(), pos.getZ());
	}

	public void setEndHandle(BlockPos pos)
	{
		this.endHandle = new Point3d(pos.getX(), pos.getY(), pos.getZ());
	}

	public Point3d getEndPosPoint()
	{
		return new Point3d(this.end.getX(), this.end.getY(), this.end.getZ());
	}

	public Point3d getStartPosPoint()
	{
		return new Point3d(this.start.getX(), this.start.getY(), this.start.getZ());
	}

}
