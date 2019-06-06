package com.gildedgames.aether.client.gui.container.guidebook.discovery;

import com.gildedgames.aether.client.gui.dialog.GuiAbstractButton;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class DiscoveryTab extends GuiAbstractButton
{
	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/gui/guidebook/discovery/guidebook_discovery_tab.png");

	private static final ResourceLocation TEXTURE_PRESSED = AetherCore.getResource("textures/gui/guidebook/discovery/guidebook_discovery_tab_pressed.png");

	public static DiscoveryTabType focusedType;

	private final DiscoveryTabType type;

	private final PlayerAether playerAether;

	public DiscoveryTab(final PlayerAether playerAether, final int buttonId, final int x, final int y, final DiscoveryTabType type)
	{
		super(buttonId, x, y, 32, 20);

		this.playerAether = playerAether;
		this.type = type;
	}

	public boolean isFocused()
	{
		return focusedType == this.type;
	}

	public void setFocused()
	{
		focusedType = this.type;
		this.playerAether.getProgressModule().setOpenedDiscoveryTabType(this.type);
	}

	@Override
	public void init(final FontRenderer fontRenderer)
	{

	}

	@Override
	public void draw(final FontRenderer fontRenderer)
	{
		final Minecraft mc = Minecraft.getMinecraft();
		mc.getTextureManager().bindTexture(this.isFocused() ? TEXTURE_PRESSED : TEXTURE);

		final int height = this.isFocused() ? 20 : 17;

		Gui.drawScaledCustomSizeModalRect(this.x, this.y + (this.isFocused() ? 1 : 3), 0, 0, 32, height, 32, height, 32, height);
	}

	public enum DiscoveryTabType
	{
		BESTIARY, BIOMES, STRUCTURES, CHARACTERS
	}
}
