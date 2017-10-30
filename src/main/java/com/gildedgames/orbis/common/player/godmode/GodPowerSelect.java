package com.gildedgames.orbis.common.player.godmode;

import com.gildedgames.orbis.client.player.godmode.GodPowerSelectClient;
import com.gildedgames.orbis.client.player.godmode.IGodPowerClient;
import com.gildedgames.orbis.common.items.ItemBlockDataContainer;
import com.gildedgames.orbis.common.items.ItemBlueprint;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.selectors.ShapeSelectorSelect;
import com.gildedgames.orbis.common.world_objects.WorldShape;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class GodPowerSelect implements IGodPower
{

	private final ShapeSelectorSelect shapeSelector;

	private GodPowerSelectClient clientHandler;

	private WorldShape selectedRegion;

	public GodPowerSelect(final World world)
	{
		if (world.isRemote)
		{
			this.clientHandler = new GodPowerSelectClient(this);
		}

		this.shapeSelector = new ShapeSelectorSelect(this);
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
	public void write(final NBTTagCompound tag)
	{

	}

	@Override
	public void read(final NBTTagCompound tag)
	{

	}

	public WorldShape getSelectedRegion()
	{
		return this.selectedRegion;
	}

	public void setSelectedRegion(final WorldShape region)
	{
		this.selectedRegion = region;
	}
}
