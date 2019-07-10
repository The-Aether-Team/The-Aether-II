package com.gildedgames.aether.common.world.spawning.util;

import com.gildedgames.aether.api.world.spawn.IPositionSelector;
import com.gildedgames.orbis.lib.util.mc.BlockUtil;
import net.minecraft.world.World;

public class OffsetFromTopBlockPositionSelector implements IPositionSelector
{
	private final int offset;

	public OffsetFromTopBlockPositionSelector(int offset)
	{
		this.offset = offset;
	}

	@Override
	public int getPosY(World world, int posX, int posZ)
	{
		return BlockUtil.getTopBlockHeight(world, posX, posZ) + this.offset;
	}

	@Override
	public int getScatter(World world)
	{
		return 4;
	}
}
