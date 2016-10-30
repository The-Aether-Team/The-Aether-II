package com.gildedgames.aether.common.blocks.dungeon;

import com.gildedgames.aether.common.tiles.TileEntityLabyrinthBridge;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockLabyrinthBridge extends BlockLabyrinthPillar implements ITileEntityProvider
{

	public BlockLabyrinthBridge()
	{
		super();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityLabyrinthBridge();
	}

}
