package com.gildedgames.orbis.common.player.godmode;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.player.godmode.GodPowerDeleteClient;
import com.gildedgames.orbis.client.player.godmode.GodPowerSelectClient;
import com.gildedgames.orbis.client.player.godmode.IGodPowerClient;
import com.gildedgames.orbis.common.block.BlockFilter;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.selectors.ShapeSelectorFilter;
import com.gildedgames.orbis.common.util.BlockFilterHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class GodPowerSelect implements IGodPower
{

	private GodPowerSelectClient clientHandler;

	private final ShapeSelectorFilter shapeSelector;

	public GodPowerSelect()
	{
		if (AetherCore.isClient())
		{
			this.clientHandler = new GodPowerSelectClient(this);
		}

		this.shapeSelector = new ShapeSelectorFilter(p -> new BlockFilter(BlockFilterHelper.getNewDeleteLayer(p.getHeldItemMainhand())), true);
	}

	@Override
	public void onUpdate(EntityPlayer player, PlayerOrbisModule module, boolean isPowerActive)
	{

	}

	@Override
	public boolean hasCustomGui()
	{
		return false;
	}

	@Override
	public void onOpenGui(EntityPlayer player)
	{

	}

	@Override
	public boolean canInteractWithItems(PlayerOrbisModule module)
	{
		return false;
	}

	@Override
	public IShapeSelector getShapeSelector()
	{
		return null;
	}

	@Override
	public IGodPowerClient getClientHandler()
	{
		return null;
	}

	@Override
	public void write(NBTTagCompound tag)
	{

	}

	@Override
	public void read(NBTTagCompound tag)
	{

	}
}
