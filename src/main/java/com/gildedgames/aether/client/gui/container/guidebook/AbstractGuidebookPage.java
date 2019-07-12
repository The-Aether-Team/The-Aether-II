package com.gildedgames.aether.client.gui.container.guidebook;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractGuidebookPage extends ContainerScreen
{
	private static final ResourceLocation TEXTURE_BASE = AetherCore.getResource("textures/gui/guidebook/guidebook_base.png");

	public static final ResourceLocation TEXTURE_GUI = AetherCore.getResource("textures/gui/guidebook/guidebook_gui.png");

	protected final int TEXTURE_WIDTH = 512; // size of entire texture width.

	protected final int TEXTURE_HEIGHT = 256; // size of entire texture height.

	protected final int PAGE_WIDTH = 188; // width of page in texture.

	protected final int PAGE_HEIGHT = 185; // height of page in texture.

	protected final PlayerAether aePlayer;

	public AbstractGuidebookPage(final PlayerAether aePlayer, Container container)
	{
		super(container);

		this.allowUserInput = true;
		this.aePlayer = aePlayer;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		this.guiLeft = (this.width/2) - 153 - 11;
		this.guiTop = (this.height/2) - 69;

		this.xSize = 176*2;
		this.ySize = 169;
	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTick)
	{
		super.drawScreen(mouseX,mouseY, partialTick);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		this.drawWorldBackground(0);

		this.mc.renderEngine.bindTexture(TEXTURE_BASE);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		AbstractGui.drawModalRectWithCustomSizedTexture((this.width/2) - 176 - 11, this.height/2 - 185/2, 0, 0, 376, 256, 512, 256);

		/* Draw abstract methods to render left and right pages.
		 *  Note that these calls have predetermined parameters which should align properly to the screen.
		 *  Proper page texture parameters can be seen in this class' instance variables.
		 */

		this.drawLeftPage((this.width/2) - 176 - 11, (this.height/2) - (185/2), 0, 0);

		this.drawRightPage((this.width/2) - 12, (this.height/2) - (185/2), this.PAGE_WIDTH-13, 0);
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
	abstract protected void drawLeftPage(int screenX, int screenY, float u, float v);

	/**
	 * Called by #drawGuiContainerBackgroundLayer
	 * handles the render calls for drawing the right page of the guidebook.
	 * Contains any logic calls to dictate what page should be called and texture binding.
	 * @param screenX x position on the screen.
	 * @param screenY y position on the screen.
	 * @param u texture x.
	 * @param v texture y.
	 */
	abstract protected void drawRightPage(int screenX, int screenY, float u, float v);
}
