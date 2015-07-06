package com.gildedgames.aether.common.blocks.construction;

import com.gildedgames.aether.common.tile_entities.TileEntityAltar;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAltar extends Block implements ITileEntityProvider
{
	public BlockAltar()
	{
		super(Material.rock);

		this.setHardness(2.0f);

		this.setStepSound(Block.soundTypeStone);
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isFullCube()
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityAltar();
	}
}
