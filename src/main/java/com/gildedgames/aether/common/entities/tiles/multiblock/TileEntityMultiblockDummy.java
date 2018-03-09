package com.gildedgames.aether.common.entities.tiles.multiblock;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.tiles.util.TileEntitySynced;
import com.gildedgames.orbis.api.util.mc.NBTHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityMultiblockDummy extends TileEntitySynced implements TileEntityMultiblockInterface
{
	private BlockPos controllerPos;

	@Override
	public void onInteract(final EntityPlayer player)
	{
		if (!hasLinkedController())
		{
			return;
		}
		final TileEntity entity = this.world.getTileEntity(this.controllerPos);

		if (entity instanceof TileEntityMultiblockController)
		{
			((TileEntityMultiblockInterface) entity).onInteract(player);
		}
		else
		{
			AetherCore.LOGGER.warn("TileEntityMultiblockDummy at " + this.pos.toString() + ", is missing it's linked controller at "
					+ this.controllerPos.toString());
		}
	}

	@Override
	public void onDestroyed()
	{
		if (!hasLinkedController())
		{
			return;
		}
		final TileEntity entity = this.world.getTileEntity(this.controllerPos);

		if (entity instanceof TileEntityMultiblockInterface)
		{
			((TileEntityMultiblockInterface) entity).onDestroyed();
		}
		else
		{
			AetherCore.LOGGER.warn("TileEntityMultiblockDummy at " + this.pos.toString() + ", is missing it's linked controller at "
					+ this.controllerPos.toString());
		}
	}

	@Override
	public ItemStack getPickedStack(final World world, final BlockPos pos, final IBlockState state)
	{
		if (!hasLinkedController())
		{
			return ItemStack.EMPTY;
		}
		final TileEntity entity = this.world.getTileEntity(this.controllerPos);

		if (entity instanceof TileEntityMultiblockInterface)
		{
			return ((TileEntityMultiblockInterface) entity).getPickedStack(world, pos, state);
		}
		else
		{
			AetherCore.LOGGER.warn("TileEntityMultiblockDummy at " + this.pos.toString() + ", is missing it's linked controller at "
					+ this.controllerPos.toString());
		}

		return ItemStack.EMPTY;
	}

	public void linkController(final BlockPos controllerPos)
	{
		this.controllerPos = controllerPos;
	}

	public BlockPos getLinkedController()
	{
		return this.controllerPos;
	}

	public boolean hasLinkedController() { return this.controllerPos != null; }

	@Override
	public void readFromNBT(final NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		if (compound.hasKey("controller"))
		{
			this.controllerPos = NBTHelper.readBlockPos(compound.getCompoundTag("controller"));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		compound.setTag("controller", NBTHelper.writeBlockPos(this.controllerPos));

		return compound;
	}

}
