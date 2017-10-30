package com.gildedgames.orbis.common.block;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BlockDataContainerDefaultVoid extends BlockDataContainer
{
	private static final BlockData _void = new BlockData(Blocks.STRUCTURE_VOID);

	private BlockDataContainerDefaultVoid(World world)
	{
		super(world);
	}

	public BlockDataContainerDefaultVoid(int width, int height, int length)
	{
		super(width, height, length);
	}


	@Override
	public BlockData get(final int x, final int y, final int z)
	{
		BlockData block = super.get(x, y, z);
		if (block == null)
		{
			return _void;
		}
		return block;
	}

	@Override
	public BlockData defaultBlock()
	{
		return _void;
	}
}
