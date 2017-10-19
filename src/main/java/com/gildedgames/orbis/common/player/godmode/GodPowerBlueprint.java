package com.gildedgames.orbis.common.player.godmode;

import com.gildedgames.aether.api.orbis.util.OrbisRotation;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.player.godmode.GodPowerBlueprintClient;
import com.gildedgames.orbis.client.player.godmode.IGodPowerClient;
import com.gildedgames.orbis.common.data.BlueprintData;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.selectors.ShapeSelectorBlueprint;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;

public class GodPowerBlueprint implements IGodPower
{

	private final ShapeSelectorBlueprint shapeSelector;

	private final OrbisRotation placingRotation = OrbisRotation.neutral();

	private GodPowerBlueprintClient clientHandler;

	private BlueprintData placingBlueprint;

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
		return this.placingBlueprint;
	}

	public void setPlacingBlueprint(final BlueprintData placingBlueprint)
	{
		this.placingBlueprint = placingBlueprint;
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
