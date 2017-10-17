package com.gildedgames.orbis.common.player;

import com.gildedgames.aether.api.orbis.region.Region;
import com.gildedgames.aether.api.orbis.shapes.IShape;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.common.network.packets.PacketOrbisActiveSelection;
import com.gildedgames.orbis.common.player.godmode.IGodPower;
import com.gildedgames.orbis.common.player.godmode.IShapeSelector;
import com.gildedgames.orbis.common.util.RaytraceHelp;
import com.gildedgames.orbis.common.world_objects.WorldRegion;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class PlayerSelectionModule extends PlayerAetherModule
{

	private BlockPos selectPos, prevPos;

	private boolean hasChanged;

	private IShape activeSelection;

	public PlayerSelectionModule(final PlayerAether playerAether)
	{
		super(playerAether);
	}

	public boolean hasChanged()
	{
		return this.hasChanged;
	}

	@Override
	public void onUpdate()
	{
		final PlayerOrbisModule module = PlayerOrbisModule.get(this.getEntity());
		final IGodPower power = module.powers().getCurrentPower();

		final IShapeSelector selector = power.getShapeSelector();

		if (this.selectPos != null)
		{
			final BlockPos endPos = RaytraceHelp.doOrbisRaytrace(this.getPlayer());
			this.hasChanged = false;

			if (this.activeSelection != null && !endPos.equals(this.prevPos))
			{
				this.prevPos = endPos;
				this.hasChanged = true;
				this.activeSelection = new WorldRegion(this.selectPos, endPos, this.getWorld());
			}
			else
			{
				//this.activeSelection.setShapeInput(this.selectPos, endPos);
			}
		}

		if (!this.getPlayer().getOrbisModule().inDeveloperMode() || !selector.isSelectorActive(module, this.getWorld()))
		{
			this.activeSelection = null;
		}
	}

	public boolean setActiveSelectionCorner(final BlockPos pos)
	{
		final PlayerOrbisModule module = PlayerOrbisModule.get(this.getEntity());
		final IGodPower power = module.powers().getCurrentPower();

		final IShapeSelector selector = power.getShapeSelector();

		if (this.activeSelection != null)
		{
			if (selector.canSelectShape(module, this.activeSelection, this.getWorld()))
			{
				this.selectPos = pos;

				if (this.getEntity().world.isRemote)
				{
					NetworkingAether.sendPacketToServer(new PacketOrbisActiveSelection(this.activeSelection));
				}

				selector.onSelect(module, this.activeSelection, this.getWorld());

				this.activeSelection = null;

				return true;
			}
		}
		else if (selector.isSelectorActive(module, this.getWorld()))
		{
			this.selectPos = pos;

			final IShape newSelection = new WorldRegion(new Region(pos, pos), this.getEntity().world);

			this.activeSelection = newSelection;
		}

		return false;
	}

	public IShape getActiveSelection()
	{
		return this.activeSelection;
	}

	public void setActiveSelection(final IShape activeSelection)
	{
		this.activeSelection = activeSelection;

		if (this.getEntity().getEntityWorld().isRemote && this.activeSelection != null)
		{
			NetworkingAether.sendPacketToServer(new PacketOrbisActiveSelection(this.activeSelection));
		}
	}

	@Override
	public void write(final NBTTagCompound output)
	{

	}

	@Override
	public void read(final NBTTagCompound input)
	{

	}

}