package com.gildedgames.aether.client.gui.container.guidebook;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.containers.guidebook.ContainerGuidebookInventory;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public abstract class AbstractGuidebookPage<T extends Container> extends ContainerScreen<T>
{
	private static final ResourceLocation TEXTURE_BASE = AetherCore.getResource("textures/gui/guidebook/guidebook_base.png");

	public static final ResourceLocation TEXTURE_GUI = AetherCore.getResource("textures/gui/guidebook/guidebook_gui.png");

	protected final int TEXTURE_WIDTH = 512; // size of entire texture width.

	protected final int TEXTURE_HEIGHT = 256; // size of entire texture height.

	protected final int PAGE_WIDTH = 188; // width of page in texture.

	protected final int PAGE_HEIGHT = 185; // height of page in texture.

	protected final PlayerAether aePlayer;

	public AbstractGuidebookPage(T container, PlayerAether aePlayer, ITextComponent title)
	{
		super(container, aePlayer.getEntity().inventory, title);

		this.aePlayer = aePlayer;
	}

	@Override
	public void init()
	{
		super.init();

		this.guiLeft = (this.width/2) - 153 - 11;
		this.guiTop = (this.height/2) - 69;

		this.xSize = 176*2;
		this.ySize = 169;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		this.renderBackground(0);

		this.minecraft.getTextureManager().bindTexture(TEXTURE_BASE);

		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);

		AbstractGui.blit((this.width/2) - 176 - 11, this.height/2 - 185/2, 0, 0, 376, 256, 512, 256);

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
