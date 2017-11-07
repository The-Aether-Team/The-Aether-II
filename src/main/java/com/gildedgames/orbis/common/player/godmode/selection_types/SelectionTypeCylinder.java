package com.gildedgames.orbis.common.player.godmode.selection_types;

import com.gildedgames.aether.api.orbis.IShape;
import com.gildedgames.aether.api.orbis_core.data.shapes.CylinderShape;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.orbis.client.player.godmode.selection_types.ISelectionTypeClient;
import com.gildedgames.orbis.client.player.godmode.selection_types.SelectionTypeClientCylinder;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.PlayerSelectionModule;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SelectionTypeCylinder implements ISelectionType
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
			this.client = new SelectionTypeClientCylinder();
		}

		return this.client;
	}

	@Override
	public IShape createShape(final BlockPos start, final BlockPos end, final PlayerOrbisModule module, final World world)
	{
		return new CylinderShape(start, end,
				PlayerAether.getPlayer(module.getEntity()).getSelectionModule().currentSelectionMode() == PlayerSelectionModule.CENTERED);
	}
}
