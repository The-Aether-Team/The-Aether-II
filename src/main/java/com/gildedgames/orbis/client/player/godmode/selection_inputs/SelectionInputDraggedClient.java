package com.gildedgames.orbis.client.player.godmode.selection_inputs;

import com.gildedgames.aether.api.orbis.IWorldRenderer;
import com.gildedgames.aether.api.world_object.IWorldObject;
import com.gildedgames.orbis.client.gui.util.GuiTexture;
import com.gildedgames.orbis.client.renderers.RenderShape;
import com.gildedgames.orbis.client.util.rect.Dim2D;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.selection_input.ISelectionInput;
import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.List;

public class SelectionInputDraggedClient implements ISelectionInputClient
{
	private final GuiTexture icon;

	private final String displayName;

	private RenderShape activeSelectionRender;

	public SelectionInputDraggedClient(final String displayName, final ResourceLocation texture)
	{
		this.displayName = displayName;
		this.icon = new GuiTexture(Dim2D.build().width(14).height(14).flush(), texture);
	}

	@Override
	public String displayName()
	{
		return this.displayName;
	}

	@Override
	public GuiTexture getIcon()
	{
		return this.icon;
	}

	@Override
	public List<IWorldRenderer> getActiveRenderers(final ISelectionInput server, final PlayerOrbisModule module, final World world)
	{
		final List<IWorldRenderer> renderers = Lists.newArrayList();

		final IWorldObject activeSelection = module.selectionInputs().getCurrentSelectionInput().getActiveSelection();

		if (activeSelection != null)
		{
			if (this.activeSelectionRender == null)
			{
				this.activeSelectionRender = new RenderShape(activeSelection.getShape());

				this.activeSelectionRender.useCustomColors = true;
			}
			else if (activeSelection != null)
			{
				this.activeSelectionRender.colorBorder = module.powers().getCurrentPower().getClientHandler().getShapeColor(module);
				this.activeSelectionRender.colorGrid = module.powers().getCurrentPower().getClientHandler().getShapeColor(module);

				this.activeSelectionRender.useCustomColors = true;

				this.activeSelectionRender.setShape(activeSelection.getShape());
			}
			else
			{
				this.activeSelectionRender.setShape(null);
			}

			renderers.add(this.activeSelectionRender);
		}

		return renderers;
	}
}
