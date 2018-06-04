package com.gildedgames.aether.common.entities.tiles.multiblock;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.tiles.util.TileEntitySynced;
import com.gildedgames.orbis_api.util.mc.NBTHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityMultiblockDummy extends TileEntitySynced implements ITileEntityMultiblock
{
	private BlockPos controllerPosOffset;

	@Override
	public boolean onInteract(final EntityPlayer player)
	{
		if (this.hasLinkedController())
		{
			final TileEntity entity = this.world.getTileEntity(this.getLinkedController());

			if (entity instanceof TileEntityMultiblockController)
			{
				((ITileEntityMultiblock) entity).onInteract(player);

				return true;
			}
			else
			{
				AetherCore.LOGGER.warn("TileEntityMultiblockDummy at " + this.pos.toString() + ", is missing it's linked controller at "
						+ this.getLinkedController().toString());
			}
		}

		return false;
	}

	@Override
	public void onDestroyed()
	{
		if (!this.hasLinkedController())
		{
			return;
		}

		final TileEntity entity = this.world.getTileEntity(this.getLinkedController());

		if (entity instanceof ITileEntityMultiblock)
		{
			((ITileEntityMultiblock) entity).onDestroyed();
		}
		else
		{
			AetherCore.LOGGER.warn("TileEntityMultiblockDummy at " + this.pos.toString() + ", is missing it's linked controller at "
					+ this.getLinkedController().toString());
		}
	}

	@Override
	public ItemStack getPickedStack(final World world, final BlockPos pos, final IBlockState state)
	{
		if (!this.hasLinkedController())
		{
			return ItemStack.EMPTY;
		}

		final TileEntity entity = this.world.getTileEntity(this.getLinkedController());

		if (entity instanceof ITileEntityMultiblock)
		{
			return ((ITileEntityMultiblock) entity).getPickedStack(world, pos, state);
		}
		else
		{
			AetherCore.LOGGER.warn("TileEntityMultiblockDummy at " + this.pos.toString() + ", is missing it's linked controller at "
					+ this.getLinkedController().toString());
		}

		return ItemStack.EMPTY;
	}

	public void linkController(final BlockPos controllerPos)
	{
		this.controllerPosOffset = controllerPos.add(-this.pos.getX(), -this.pos.getY(), -this.pos.getZ());
	}

	public BlockPos getLinkedController()
	{
		return this.getPos().add(this.controllerPosOffset);
	}

	public boolean hasLinkedController()
	{
		return this.controllerPosOffset != null;
	}

	@Override
	public void readFromNBT(final NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		if (compound.hasKey("controller"))
		{
			this.controllerPosOffset = NBTHelper.readBlockPos(compound.getCompoundTag("controller"));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		compound.setTag("controller", NBTHelper.writeBlockPos(this.controllerPosOffset));

		return compound;
	}

}
