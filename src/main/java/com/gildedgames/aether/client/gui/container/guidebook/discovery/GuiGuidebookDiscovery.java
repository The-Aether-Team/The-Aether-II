package com.gildedgames.aether.client.gui.container.guidebook.discovery;

import com.gildedgames.aether.client.gui.container.guidebook.AbstractGuidebookPage;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerProgressModule;
import com.gildedgames.aether.common.containers.guidebook.EmptyContainer;
import com.gildedgames.orbis.lib.client.gui.data.Text;
import com.gildedgames.orbis.lib.client.gui.util.GuiText;
import com.gildedgames.orbis.lib.client.gui.util.GuiTexture;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.IGuiContext;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.IGuiElement;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.IGuiViewer;
import com.gildedgames.orbis.lib.client.rect.Dim2D;
import com.gildedgames.orbis.lib.client.rect.Pos2D;
import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.List;

public class GuiGuidebookDiscovery extends AbstractGuidebookPage
{
	private static final ResourceLocation LEFT_PAGE = AetherCore.getResource("textures/gui/guidebook/discovery/guidebook_discovery_left.png");

	private static final ResourceLocation RIGHT_PAGE_GENERAL = AetherCore.getResource("textures/gui/guidebook/discovery/guidebook_discovery_right_general.png");

	private static final ResourceLocation BESTIARY_ICON = AetherCore.getResource("textures/gui/guidebook/discovery/icon_bestiary.png");

	private static final ResourceLocation EFFECTS_ICON = AetherCore.getResource("textures/gui/guidebook/discovery/icon_effects.png");

	private static final ResourceLocation LANDMARK_ICON = AetherCore.getResource("textures/gui/guidebook/discovery/icon_landmark.png");

	private static final ResourceLocation CHARACTER_ICON = AetherCore.getResource("textures/gui/guidebook/discovery/icon_character.png");

	private DiscoveryTab bestiaryTab;

	private DiscoveryTab effectTab;

	private DiscoveryTab landmarkTab;

	private DiscoveryTab characterTab;

	public GuiGuidebookDiscovery(final IGuiViewer prevViewer, final PlayerAether aePlayer)
	{
		super(prevViewer, aePlayer, new EmptyContainer());
	}

	@Override
	public void build(final IGuiContext context)
	{
		super.build(context);
	}

	private void onClickTab(final DiscoveryTab tab)
	{
		this.bestiaryTab.setSelected(false);
		this.effectTab.setSelected(false);
		this.landmarkTab.setSelected(false);
		this.characterTab.setSelected(false);

		this.aePlayer.getModule(PlayerProgressModule.class).setOpenedDiscoveryTabType(tab.getType());

		tab.setSelected(true);

		switch (tab.getType())
		{
			case BESTIARY:
			{
				this.mc.displayGuiScreen(new GuiGuidebookDiscoveryBestiary(this, this.aePlayer));
				break;
			}
			case EFFECTS:
			{
				this.mc.displayGuiScreen(new GuiGuidebookDiscoveryEffects(this, this.aePlayer));
				break;
			}
			case LANDMARKS:
			{
				this.mc.displayGuiScreen(new GuiGuidebookDiscoveryLandmarks(this, this.aePlayer));
				break;
			}
			case CHARACTERS:
			{
				this.mc.displayGuiScreen(new GuiGuidebookDiscoveryCharacters(this, this.aePlayer));
				break;
			}
		}
	}

	@Override
	protected List<IGuiElement> createLeftPage(final int screenX, final int screenY, final float u, final float v)
	{
		final GuiTexture leftPage = new GuiTexture(Dim2D.build().width(this.PAGE_WIDTH).height(this.PAGE_HEIGHT).x(screenX).y(screenY).flush(), LEFT_PAGE);

		final DiscoveryTab.DiscoveryTabType openedTab = this.aePlayer.getModule(PlayerProgressModule.class).getOpenedDiscoveryTabType();

		final GuiText header = new GuiText(Dim2D.build().x(screenX + 63).y(screenY + 13).flush(),
				new Text(new TextComponentTranslation("tab.guidebook.discovery"), 1.0F));

		Pos2D screen = Pos2D.flush(screenX, screenY);

		this.bestiaryTab = new DiscoveryTab(screen.clone().addX(24).addY(26).flush(), DiscoveryTab.DiscoveryTabType.BESTIARY, openedTab, BESTIARY_ICON);
		this.effectTab = new DiscoveryTab(screen.clone().addX(24 + 32).addY(26).flush(), DiscoveryTab.DiscoveryTabType.EFFECTS, openedTab, EFFECTS_ICON);
		this.landmarkTab = new DiscoveryTab(screen.clone().addX(24 + 64).addY(26).flush(), DiscoveryTab.DiscoveryTabType.LANDMARKS, openedTab, LANDMARK_ICON);
		this.characterTab = new DiscoveryTab(screen.clone().addX(24 + 96).addY(26).flush(), DiscoveryTab.DiscoveryTabType.CHARACTERS, openedTab, CHARACTER_ICON);

		this.bestiaryTab.addAdvancedClickEvent(this::onClickTab);
		this.effectTab.addAdvancedClickEvent(this::onClickTab);
		this.landmarkTab.addAdvancedClickEvent(this::onClickTab);
		this.characterTab.addAdvancedClickEvent(this::onClickTab);

		final GuiText text = new GuiText(Dim2D.build().x(screenX + 26).y(screenY + 48).flush(),
				new Text(new TextComponentTranslation("tab.guidebook.discovery" + this.getTypeName(openedTab)), 1.0F));

		return Lists.newArrayList(leftPage, header, this.bestiaryTab, this.effectTab, this.landmarkTab, this.characterTab, text);
		//
	}

	@Override
	protected List<IGuiElement> createRightPage(final int screenX, final int screenY, final float u, final float v)
	{
		final GuiTexture rightPage = new GuiTexture(Dim2D.build().width(this.PAGE_WIDTH).height(this.PAGE_HEIGHT).x(screenX).y(screenY).flush(),
				RIGHT_PAGE_GENERAL);

		return Lists.newArrayList(rightPage);
	}

	public String getTypeName(DiscoveryTab.DiscoveryTabType type)
	{
		switch (type)
		{
			case BESTIARY:
				return ".bestiary";
			case EFFECTS:
				return ".effects";
			case LANDMARKS:
				return ".landmarks";
			case CHARACTERS:
				return ".characters";
		}

		return "";
	}
}
