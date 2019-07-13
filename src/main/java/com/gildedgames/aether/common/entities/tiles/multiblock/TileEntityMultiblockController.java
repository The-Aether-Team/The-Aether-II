package com.gildedgames.aether.common.entities.tiles.multiblock;

import com.gildedgames.aether.common.blocks.multiblock.BlockMultiController;
import com.gildedgames.aether.common.entities.tiles.TileEntitySynced;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class TileEntityMultiblockController extends TileEntitySynced implements ITileEntityMultiblock
{

	private final BlockMultiController block;

	private final Block dummy;

	public TileEntityMultiblockController(BlockMultiController block, Block dummy)
	{
		this.block = block;
		this.dummy = dummy;
	}

	public void rebuild()
	{
		for (BlockPos.MutableBlockPos pos : this.block.getMultiblockVolumeIterator(this.pos, this.getWorld()))
		{
			if (this.pos.equals(pos))
			{
				continue;
			}

			this.world.setBlockState(pos, this.dummy.getDefaultState(), 3);
			this.world.notifyBlockUpdate(pos, Blocks.AIR.getDefaultState(), this.dummy.getDefaultState(), 2);

			TileEntityMultiblockDummy te = (TileEntityMultiblockDummy) this.world.getTileEntity(pos);
			te.linkController(new BlockPos(this.pos));
		}
	}

	@Override
	public void onDestroyed()
	{
		for (BlockPos.MutableBlockPos pos : this.block.getMultiblockVolumeIterator(this.pos, this.getWorld()))
		{
			if (this.doesControllerOwn(pos))
			{
				this.world.removeTileEntity(pos);
				this.world.removeBlock(pos, false);
			}
		}
	}

	public boolean doesControllerOwn(BlockPos pos)
	{
		TileEntity entity = this.world.getTileEntity(pos);

		if (entity instanceof TileEntityMultiblockController)
		{
			return true;
		}
		else if (entity instanceof TileEntityMultiblockDummy)
		{
			TileEntityMultiblockDummy dummy = (TileEntityMultiblockDummy) entity;

			return dummy.getLinkedController().equals(this.pos);
		}

		return false;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		Iterable<BlockPos.MutableBlockPos> itPos = this.block.getMultiblockVolumeIterator(this.pos, this.world);

		BlockPos min = this.pos;
		BlockPos max = this.pos;

		for (BlockPos.MutableBlockPos pos : itPos)
		{
			max = pos;
		}

		return new AxisAlignedBB(min, max.add(1, 1, 1));
	}

}
