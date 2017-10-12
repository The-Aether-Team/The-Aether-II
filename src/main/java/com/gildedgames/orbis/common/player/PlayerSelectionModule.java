package com.gildedgames.orbis.common.player;

import com.gildedgames.aether.api.orbis.IWorldObjectGroup;
import com.gildedgames.aether.api.orbis.IWorldObjectManager;
import com.gildedgames.aether.api.orbis.region.Region;
import com.gildedgames.aether.api.orbis.shapes.IShape;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.capabilities.world.WorldObjectManager;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.common.network.packets.PacketOrbisActiveSelection;
import com.gildedgames.orbis.common.util.RaytraceHelp;
import com.gildedgames.orbis.common.world_objects.Blueprint;
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
		if (this.selectPos != null)
		{
			final BlockPos endPos = RaytraceHelp.doOrbisRaytrace(this.getPlayer());
			this.hasChanged = false;

			if (this.activeSelection != null && !endPos.equals(this.prevPos))
			{
				this.prevPos = endPos;
				this.hasChanged = true;
				this.activeSelection = new WorldRegion(this.selectPos, endPos, this.getEntity().world);
			}
			else
			{
				//this.activeSelection.setShapeInput(this.selectPos, endPos);
			}
		}

		if (!this.getPlayer().getOrbisModule().inDeveloperMode())
		{
			this.activeSelection = null;
		}
	}

	public boolean setActiveSelectionCorner(final BlockPos pos)
	{
		if (this.activeSelection != null)
		{
			this.selectPos = pos;

			if (this.getEntity().world.isRemote)
			{
				final IWorldObjectManager manager = WorldObjectManager.get(this.getEntity().world);

				final IWorldObjectGroup group = manager.getGroup(0);

				NetworkingAether.sendPacketToServer(new PacketOrbisActiveSelection(this.activeSelection));

				group.addObject(new Blueprint(this.getEntity().world, this.activeSelection.getBoundingBox()));
			}

			this.activeSelection = null;

			return true;
		}
		else
		{
			this.selectPos = pos;

			final IShape newSelection = new Blueprint(this.getEntity().world, new Region(pos, pos));

			//if (power.canSelectShape(this.playerHook, newSelection, this.world))
			{
				this.activeSelection = newSelection;
			}
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