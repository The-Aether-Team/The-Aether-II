package com.gildedgames.orbis.common.data.pathway;

import net.minecraft.util.math.BlockPos;

public class Entrance
{
	private BlockPos pos;

	private final PathwayData toConnectTo;

	public Entrance(BlockPos pos, PathwayData toConnectTo)
	{
		this.pos = pos;
		this.toConnectTo = toConnectTo;
	}

	public BlockPos getPos()
	{
		return this.pos;
	}

	public PathwayData toConnectTo()
	{
		return this.toConnectTo;
	}

	public void setPos(BlockPos pos)
	{
		this.pos = pos;
	}
}
