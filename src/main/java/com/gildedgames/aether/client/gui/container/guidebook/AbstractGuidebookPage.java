package com.gildedgames.aether.client.gui.container.guidebook;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.orbis.lib.client.gui.util.GuiTexture;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.*;
import com.gildedgames.orbis.lib.client.rect.Dim2D;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public abstract class AbstractGuidebookPage extends GuiViewer
{
	public static final ResourceLocation TEXTURE_GUI = AetherCore.getResource("textures/gui/guidebook/guidebook_gui.png");

	private static final ResourceLocation TEXTURE_BASE = AetherCore.getResource("textures/gui/guidebook/guidebook_base.png");

	protected final int TEXTURE_WIDTH = 512; // size of entire texture width.

	protected final int TEXTURE_HEIGHT = 256; // size of entire texture height.

	protected final int PAGE_WIDTH = 176; // width of page in texture.

	protected final int PAGE_HEIGHT = 185; // height of page in texture.

	protected final PlayerAether aePlayer;

	public AbstractGuidebookPage(final IGuiViewer prevViewer, final PlayerAether aePlayer, final Container container)
	{
		super(new GuiElement(Dim2D.flush(), false), prevViewer, container);

		this.allowUserInput = true;
		this.aePlayer = aePlayer;
	}

	@Override
	public void build(final IGuiContext context)
	{
		final GuiTexture baseBook = new GuiTexture(
				Dim2D.build().x((this.width / 2) - 176 - 11).y(this.height / 2 - 185 / 2).width(375).height(198).flush(),
				TEXTURE_BASE);

		context.addChildren(baseBook);

		final List<IGuiElement> leftPageElements = this.createLeftPage((this.width / 2) - this.PAGE_WIDTH + 1, (this.height / 2) - (185 / 2), 0, 0);
		final List<IGuiElement> rightPageElements = this.createRightPage(this.width / 2, (this.height / 2) - (185 / 2), this.PAGE_WIDTH - 13, 0);

		for (final IGuiElement e : leftPageElements)
		{
			context.addChildren(e);
		}

		for (final IGuiElement e : rightPageElements)
		{
			context.addChildren(e);
		}
	}

	@Override
	public void initGui()
	{
		super.initGui();

		this.guiLeft = (this.width / 2) - 153 - 11;
		this.guiTop = (this.height / 2) - 69;

		this.xSize = 176 * 2;
		this.ySize = 169;
	}

	/**
	 * Called by #drawGuiContainerBackgroundLayer
	 * handles the render calls for drawing the left page of the guidebook.
	 * Contains any logic calls to dictate what page should be called and texture binding.
	 * @param screenX x position on the screen.
	 * @param screenY y position on the screen.
	 * @param u texture x.
	 * @param v texture y.
	 */
	abstract protected List<IGuiElement> createLeftPage(int screenX, int screenY, float u, float v);

	/**
	 * Called by #drawGuiContainerBackgroundLayer
	 * handles the render calls for drawing the right page of the guidebook.
	 * Contains any logic calls to dictate what page should be called and texture binding.
	 * @param screenX x position on the screen.
	 * @param screenY y position on the screen.
	 * @param u texture x.
	 * @param v texture y.
	 */
	abstract protected List<IGuiElement> createRightPage(int screenX, int screenY, float u, float v);
}
