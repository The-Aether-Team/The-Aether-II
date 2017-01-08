package com.gildedgames.aether.common.tiles.multiblock;

import com.gildedgames.aether.common.blocks.util.multiblock.BlockMultiController;
import com.gildedgames.aether.common.tiles.util.TileEntitySynced;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class TileEntityMultiblockController extends TileEntitySynced implements TileEntityMultiblockInterface
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

			this.worldObj.setBlockState(pos, this.dummy.getDefaultState(), 3);
			this.worldObj.notifyBlockUpdate(pos, Blocks.AIR.getDefaultState(), this.dummy.getDefaultState(), 2);

			TileEntityMultiblockDummy te = (TileEntityMultiblockDummy) this.worldObj.getTileEntity(pos);
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
				this.worldObj.removeTileEntity(pos);
				this.worldObj.setBlockToAir(pos);
			}
		}
	}

	public boolean doesControllerOwn(BlockPos pos)
	{
		TileEntity entity = this.worldObj.getTileEntity(pos);

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
	@SideOnly(Side.CLIENT)
	public net.minecraft.util.math.AxisAlignedBB getRenderBoundingBox()
	{
		Iterable<BlockPos.MutableBlockPos> itPos = this.block.getMultiblockVolumeIterator(this.pos, this.worldObj);

		BlockPos min = this.pos;
		BlockPos max = this.pos;

		for (BlockPos.MutableBlockPos pos : itPos)
		{
			max = pos;
		}

		AxisAlignedBB bb = new net.minecraft.util.math.AxisAlignedBB(min, max.add(1, 1, 1));

		return bb;
	}

}
