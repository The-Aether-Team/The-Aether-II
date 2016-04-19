package com.gildedgames.aether.common.blocks.util.multiblock;

import com.gildedgames.aether.common.tile_entities.multiblock.TileEntityMultiblockDummy;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMultiDummy extends BlockMultiBase
{
	public BlockMultiDummy()
	{
		super(Material.rock);
	}

	@Override
	public int getRenderType()
	{
		return -1;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityMultiblockDummy();
	}
}
