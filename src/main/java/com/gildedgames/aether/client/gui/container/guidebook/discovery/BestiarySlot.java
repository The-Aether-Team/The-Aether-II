package com.gildedgames.aether.client.gui.container.guidebook.discovery;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.travellers_guidebook.entries.TGEntryBestiaryPage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class BestiarySlot extends GuiButton
{
	private static final ResourceLocation DARK_OVERLAY = AetherCore.getResource("textures/gui/inventory/dark_slot_overlay.png");

	private final TGEntryBestiaryPage page;

	private final ResourceLocation discovered, silhouette;

	private final PlayerAether playerAether;

	public BestiarySlot(final PlayerAether playerAether, final int x, final int y, final TGEntryBestiaryPage page)
	{
		super(-1, x, y, 18, 18, "");

		this.playerAether = playerAether;
		this.page = page;
		this.discovered = page.getDiscoveredSlotTexture();
		this.silhouette = page.getSilhouetteSlotTexture();
	}

	public TGEntryBestiaryPage getPage()
	{
		return this.page;
	}

	@Override
	public void drawButton(final Minecraft mc, final int mouseX, final int mouseY, final float partialTicks)
	{
		//super.drawButton(mc, mouseX, mouseY);

		if (this.visible)
		{
			this.hovered =
					mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

			mc.getTextureManager().bindTexture(this.page.isUnlocked(this.playerAether) ? this.discovered : this.silhouette);

			Gui.drawScaledCustomSizeModalRect(this.x, this.y, 0, 0, 16, 16, 16, 16, 16, 16);
		}
	}

}
