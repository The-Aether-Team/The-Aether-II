package com.gildedgames.aether.common.blocks.multiblock;

import com.gildedgames.aether.common.entities.tiles.multiblock.TileEntityMultiblockController;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class BlockMultiController extends BlockMultiBase
{
	protected BlockMultiController(Properties properties)
	{
		super(properties);
	}

	public abstract Iterable<BlockPos> getMultiblockVolumeIterator(BlockPos pos, IWorldReader world);

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos)
	{
		for (BlockPos otherPos : this.getMultiblockVolumeIterator(pos, world))
		{
			BlockState otherState = world.getBlockState(otherPos);

			if (!otherState.getMaterial().isReplaceable())
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public void onBlockAdded(BlockState state1, World world, BlockPos pos, BlockState state2, boolean isMoving)
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
