package com.gildedgames.aether.common.blocks.multiblock;

import com.gildedgames.aether.common.entities.tiles.multiblock.TileEntityMultiblockController;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class BlockMultiController extends BlockMultiBase
{
	protected BlockMultiController(Properties properties)
	{
		super(properties);
	}

	public abstract Iterable<BlockPos.MutableBlockPos> getMultiblockVolumeIterator(BlockPos pos, World world);

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos placePos)
	{
		for (BlockPos pos : this.getMultiblockVolumeIterator(placePos, world))
		{
			BlockState state = world.getBlockState(pos);

			if (!state.getBlock().isReplaceable(world, pos))
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, BlockState state)
	{
		TileEntity te = world.getTileEntity(pos);

		if (te instanceof TileEntityMultiblockController)
		{
			TileEntityMultiblockController controller = (TileEntityMultiblockController) te;

			controller.rebuild();
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.INVISIBLE;
	}
}
