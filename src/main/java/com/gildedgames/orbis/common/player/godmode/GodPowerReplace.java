package com.gildedgames.orbis.common.player.godmode;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.player.godmode.GodPowerReplaceClient;
import com.gildedgames.orbis.client.player.godmode.IGodPowerClient;
import com.gildedgames.orbis.common.block.BlockFilter;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.selectors.ShapeSelectorFilter;
import com.gildedgames.orbis.common.util.BlockFilterHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;

public class GodPowerReplace implements IGodPower
{

	private final ShapeSelectorFilter shapeSelector;

	private GodPowerReplaceClient clientHandler;

	public GodPowerReplace()
	{
		if (AetherCore.isClient())
		{
			this.clientHandler = new GodPowerReplaceClient(this);
		}

		this.shapeSelector = new ShapeSelectorFilter(itemStack -> new BlockFilter(BlockFilterHelper.getNewReplaceLayer(itemStack)), false);
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
	public void onUpdate(final EntityPlayer player, final PlayerOrbisModule module)
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
		return false;
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
