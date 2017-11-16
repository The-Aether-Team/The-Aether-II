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
import com.gildedgames.orbis.common.containers.inventory.InventoryBlueprintForge;
import com.gildedgames.orbis.common.containers.util.StagedInventory;
import com.gildedgames.orbis.common.data.BlueprintPalette;
import com.gildedgames.orbis.common.items.ItemBlockDataContainer;
import com.gildedgames.orbis.common.items.ItemBlueprint;
import com.gildedgames.orbis.common.items.ItemBlueprintPalette;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.selectors.ShapeSelectorBlueprint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class GodPowerBlueprint implements IGodPower
{

	private final ShapeSelectorBlueprint shapeSelector;

	private final StagedInventory<InventoryBlueprintForge> stagedInventory;

	private Rotation placingRotation = Rotation.NONE;

	private GodPowerBlueprintClient clientHandler;

	private BlueprintData placingBlueprint;

	private BlueprintPalette placingPalette;

	private ItemStack previousStack;

	public GodPowerBlueprint(final PlayerOrbisModule module, final World world)
	{
		if (world.isRemote)
		{
			this.clientHandler = new GodPowerBlueprintClient(this);
		}

		this.shapeSelector = new ShapeSelectorBlueprint(this);
		this.stagedInventory = new StagedInventory<>(module, () -> new InventoryBlueprintForge(module.getEntity()),
				m -> m.powers().getBlueprintPower().getStagedInventory(), "blueprintForge");
	}

	public StagedInventory<InventoryBlueprintForge> getStagedInventory()
	{
		return this.stagedInventory;
	}

	public Rotation getPlacingRotation()
	{
		return this.placingRotation;
	}

	public void setPlacingRotation(final Rotation rotation)
	{
		this.placingRotation = rotation;
	}

	public BlueprintPalette getPlacingPalette()
	{
		return this.placingPalette;
	}

	public BlueprintData getPlacingBlueprint()
	{
		return this.placingBlueprint;
	}

	public IInventory getForgeInventory()
	{
		return this.stagedInventory.get();
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

			if (stack.getItem() instanceof ItemBlueprintPalette)
			{
				this.placingBlueprint = null;

				this.placingPalette = ItemBlueprintPalette.getBlueprintPalette(stack);
			}
			else if (stack.getItem() instanceof ItemBlueprint)
			{
				this.placingPalette = null;
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
				this.placingPalette = null;
				final BlockDataContainer container = ItemBlockDataContainer.getDataContainer(stack);

				if (container != null)
				{
					this.placingBlueprint = new BlueprintData(container);
				}
			}
			else
			{
				this.placingPalette = null;
				this.placingBlueprint = null;
			}
		}
		else if (stack.getItem() instanceof ItemBlueprintPalette && this.placingPalette == null)
		{
			this.placingBlueprint = null;

			this.placingPalette = ItemBlueprintPalette.getBlueprintPalette(stack);
		}
		else if (stack.getItem() instanceof ItemBlockDataContainer && this.placingBlueprint == null)
		{
			this.placingPalette = null;

			final BlockDataContainer container = ItemBlockDataContainer.getDataContainer(stack);

			if (container != null)
			{
				this.placingBlueprint = new BlueprintData(container);
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
