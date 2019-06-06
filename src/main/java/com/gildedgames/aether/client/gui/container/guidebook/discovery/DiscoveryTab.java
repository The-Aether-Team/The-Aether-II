package com.gildedgames.aether.client.gui.container.guidebook.discovery;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.lib.client.gui.util.GuiAbstractButton;
import com.gildedgames.orbis.lib.client.gui.util.GuiTexture;
import com.gildedgames.orbis.lib.client.rect.Dim2D;
import com.gildedgames.orbis.lib.client.rect.Pos2D;
import net.minecraft.util.ResourceLocation;

public class DiscoveryTab extends GuiAbstractButton
{
	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/gui/guidebook/discovery/guidebook_discovery_tab.png");

	private static final ResourceLocation TEXTURE_PRESSED = AetherCore.getResource("textures/gui/guidebook/discovery/guidebook_discovery_tab_pressed.png");

	private final DiscoveryTabType type;

	public DiscoveryTab(final Pos2D pos, final DiscoveryTabType type, final DiscoveryTabType selectedType)
	{
		super(Dim2D.build().width(32).height(20).pos(pos).flush(),
				new GuiTexture(Dim2D.build().width(32).height(20).flush(), TEXTURE),
				new GuiTexture(Dim2D.build().width(32).height(20).flush(), TEXTURE_PRESSED),
				new GuiTexture(Dim2D.build().width(32).height(20).flush(), TEXTURE));

		this.type = type;

		if (type == selectedType)
		{
			this.setSelected(true);
		}
	}

	public DiscoveryTabType getType()
	{
		return this.type;
	}

	public enum DiscoveryTabType
	{
		BESTIARY, BIOMES, STRUCTURES, CHARACTERS
	}
}
