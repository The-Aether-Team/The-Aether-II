package com.gildedgames.aether.client.gui.container.guidebook.discovery;

import com.gildedgames.aether.api.travellers_guidebook.ITGManager;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.travellers_guidebook.entries.TGEntryBestiaryPage;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.List;

public class GuiGuidebookDiscoveryBestiary extends GuiGuidebookDiscovery
{
	private static final ResourceLocation RIGHT_PAGE_MOB = AetherCore.getResource("textures/gui/guidebook/discovery/guidebook_discovery_right_mob.png");

	private List<TGEntryBestiaryPage> bestiaryEntries;

	private List<BestiarySlot> slots;

	private ResourceLocation bestiaryFrame;

	private String beastTitle;

	public GuiGuidebookDiscoveryBestiary(final PlayerAether aePlayer)
	{
		super(aePlayer);
	}

	@Override
	public void initGui()
	{
		super.initGui();

		final ITGManager tgManager = AetherCore.PROXY.content().tgManager();

		this.bestiaryEntries = tgManager.getEntriesWithTagAndClass("bestiary", TGEntryBestiaryPage.class);
		this.slots = Lists.newArrayList();

		for (int i = 0; i < this.bestiaryEntries.size(); i++)
		{
			final TGEntryBestiaryPage page = this.bestiaryEntries.get(i);

			final int x = 96 + ((i % 6) * 18);
			final int y = 95 + ((i / 6) * 18);

			this.slots.add(this.addButton(new BestiarySlot(this.aePlayer, x, y, page)));
		}
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException
	{
		super.actionPerformed(button);

		if (button instanceof BestiarySlot)
		{
			final BestiarySlot slot = (BestiarySlot) button;
			final TGEntryBestiaryPage page = slot.getPage();
			final boolean isUnderstood = page.isUnderstood(this.aePlayer);

			this.bestiaryFrame = isUnderstood ? page.getDiscoveredTexture() : page.getSilhouetteTexture();
			this.beastTitle = isUnderstood ? page.getEntityName() : "???";
		}
	}

	@Override
	protected void drawRightPage(final int screenX, final int screenY, final float u, final float v)
	{
		this.mc.renderEngine.bindTexture(RIGHT_PAGE_MOB);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		Gui.drawModalRectWithCustomSizedTexture(screenX, screenY, u, v, this.PAGE_WIDTH, this.PAGE_HEIGHT, this.TEXTURE_WIDTH, this.TEXTURE_HEIGHT);

		if (this.bestiaryFrame != null)
		{
			this.mc.renderEngine.bindTexture(this.bestiaryFrame);
			Gui.drawModalRectWithCustomSizedTexture(screenX + 37, screenY + 27, 0, 0, 58, 71, 58, 71);
		}

		if (this.beastTitle != null)
		{
			this.drawCenteredString(this.mc.fontRenderer, this.beastTitle, screenX + 100, screenY + 10, 0xFFFFFF);
		}
	}
}
