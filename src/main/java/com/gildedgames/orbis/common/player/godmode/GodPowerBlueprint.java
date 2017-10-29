package com.gildedgames.orbis.common.player.godmode;

import com.gildedgames.aether.api.orbis.management.IDataIdentifier;
import com.gildedgames.aether.api.orbis.util.OrbisRotation;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.AetherGuiHandler;
import com.gildedgames.orbis.client.player.godmode.GodPowerBlueprintClient;
import com.gildedgames.orbis.client.player.godmode.IGodPowerClient;
import com.gildedgames.orbis.common.OrbisCore;
import com.gildedgames.orbis.common.data.BlueprintData;
import com.gildedgames.orbis.common.items.ItemBlueprint;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.selectors.ShapeSelectorBlueprint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class GodPowerBlueprint implements IGodPower
{

	private final ShapeSelectorBlueprint shapeSelector;

	private final OrbisRotation placingRotation = OrbisRotation.neutral();

	private GodPowerBlueprintClient clientHandler;

	private IDataIdentifier placingBlueprintId;

	public GodPowerBlueprint()
	{
		if (AetherCore.isClient())
		{
			this.clientHandler = new GodPowerBlueprintClient(this);
		}

		this.shapeSelector = new ShapeSelectorBlueprint(this);
	}

	public OrbisRotation getPlacingRotation()
	{
		return this.placingRotation;
	}

	public BlueprintData getPlacingBlueprint()
	{
		if (this.placingBlueprintId == null)
		{
			return null;
		}

		return OrbisCore.getProjectManager().findData(this.placingBlueprintId);
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

		if (stack.getItem() instanceof ItemBlueprint)
		{
			final IDataIdentifier id = ItemBlueprint.getBlueprintId(stack);

			if ((this.placingBlueprintId == null || !this.placingBlueprintId.equals(id)) && id != null)
			{
				this.placingBlueprintId = id;
			}
		}
		else
		{
			this.placingBlueprintId = null;
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
