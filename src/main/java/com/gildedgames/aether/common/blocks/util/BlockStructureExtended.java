package com.gildedgames.aether.common.blocks.util;

import com.gildedgames.aether.common.tile_entities.TileEntityStructureExtended;
import net.minecraft.block.BlockStructure;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockStructureExtended extends BlockStructure
{

	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityStructureExtended();
	}

}
