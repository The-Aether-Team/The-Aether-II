package com.gildedgames.orbis.common.player.godmode.selection_types;

import com.gildedgames.aether.api.orbis.IRegion;
import com.gildedgames.aether.api.orbis.IShape;
import com.gildedgames.aether.api.orbis_core.data.region.Region;
import com.gildedgames.aether.api.orbis_core.data.shapes.EllipsoidShape;
import com.gildedgames.aether.api.orbis_core.util.RegionHelp;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.player.godmode.selection_types.ISelectionTypeClient;
import com.gildedgames.orbis.client.player.godmode.selection_types.SelectionTypeClientEllipsoid;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SelectionTypeEllipsoid implements ISelectionType
{
	private ISelectionTypeClient client;

	@Override
	public void write(final NBTTagCompound tag)
	{

	}

	@Override
	public void read(final NBTTagCompound tag)
	{

	}

	@Override
	public ISelectionTypeClient getClient()
	{
		if (AetherCore.isClient() && this.client == null)
		{
			this.client = new SelectionTypeClientEllipsoid();
		}

		return this.client;
	}

	@Override
	public IShape createShape(final BlockPos start, final BlockPos end, final PlayerOrbisModule module, final World world)
	{
		final IRegion region = new Region(start, end);

		final int radiusX = 1 + (region.getWidth() / 2);
		final int radiusY = 1 + (region.getHeight() / 2);
		final int radiusZ = 1 + (region.getLength() / 2);

		return new EllipsoidShape(RegionHelp.getCenter(region), radiusX, radiusY, radiusZ);

	}
}
