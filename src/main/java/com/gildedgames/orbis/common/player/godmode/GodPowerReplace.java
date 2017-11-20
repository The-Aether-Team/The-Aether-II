package com.gildedgames.orbis.common.player.godmode;

import com.gildedgames.aether.api.orbis_core.block.BlockFilter;
import com.gildedgames.aether.api.orbis_core.util.BlockFilterHelper;
import com.gildedgames.orbis.client.player.godmode.GodPowerReplaceClient;
import com.gildedgames.orbis.client.player.godmode.IGodPowerClient;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.selectors.IShapeSelector;
import com.gildedgames.orbis.common.player.godmode.selectors.ShapeSelectorFilter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class GodPowerReplace implements IGodPower
{

	private final ShapeSelectorFilter shapeSelector;

	private GodPowerReplaceClient clientHandler;

	public GodPowerReplace(final World world)
	{
		if (world.isRemote)
		{
			this.clientHandler = new GodPowerReplaceClient(this);
		}

		this.shapeSelector = new ShapeSelectorFilter(
				p -> new BlockFilter(BlockFilterHelper.getNewReplaceLayer(p.getHeldItemMainhand(), p.getHeldItemOffhand())), false);
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
