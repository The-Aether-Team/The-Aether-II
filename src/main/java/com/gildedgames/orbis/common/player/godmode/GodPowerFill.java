package com.gildedgames.orbis.common.player.godmode;

import com.gildedgames.aether.api.orbis_core.block.BlockFilter;
import com.gildedgames.aether.api.orbis_core.util.BlockFilterHelper;
import com.gildedgames.orbis.client.player.godmode.GodPowerFillClient;
import com.gildedgames.orbis.client.player.godmode.IGodPowerClient;
import com.gildedgames.orbis.common.containers.inventory.InventoryBlockForge;
import com.gildedgames.orbis.common.containers.util.StagedInventory;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.selectors.IShapeSelector;
import com.gildedgames.orbis.common.player.godmode.selectors.ShapeSelectorFilter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class GodPowerFill implements IGodPower
{

	private final ShapeSelectorFilter shapeSelector;

	private final StagedInventory<InventoryBlockForge> stagedInventory;

	private GodPowerFillClient clientHandler = null;

	public GodPowerFill(final PlayerOrbisModule module, final World world)
	{
		if (world.isRemote)
		{
			this.clientHandler = new GodPowerFillClient(this);
		}

		this.shapeSelector = new ShapeSelectorFilter(p -> new BlockFilter(BlockFilterHelper.getNewFillLayer(p.getHeldItemMainhand())), false);
		this.stagedInventory = new StagedInventory<>(module, () -> new InventoryBlockForge(module.getEntity()),
				m -> m.powers().getFillPower().getStagedInventory(), "blockForge");
	}

	public StagedInventory<InventoryBlockForge> getStagedInventory()
	{
		return this.stagedInventory;
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

	}

	@Override
	public boolean hasCustomGui()
	{
		return true;
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
