package com.gildedgames.orbis.common.player.godmode;

import com.gildedgames.orbis.client.player.godmode.GodPowerCreativeClient;
import com.gildedgames.orbis.client.player.godmode.IGodPowerClient;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.selectors.ShapeSelectorInvalid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class GodPowerCreative implements IGodPower
{

	private final IShapeSelector shapeSelector;

	private GodPowerCreativeClient clientHandler;

	public GodPowerCreative(final World world)
	{
		if (world.isRemote)
		{
			this.clientHandler = new GodPowerCreativeClient(this);
		}

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
	public void onUpdate(final EntityPlayer player, final PlayerOrbisModule module, final boolean isPowerActive)
	{

	}

	@Override
	public boolean hasCustomGui()
	{
		return false;
	}

	@Override
	public void onOpenGui(final EntityPlayer player)
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
