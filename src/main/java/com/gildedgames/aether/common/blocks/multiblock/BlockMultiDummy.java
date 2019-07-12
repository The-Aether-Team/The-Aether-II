package com.gildedgames.aether.common.blocks.multiblock;

import com.gildedgames.aether.common.blocks.IInternalBlock;
import com.gildedgames.aether.common.entities.tiles.multiblock.TileEntityMultiblockDummy;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockMultiDummy extends BlockMultiBase implements IInternalBlock
{
	public BlockMultiDummy()
	{
		super(Material.ROCK);

		this.setBlockUnbreakable();
		this.setResistance(6000000.0F);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.INVISIBLE;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityMultiblockDummy();
	}
}
