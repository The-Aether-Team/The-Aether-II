package com.gildedgames.orbis.common.player.godmode;

import com.gildedgames.aether.api.orbis.IWorldObject;
import com.gildedgames.aether.api.orbis.region.IRegion;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.player.godmode.GodPowerDeleteClient;
import com.gildedgames.orbis.client.player.godmode.GodPowerSelectClient;
import com.gildedgames.orbis.client.player.godmode.IGodPowerClient;
import com.gildedgames.orbis.common.block.BlockFilter;
import com.gildedgames.orbis.common.items.ItemBlockDataContainer;
import com.gildedgames.orbis.common.items.ItemBlueprint;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.selectors.ShapeSelectorFilter;
import com.gildedgames.orbis.common.player.godmode.selectors.ShapeSelectorSelect;
import com.gildedgames.orbis.common.util.BlockFilterHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GodPowerSelect implements IGodPower
{

	private GodPowerSelectClient clientHandler;

	private final ShapeSelectorSelect shapeSelector;

	private IWorldObject selectedRegion;

	public GodPowerSelect()
	{
		if (AetherCore.isClient())
		{
			this.clientHandler = new GodPowerSelectClient(this);
		}

		this.shapeSelector = new ShapeSelectorSelect(this);
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
		final ItemStack held = module.getEntity().getHeldItemMainhand();

		return held != ItemStack.EMPTY && (held.getItem() instanceof ItemBlock
				|| held.getItem() instanceof ItemBlueprint
				|| held.getItem() instanceof ItemBlockDataContainer);
	}

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

	@Override
	public void write(NBTTagCompound tag)
	{

	}

	@Override
	public void read(NBTTagCompound tag)
	{

	}

	public IWorldObject getSelectedRegion()
	{
		return this.selectedRegion;
	}

	public void setSelectedRegion(IWorldObject region)
	{
		this.selectedRegion = region;
	}
}
