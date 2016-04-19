package com.gildedgames.aether.common.tile_entities.multiblock;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.util.core.nbt.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;

public class TileEntityMultiblockDummy extends TileEntity implements TileEntityMultiblockInterface
{
	private BlockPos controllerPos;

	@Override
	public void onInteract(EntityPlayer player)
	{
		TileEntity entity = this.worldObj.getTileEntity(controllerPos);

		if (entity instanceof TileEntityMultiblockController)
		{
			((TileEntityMultiblockInterface) entity).onInteract(player);
		}
		else
		{
			AetherCore.LOGGER.warn("TileEntityMultiblockDummy at " + this.pos.toString() + ", is missing it's linked controller at " + this.controllerPos.toString());
		}
	}

	@Override
	public void onDestroyed()
	{
		TileEntity entity = this.worldObj.getTileEntity(controllerPos);

		if (entity instanceof TileEntityMultiblockInterface)
		{
			((TileEntityMultiblockInterface) entity).onDestroyed();
		}
		else
		{
			AetherCore.LOGGER.warn("TileEntityMultiblockDummy at " + this.pos.toString() + ", is missing it's linked controller at " + this.controllerPos.toString());
		}
	}

	public void linkController(BlockPos controllerPos)
	{
		this.controllerPos = controllerPos;
	}

	public BlockPos getLinkedController()
	{
		return this.controllerPos;
	}

	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		this.controllerPos = NBTHelper.readBlockPos(compound, "controller");
	}

	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		NBTHelper.writeBlockPos(compound, "controller", this.controllerPos);
	}
}
