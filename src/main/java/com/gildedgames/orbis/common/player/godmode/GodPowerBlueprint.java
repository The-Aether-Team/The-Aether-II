package com.gildedgames.orbis.common.player.godmode;

import com.gildedgames.aether.api.orbis_core.api.exceptions.OrbisMissingDataException;
import com.gildedgames.aether.api.orbis_core.api.exceptions.OrbisMissingProjectException;
import com.gildedgames.aether.api.orbis_core.block.BlockDataContainer;
import com.gildedgames.aether.api.orbis_core.data.BlueprintData;
import com.gildedgames.aether.api.orbis_core.data.management.IDataIdentifier;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.AetherGuiHandler;
import com.gildedgames.orbis.client.player.godmode.GodPowerBlueprintClient;
import com.gildedgames.orbis.client.player.godmode.IGodPowerClient;
import com.gildedgames.orbis.common.Orbis;
import com.gildedgames.orbis.common.items.ItemBlockDataContainer;
import com.gildedgames.orbis.common.items.ItemBlueprint;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.selectors.ShapeSelectorBlueprint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class GodPowerBlueprint implements IGodPower
{

	private final ShapeSelectorBlueprint shapeSelector;

	private Rotation placingRotation = Rotation.NONE;

	private GodPowerBlueprintClient clientHandler;

	private BlueprintData placingBlueprint;

	private ItemStack previousStack;

	public GodPowerBlueprint(final World world)
	{
		if (world.isRemote)
		{
			this.clientHandler = new GodPowerBlueprintClient(this);
		}

		this.shapeSelector = new ShapeSelectorBlueprint(this);
	}

	public Rotation getPlacingRotation()
	{
		return this.placingRotation;
	}

	public void setPlacingRotation(final Rotation rotation)
	{
		this.placingRotation = rotation;
	}

	public BlueprintData getPlacingBlueprint()
	{
		return this.placingBlueprint;
	}

	@Override
	public void write(final NBTTagCompound tag)
	{

	}

	@Override
	public void read(final NBTTagCompound tag)
	{

	}

	@Override
	public void onUpdate(final EntityPlayer player, final PlayerOrbisModule module, final boolean isPowerActive)
	{
		final ItemStack stack = player.getHeldItemMainhand();

		if (this.previousStack != stack)
		{
			this.previousStack = stack;
			if (stack.getItem() instanceof ItemBlueprint)
			{
				this.placingBlueprint = null;
				try
				{
					final IDataIdentifier id = ItemBlueprint.getBlueprintId(stack);
					this.placingBlueprint = Orbis.getProjectManager().findData(id);
				}
				catch (final OrbisMissingDataException | OrbisMissingProjectException e)
				{
					AetherCore.LOGGER.error(e);
					player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
				}
			}
			else if (stack.getItem() instanceof ItemBlockDataContainer)
			{
				final BlockDataContainer container = ItemBlockDataContainer.getDatacontainer(player.world, stack);
				this.placingBlueprint = new BlueprintData(container);
			}
			else
			{
				this.placingBlueprint = null;
			}
		}
	}

	@Override
	public boolean hasCustomGui()
	{
		return true;
	}

	@Override
	public void onOpenGui(final EntityPlayer player)
	{
		final BlockPos pos = player.getPosition();

		player.openGui(AetherCore.INSTANCE, AetherGuiHandler.ORBIS_BLUEPRINT_LOAD, player.world, pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public boolean canInteractWithItems(final PlayerOrbisModule module)
	{
		final ItemStack held = module.getEntity().getHeldItemMainhand();

		return held != ItemStack.EMPTY;
	}

	@Nullable
	@Override
	public IShapeSelector getShapeSelector()
	{
		return this.shapeSelector;
	}

	@Override
	public IGodPowerClient getClientHandler()
	{
		return this.clientHandler;
	}
}
