package com.gildedgames.aether.client.gui.container.guidebook.discovery;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiGuidebookDiscoveryBestiary extends GuiGuidebookDiscovery
{
	private static final ResourceLocation RIGHT_PAGE_MOB = AetherCore.getResource("textures/gui/guidebook/discovery/guidebook_discovery_right_mob.png");

	public GuiGuidebookDiscoveryBestiary(final PlayerAether aePlayer)
	{
		super(aePlayer);
	}

	@Override
	public void initGui()
	{
		super.initGui();
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException
	{
		super.actionPerformed(button);
	}

	@Override
	protected void drawRightPage(final int screenX, final int screenY, final float u, final float v)
	{
		this.mc.renderEngine.bindTexture(RIGHT_PAGE_MOB);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		Gui.drawModalRectWithCustomSizedTexture(screenX, screenY, u, v, this.PAGE_WIDTH, this.PAGE_HEIGHT, this.TEXTURE_WIDTH, this.TEXTURE_HEIGHT);
	}
}
