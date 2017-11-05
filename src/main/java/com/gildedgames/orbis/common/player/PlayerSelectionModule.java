package com.gildedgames.orbis.common.player;

import com.gildedgames.aether.api.orbis.IShape;
import com.gildedgames.aether.api.orbis_core.data.shapes.AbstractShape;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.common.network.packets.PacketActiveSelection;
import com.gildedgames.orbis.common.player.godmode.IGodPower;
import com.gildedgames.orbis.common.player.godmode.IShapeSelector;
import com.gildedgames.orbis.common.util.RaytraceHelp;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class PlayerSelectionModule extends PlayerAetherModule
{

	public static final int NON_UNIFORM = 0, CENTERED = 1, UNIFORM = 2;

	public static final int MODES = 3;

	private BlockPos selectPos, prevPos;

	private boolean hasChanged;

	private IShape activeSelection;

	private int modeIndex;

	public PlayerSelectionModule(final PlayerAether playerAether)
	{
		super(playerAether);
	}

	public int currentSelectionMode()
	{
		return this.modeIndex;
	}

	public void nextSelectionMode()
	{
		if (this.modeIndex + 1 >= MODES)
		{
			this.modeIndex = 0;
		}
		else
		{
			this.modeIndex++;
		}

		if (this.activeSelection instanceof AbstractShape)
		{
			this.processSelectionMode((AbstractShape) this.activeSelection);
		}

		this.refreshSelection();
	}

	public void refreshSelection()
	{
		if (this.selectPos != null)
		{
			final PlayerOrbisModule module = PlayerOrbisModule.get(this.getEntity());

			final BlockPos endPos = RaytraceHelp.doOrbisRaytrace(this.getPlayer());

			this.prevPos = endPos;
			this.hasChanged = true;
			this.activeSelection = module.selectionTypes().getCurrentSelectionType().createShape(this.selectPos, endPos, module, this.getEntity().world);
		}
	}

	public void processSelectionMode()
	{
		if (this.activeSelection instanceof AbstractShape)
		{
			this.processSelectionMode((AbstractShape) this.activeSelection);
		}
	}

	private void processSelectionMode(final AbstractShape shape)
	{
		switch (this.modeIndex)
		{
			case (NON_UNIFORM):
				shape.setCreateFromCenter(false);
				shape.setUniform(false);
				break;
			case (CENTERED):
				shape.setCreateFromCenter(true);
				shape.setUniform(true);
				break;
			case (UNIFORM):
				shape.setCreateFromCenter(false);
				shape.setUniform(true);
				break;
		}
	}

	public void setHasChanged(final boolean flag)
	{
		this.hasChanged = flag;
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

		if (this.activeSelection instanceof AbstractShape)
		{
			this.processSelectionMode((AbstractShape) this.activeSelection);
		}

		if (this.selectPos != null)
		{
			final BlockPos endPos = RaytraceHelp.doOrbisRaytrace(this.getPlayer());

			if (this.activeSelection != null && !endPos.equals(this.prevPos))
			{
				this.prevPos = endPos;
				this.activeSelection = module.selectionTypes().getCurrentSelectionType().createShape(this.selectPos, endPos, module, this.getEntity().world);
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
					NetworkingAether.sendPacketToServer(new PacketActiveSelection(this.activeSelection));
				}

				selector.onSelect(module, this.activeSelection, this.getWorld());

				this.activeSelection = null;
				this.selectPos = null;
				this.prevPos = null;

				return true;
			}
		}
		else if (selector.isSelectorActive(module, this.getWorld()))
		{
			this.selectPos = pos;

			final IShape newSelection = module.selectionTypes().getCurrentSelectionType().createShape(pos, pos, module, this.getEntity().world);

			this.activeSelection = newSelection;
		}

		if (this.activeSelection instanceof AbstractShape)
		{
			this.processSelectionMode((AbstractShape) this.activeSelection);
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

		if (this.activeSelection instanceof AbstractShape)
		{
			this.processSelectionMode((AbstractShape) this.activeSelection);
		}

		if (this.getEntity().getEntityWorld().isRemote && this.activeSelection != null)
		{
			NetworkingAether.sendPacketToServer(new PacketActiveSelection(this.activeSelection));
		}
	}

	public void clearSelection()
	{
		this.activeSelection = null;
		this.selectPos = null;
		this.prevPos = null;
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