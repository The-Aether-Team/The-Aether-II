package com.gildedgames.aether.client.gui.container.guidebook.discovery;

import com.gildedgames.aether.client.gui.container.guidebook.AbstractGuidebookPage;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.containers.guidebook.EmptyContainer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public abstract class GuiGuidebookDiscovery extends AbstractGuidebookPage
{
	private static final ResourceLocation LEFT_PAGE = AetherCore.getResource("textures/gui/guidebook/discovery/guidebook_discovery_left.png");

	private static final ResourceLocation RIGHT_PAGE_GENERAL = AetherCore.getResource("textures/gui/guidebook/discovery/guidebook_discovery_right_general.png");

	private DiscoveryTab bestiaryTab;

	private DiscoveryTab structureTab;

	private DiscoveryTab biomeTab;

	private DiscoveryTab characterTab;

	public GuiGuidebookDiscovery(final PlayerAether aePlayer)
	{
		super(aePlayer, new EmptyContainer());
	}

	@Override
	public void initGui()
	{
		this.bestiaryTab = this.addButton(new DiscoveryTab(this.aePlayer, 0, 89, 60, DiscoveryTab.DiscoveryTabType.BESTIARY));
		this.structureTab = this.addButton(new DiscoveryTab(this.aePlayer, 1, 121, 60, DiscoveryTab.DiscoveryTabType.STRUCTURES));
		this.characterTab = this.addButton(new DiscoveryTab(this.aePlayer, 2, 153, 60, DiscoveryTab.DiscoveryTabType.CHARACTERS));
		this.biomeTab = this.addButton(new DiscoveryTab(this.aePlayer, 3, 185, 60, DiscoveryTab.DiscoveryTabType.BIOMES));

		DiscoveryTab.focusedType = this.aePlayer.getProgressModule().getOpenedDiscoveryTabType();
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException
	{
		if (button.enabled)
		{
			switch (button.id)
			{
				case (0):
				{
					this.bestiaryTab.setFocused();
					this.mc.displayGuiScreen(new GuiGuidebookDiscoveryBestiary(this.aePlayer));
					break;
				}
				case (1):
				{
					this.structureTab.setFocused();
					this.mc.displayGuiScreen(new GuiGuidebookDiscoveryStructures(this.aePlayer));
					break;
				}
				case (2):
				{
					this.characterTab.setFocused();
					this.mc.displayGuiScreen(new GuiGuidebookDiscoveryCharacters(this.aePlayer));
					break;
				}
				case (3):
				{
					this.biomeTab.setFocused();
					this.mc.displayGuiScreen(new GuiGuidebookDiscoveryBiomes(this.aePlayer));
					break;
				}
			}
		}
	}

	@Override
	protected void drawLeftPage(final int screenX, final int screenY, final float u, final float v)
	{
		this.mc.renderEngine.bindTexture(LEFT_PAGE);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		Gui.drawModalRectWithCustomSizedTexture(screenX, screenY, u, v, this.PAGE_WIDTH, this.PAGE_HEIGHT, this.TEXTURE_WIDTH, this.TEXTURE_HEIGHT);
	}

	@Override
	protected void drawRightPage(final int screenX, final int screenY, final float u, final float v)
	{
		this.mc.renderEngine.bindTexture(RIGHT_PAGE_GENERAL);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		Gui.drawModalRectWithCustomSizedTexture(screenX, screenY, u, v, this.PAGE_WIDTH, this.PAGE_HEIGHT, this.TEXTURE_WIDTH, this.TEXTURE_HEIGHT);
	}
}
