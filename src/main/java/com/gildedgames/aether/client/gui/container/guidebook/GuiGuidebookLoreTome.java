package com.gildedgames.aether.client.gui.container.guidebook;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.containers.guidebook.EmptyContainer;
import com.gildedgames.orbis.lib.client.gui.util.GuiTexture;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.IGuiElement;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.IGuiViewer;
import com.gildedgames.orbis.lib.client.rect.Dim2D;
import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class GuiGuidebookLoreTome extends AbstractGuidebookPage
{
	private static final ResourceLocation LEFT_PAGE = AetherCore.getResource("textures/gui/guidebook/lore_tome/guidebook_loretome_left.png");

	private static final ResourceLocation RIGHT_PAGE = AetherCore.getResource("textures/gui/guidebook/lore_tome/guidebook_loretome_right.png");

	public GuiGuidebookLoreTome(final IGuiViewer prevViewer, final PlayerAether aePlayer)
	{
		super(prevViewer, aePlayer, new EmptyContainer());
	}

	@Override
	protected List<IGuiElement> createLeftPage(final int screenX, final int screenY, final float u, final float v)
	{
		final GuiTexture leftPage = new GuiTexture(Dim2D.build().width(this.PAGE_WIDTH).height(this.PAGE_HEIGHT).x(screenX).y(screenY).flush(),
				LEFT_PAGE);

		return Lists.newArrayList(leftPage);
	}

	@Override
	protected List<IGuiElement> createRightPage(final int screenX, final int screenY, final float u, final float v)
	{
		final GuiTexture rightPage = new GuiTexture(Dim2D.build().width(this.PAGE_WIDTH).height(this.PAGE_HEIGHT).x(screenX).y(screenY).flush(),
				RIGHT_PAGE);

		return Lists.newArrayList(rightPage);
	}
}
