package com.gildedgames.orbis.common.player.godmode.selection_input;

import com.gildedgames.aether.api.orbis.IShape;
import com.gildedgames.aether.api.orbis_core.data.shapes.AbstractShape;
import com.gildedgames.aether.api.world_object.IWorldObject;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.client.player.godmode.selection_inputs.ISelectionInputClient;
import com.gildedgames.orbis.client.player.godmode.selection_inputs.SelectionInputDraggedClient;
import com.gildedgames.orbis.common.network.packets.PacketActiveSelection;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.IGodPower;
import com.gildedgames.orbis.common.player.godmode.selectors.IShapeSelector;
import com.gildedgames.orbis.common.util.OrbisRaytraceHelp;
import com.gildedgames.orbis.common.util.RaytraceHelp;
import com.gildedgames.orbis.common.world_objects.WorldShape;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.MouseEvent;

public class SelectionInputDragged implements ISelectionInput
{
	private final EntityPlayer player;

	private ISelectionInputClient client;

	private BlockPos selectPos, prevPos;

	private IWorldObject activeSelection;

	private boolean createFromCenter, uniform;

	public SelectionInputDragged(final EntityPlayer player, final String displayName, final ResourceLocation texture)
	{
		this.player = player;

		if (player.getEntityWorld().isRemote)
		{
			this.client = new SelectionInputDraggedClient(displayName, texture);
		}
	}

	public SelectionInputDragged createFromCenter(final boolean flag)
	{
		this.createFromCenter = flag;

		return this;
	}

	public SelectionInputDragged uniform(final boolean flag)
	{
		this.uniform = flag;

		return this;
	}

	private void processSelectionMode(final AbstractShape shape)
	{
		shape.setCreateFromCenter(this.createFromCenter);
		shape.setUniform(this.uniform);
	}

	private boolean setActiveSelectionCorner(final BlockPos pos)
	{
		final PlayerOrbisModule module = PlayerOrbisModule.get(this.player);
		final IGodPower power = module.powers().getCurrentPower();

		IShapeSelector selector = power.getShapeSelector();

		final ItemStack held = module.getEntity().getHeldItemMainhand();

		if (held.getItem() instanceof IShapeSelector)
		{
			selector = (IShapeSelector) held.getItem();
		}

		if (this.activeSelection != null)
		{
			if (this.activeSelection.getShape() instanceof AbstractShape)
			{
				this.processSelectionMode((AbstractShape) this.activeSelection.getShape());
			}

			if (selector.canSelectShape(module, this.activeSelection.getShape(), module.getWorld()))
			{
				this.selectPos = pos;

				if (module.getWorld().isRemote)
				{
					NetworkingAether.sendPacketToServer(new PacketActiveSelection(this.activeSelection.getShape()));
				}

				this.activeSelection = null;
				this.selectPos = null;
				this.prevPos = null;

				return true;
			}
		}
		else if (selector.isSelectorActive(module, module.getWorld()))
		{
			this.selectPos = pos;

			final IShape newSelection = module.selectionTypes().getCurrentSelectionType().createShape(pos, pos, module, this.createFromCenter);

			this.activeSelection = new WorldShape(newSelection, this.player.getEntityWorld());
		}

		return false;
	}

	@Override
	public boolean shouldClearSelectionOnEscape()
	{
		return true;
	}

	@Override
	public void onUpdate(final boolean isActive, final IShapeSelector selector)
	{
		final PlayerOrbisModule module = PlayerOrbisModule.get(this.player);

		if (isActive)
		{
			if (this.selectPos != null)
			{
				final BlockPos endPos = RaytraceHelp.doOrbisRaytrace(module.getPlayer());

				if (this.activeSelection != null && !endPos.equals(this.prevPos))
				{
					this.prevPos = endPos;
					final IShape newSelection = module.selectionTypes().getCurrentSelectionType()
							.createShape(this.selectPos, endPos, module, this.createFromCenter);

					this.activeSelection = new WorldShape(newSelection, this.player.getEntityWorld());
				}
			}

			if (!module.inDeveloperMode() || !selector.isSelectorActive(module, module.getWorld()))
			{
				this.activeSelection = null;
			}

			if (this.activeSelection != null && this.activeSelection.getShape() instanceof AbstractShape)
			{
				this.processSelectionMode((AbstractShape) this.activeSelection.getShape());
			}
		}
		else
		{
			this.activeSelection = null;
		}
	}

	@Override
	public void onMouseEvent(final MouseEvent event, final IShapeSelector selector, final PlayerOrbisModule module)
	{
		if (event.getButton() == 0 || event.getButton() == 1)
		{
			if (module.inDeveloperMode() && selector.isSelectorActive(module, module.getWorld()))
			{
				event.setCanceled(true);

				final IShape selectedShape = module.getSelectedRegion();

				if (selectedShape == null || module.powers().getCurrentPower().getClientHandler().onRightClickShape(module, selectedShape, event))
				{
					if (!event.isButtonstate() && this.getActiveSelection() != null || event.isButtonstate() && this.getActiveSelection() == null)
					{
						final BlockPos pos = OrbisRaytraceHelp.raytraceNoSnapping(module.getEntity());
						this.setActiveSelectionCorner(pos);
					}
				}
			}
		}
	}

	@Override
	public void clearSelection()
	{
		this.activeSelection = null;
		this.selectPos = null;
		this.prevPos = null;
	}

	@Override
	public IWorldObject getActiveSelection()
	{
		return this.activeSelection;
	}

	@Override
	public void setActiveSelection(final IWorldObject activeSelection)
	{
		this.activeSelection = activeSelection;

		if (this.activeSelection instanceof AbstractShape)
		{
			this.processSelectionMode((AbstractShape) this.activeSelection);
		}

		if (this.player.getEntityWorld().isRemote && this.activeSelection != null)
		{
			NetworkingAether.sendPacketToServer(new PacketActiveSelection(this.activeSelection.getShape()));
		}
	}

	@Override
	public ISelectionInputClient getClient()
	{
		return this.client;
	}

	@Override
	public void write(final NBTTagCompound tag)
	{

	}

	@Override
	public void read(final NBTTagCompound tag)
	{

	}
}
