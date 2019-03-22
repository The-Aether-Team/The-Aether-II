package com.gildedgames.aether.client.gui.container.guidebook;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.containers.guidebook.EmptyContainer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiGuidebookLoreTome extends AbstractGuidebookPage
{
	private static final ResourceLocation LEFT_PAGE = AetherCore.getResource("textures/gui/guidebook/lore_tome/guidebook_loretome_left.png");
	private static final ResourceLocation RIGHT_PAGE = AetherCore.getResource("textures/gui/guidebook/lore_tome/guidebook_loretome_right.png");

	public GuiGuidebookLoreTome(final PlayerAether aePlayer)
	{
		super(aePlayer, new EmptyContainer(aePlayer));
	}

	@Override
	protected void drawLeftPage()
	{
		this.mc.renderEngine.bindTexture(LEFT_PAGE);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		Gui.drawModalRectWithCustomSizedTexture((this.width/2) - 176 - 11, this.height/2 - 185/2, 0, 0, this.PAGE_WIDTH, this.PAGE_HEIGHT, this.TEXTURE_WIDTH, this.TEXTURE_HEIGHT);
	}

	@Override
	protected void drawRightPage()
	{
		this.mc.renderEngine.bindTexture(RIGHT_PAGE);

		int rightPageCoordX = (this.width/2) - 12;
		int rightPageCoordY = (this.height/2) - (185/2);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		Gui.drawModalRectWithCustomSizedTexture(rightPageCoordX, rightPageCoordY, this.PAGE_WIDTH - 13 ,0, this.PAGE_WIDTH, this.PAGE_HEIGHT,this.TEXTURE_WIDTH, this.TEXTURE_HEIGHT);
	}
}
