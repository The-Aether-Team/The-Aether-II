package com.gildedgames.aether.common.blocks.util.multiblock;

import com.gildedgames.aether.common.tile_entities.multiblock.TileEntityMultiblockController;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class BlockMultiController extends BlockMultiBase
{
	protected BlockMultiController(Material material)
	{
		super(material);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		this.rebuild(worldIn, pos);
	}

	public void rebuild(World world, BlockPos pos)
	{
		TileEntity te = world.getTileEntity(pos);

		if (te instanceof TileEntityMultiblockController)
		{
			TileEntityMultiblockController controller = (TileEntityMultiblockController) te;

			controller.rebuild();
		}
	}

	@Override
	public int getRenderType()
	{
		return 2;
	}
}
