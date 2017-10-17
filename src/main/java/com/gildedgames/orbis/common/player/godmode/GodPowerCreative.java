package com.gildedgames.orbis.common.player.godmode;

import com.gildedgames.orbis.client.player.godmode.GodPowerCreativeClient;
import com.gildedgames.orbis.client.player.godmode.IGodPowerClient;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.selectors.ShapeSelectorInvalid;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;

public class GodPowerCreative implements IGodPower
{

	private final GodPowerCreativeClient clientHandler;

	private final IShapeSelector shapeSelector;

	public GodPowerCreative()
	{
		this.clientHandler = new GodPowerCreativeClient(this);
		this.shapeSelector = new ShapeSelectorInvalid();
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
		return true;
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
