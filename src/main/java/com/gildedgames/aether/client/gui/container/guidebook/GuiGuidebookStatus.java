package com.gildedgames.aether.client.gui.container.guidebook;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.containers.guidebook.EmptyContainer;
import net.minecraft.client.gui.AbstractGui;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiGuidebookStatus extends AbstractGuidebookPage
{
	private static final ResourceLocation LEFT_PAGE = AetherCore.getResource("textures/gui/guidebook/status/guidebook_status_left.png");

	private static final ResourceLocation RIGHT_PAGE = AetherCore.getResource("textures/gui/guidebook/status/guidebook_status_right.png");

	public GuiGuidebookStatus(final PlayerAether aePlayer)
	{
		super(aePlayer, new EmptyContainer());
	}

	@Override
	protected void drawLeftPage(int screenX, int screenY, float u, float v)
	{
		this.mc.renderEngine.bindTexture(LEFT_PAGE);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		AbstractGui.drawModalRectWithCustomSizedTexture(screenX, screenY, u, v, this.PAGE_WIDTH, this.PAGE_HEIGHT, this.TEXTURE_WIDTH, this.TEXTURE_HEIGHT);
	}

	@Override
	protected void drawRightPage(int screenX, int screenY, float u, float v)
	{
		this.mc.renderEngine.bindTexture(RIGHT_PAGE);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		AbstractGui.drawModalRectWithCustomSizedTexture(screenX, screenY, u ,v, this.PAGE_WIDTH, this.PAGE_HEIGHT,this.TEXTURE_WIDTH, this.TEXTURE_HEIGHT);
	}
}
