package com.gildedgames.aether.client.gui.container.guidebook;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractGuidebookPage extends GuiContainer
{
	private static final ResourceLocation TEXTURE_BASE = AetherCore.getResource("textures/gui/guidebook/guidebook_base.png");

	protected static final ResourceLocation TEXTURE_GUI = AetherCore.getResource("textures/gui/guidebook/guidebook_gui.png");

	protected final int TEXTURE_WIDTH = 512;

	protected final int TEXTURE_HEIGHT = 256;

	protected final int PAGE_WIDTH = 188;

	protected final int PAGE_HEIGHT = 185;

	protected final PlayerAether aePlayer;

	public AbstractGuidebookPage(final PlayerAether aePlayer, ContainerPlayer container)
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

		Gui.drawModalRectWithCustomSizedTexture((this.width/2) - 176 - 11, this.height/2 - 185/2, 0, 0, 376, 256, 512, 256);

		this.drawLeftPage();

		this.drawRightPage();
	}

	abstract protected void drawLeftPage();

	abstract protected void drawRightPage();
}
