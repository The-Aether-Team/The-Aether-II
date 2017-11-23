package com.gildedgames.orbis.common.player.godmode.selection_input;

import com.gildedgames.aether.api.orbis.IRegion;
import com.gildedgames.aether.api.orbis.IShape;
import com.gildedgames.aether.api.orbis_core.data.shapes.AbstractShape;
import com.gildedgames.aether.api.orbis_core.util.RotationHelp;
import com.gildedgames.aether.api.world_object.IWorldObject;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.client.player.godmode.selection_inputs.ISelectionInputClient;
import com.gildedgames.orbis.client.player.godmode.selection_inputs.SelectionInputBrushClient;
import com.gildedgames.orbis.common.network.packets.PacketActiveSelection;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.IGodPower;
import com.gildedgames.orbis.common.player.godmode.selectors.IShapeSelector;
import com.gildedgames.orbis.common.util.RaytraceHelp;
import com.gildedgames.orbis.common.world_objects.WorldShape;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.MouseEvent;
import org.lwjgl.input.Mouse;

public class SelectionInputBrush implements ISelectionInput
{
	private final PlayerOrbisModule module;

	private ISelectionInputClient client;

	private IWorldObject activeSelection;

	private BlockPos changingSizeOrigin, changingSizePos;

	private BlockPos prevPlacingPos;

	private boolean changed, changingSize;

	private IGodPower prevPower;

	public SelectionInputBrush(final PlayerOrbisModule module, final World world)
	{
		this.module = module;

		if (world.isRemote)
		{
			this.client = new SelectionInputBrushClient();
		}
	}

	@Override
	public boolean shouldClearSelectionOnEscape()
	{
		return false;
	}

	@Override
	public void onUpdate(final boolean isActive, final IShapeSelector selector)
	{
		final World world = this.module.getWorld();

		if (isActive && selector.isSelectorActive(this.module, this.module.getWorld()))
		{
			if (this.prevPower != this.module.powers().getCurrentPower())
			{
				this.prevPower = this.module.powers().getCurrentPower();

				this.changed = true;
			}

			final BlockPos pos = RaytraceHelp.doOrbisRaytrace(this.module.getPlayer(), this.module.raytraceWithRegionSnapping());

			if (!pos.equals(this.changingSizePos) && this.changingSize)
			{
				this.changingSizePos = pos;

				this.changed = true;
			}

			if (this.activeSelection == null || this.changed)
			{
				final IShape newSelection = this.module.selectionTypes().getCurrentSelectionType()
						.createShape(this.changingSizeOrigin != null ? this.changingSizeOrigin : pos, this.changingSizePos != null ? this.changingSizePos : pos,
								this.module,
								true);

				this.activeSelection = new WorldShape(newSelection, this.module.getWorld());

				this.changed = false;
			}

			if (!this.changingSize)
			{
				final IRegion centered = RotationHelp.regionFromCenter(pos, this.activeSelection.getShape().getBoundingBox(), Rotation.NONE);

				this.activeSelection.setPos(centered.getMin().add(0, -centered.getHeight() / 2, 0));
			}

			if (this.activeSelection.getShape() instanceof AbstractShape)
			{
				final AbstractShape shape = (AbstractShape) this.activeSelection.getShape();

				shape.setCreateFromCenter(true);
				shape.setUniform(true);
			}

			if (world.isRemote)
			{
				if (Mouse.isButtonDown(0) && Minecraft.getMinecraft().currentScreen == null)
				{
					if (!pos.equals(this.prevPlacingPos))
					{
						this.prevPlacingPos = pos;

						if (this.activeSelection != null && selector.canSelectShape(this.module, this.activeSelection.getShape(), this.module.getWorld()))
						{
							if (this.module.getWorld().isRemote)
							{
								NetworkingAether.sendPacketToServer(new PacketActiveSelection(this.activeSelection.getShape()));
							}
						}
					}
				}
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
		if (module.inDeveloperMode() && selector.isSelectorActive(module, module.getWorld()))
		{
			if (event.getButton() == 1 || event.getButton() == 0)
			{
				event.setCanceled(true);

				final IShape selectedShape = module.getSelectedRegion();

				if (selectedShape != null && !module.powers().getCurrentPower().getClientHandler().onRightClickShape(module, selectedShape, event))
				{
					return;
				}
			}

			if (event.getButton() == 1)
			{
				if (event.isButtonstate())
				{
					this.changingSizeOrigin = RaytraceHelp.doOrbisRaytrace(this.module.getPlayer(), this.module.raytraceWithRegionSnapping());

					this.changingSize = true;
				}
				else
				{
					this.changingSize = false;
				}
			}
		}
	}

	@Override
	public void clearSelection()
	{
		this.activeSelection = null;
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
