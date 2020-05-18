package com.gildedgames.aether.client.gui.container.guidebook.discovery;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.orbis.lib.client.gui.util.GuiAbstractButton;
import com.gildedgames.orbis.lib.client.gui.util.GuiTexture;
import com.gildedgames.orbis.lib.client.rect.Dim2D;
import com.gildedgames.orbis.lib.client.rect.Pos2D;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DiscoveryTab extends GuiAbstractButton
{
	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/gui/guidebook/discovery/guidebook_discovery_tab.png");

	private static final ResourceLocation TEXTURE_PRESSED = AetherCore.getResource("textures/gui/guidebook/discovery/guidebook_discovery_tab_pressed.png");

	private final DiscoveryTabType type;

	private ResourceLocation icon;

	public DiscoveryTab(final Pos2D pos, final DiscoveryTabType type, final DiscoveryTabType selectedType, final ResourceLocation icon)
	{
		super(Dim2D.build().width(32).height(20).pos(pos).flush(),
				new GuiTexture(Dim2D.build().width(32).height(20).flush(), TEXTURE),
				new GuiTexture(Dim2D.build().width(32).height(20).flush(), TEXTURE_PRESSED),
				new GuiTexture(Dim2D.build().width(32).height(20).flush(), TEXTURE));

		this.type = type;

		this.icon = icon;

		if (type == selectedType)
		{
			this.setSelected(true);
		}
	}

	@Override
	public void build()
	{
		super.build();

		GuiTexture icon = new GuiTexture(Dim2D.build().width(22).height(16).x(5).y(3).flush(), this.icon);

		this.context().addChildren(icon);
	}

	public DiscoveryTabType getType()
	{
		return this.type;
	}

	public enum DiscoveryTabType
	{
		BESTIARY
				{
					@Override
					@SideOnly(Side.CLIENT)
					public GuiContainer createPageContainer(final PlayerAether playerAether)
					{
						return new GuiGuidebookDiscoveryBestiary(null, playerAether);
					}
				},
		EFFECTS
				{
					@Override
					@SideOnly(Side.CLIENT)
					public GuiContainer createPageContainer(final PlayerAether playerAether)
					{
						return new GuiGuidebookDiscoveryEffects(null, playerAether);
					}
				},
		LANDMARKS
				{
					@Override
					@SideOnly(Side.CLIENT)
					public GuiContainer createPageContainer(final PlayerAether playerAether)
					{
						return new GuiGuidebookDiscoveryLandmarks(null, playerAether);
					}
				},
		CHARACTERS
			{
				@Override
				@SideOnly(Side.CLIENT)
				public GuiContainer createPageContainer(final PlayerAether playerAether)
				{
					return new GuiGuidebookDiscoveryCharacters(null, playerAether);
				}
			};

		DiscoveryTabType()
		{

		}

		@SideOnly(Side.CLIENT)
		public GuiContainer createPageContainer(final PlayerAether playerAether)
		{
			return null;
		}
	}
}
