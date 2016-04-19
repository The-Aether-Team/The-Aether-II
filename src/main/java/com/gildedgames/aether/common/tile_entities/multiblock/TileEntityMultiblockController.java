package com.gildedgames.aether.common.tile_entities.multiblock;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.util.core.util.GGHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public abstract class TileEntityMultiblockController extends TileEntity implements TileEntityMultiblockInterface
{
	/**
	 * Returns the size this controller takes up in the world. This will be filled with
	 * dummy blocks.
	 *
	 * @return The controller's volume in the world.
	 */
	public abstract AxisAlignedBB getBoundingBox();

	/**
	 * Fills {@link TileEntityMultiblockController#getBoundingBox()} with {@link TileEntityMultiblockDummy} and
	 * links them to this controller
	 */
	public void rebuild()
	{
		for (BlockPos.MutableBlockPos pos : GGHelper.getInBox(this.getBoundingBox()))
		{
			if (this.worldObj.getTileEntity(pos) == this)
			{
				continue;
			}

			this.worldObj.setBlockState(pos, BlocksAether.multiblock_dummy.getDefaultState());

			TileEntityMultiblockDummy te = (TileEntityMultiblockDummy) this.worldObj.getTileEntity(pos);
			te.linkController(new BlockPos(this.pos));
		}
	}

	@Override
	public void onDestroyed()
	{
		for (BlockPos.MutableBlockPos pos : GGHelper.getInBox(this.getBoundingBox()))
		{
			if (this.doesControllerOwn(pos))
			{
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
}
