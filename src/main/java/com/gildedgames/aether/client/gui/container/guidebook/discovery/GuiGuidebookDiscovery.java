package com.gildedgames.aether.client.gui.container.guidebook.discovery;

import com.gildedgames.aether.client.gui.container.guidebook.AbstractGuidebookPage;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerProgressModule;
import com.gildedgames.aether.common.containers.guidebook.EmptyContainer;
import com.gildedgames.orbis.lib.client.gui.util.GuiTexture;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.IGuiContext;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.IGuiElement;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.IGuiViewer;
import com.gildedgames.orbis.lib.client.rect.Dim2D;
import com.gildedgames.orbis.lib.client.rect.Pos2D;
import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class GuiGuidebookDiscovery extends AbstractGuidebookPage
{
	private static final ResourceLocation LEFT_PAGE = AetherCore.getResource("textures/gui/guidebook/discovery/guidebook_discovery_left.png");

	private static final ResourceLocation RIGHT_PAGE_GENERAL = AetherCore.getResource("textures/gui/guidebook/discovery/guidebook_discovery_right_general.png");

	private DiscoveryTab bestiaryTab;

	private DiscoveryTab structureTab;

	private DiscoveryTab biomeTab;

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
		this.structureTab.setSelected(false);
		this.characterTab.setSelected(false);
		this.biomeTab.setSelected(false);

		this.aePlayer.getModule(PlayerProgressModule.class).setOpenedDiscoveryTabType(tab.getType());

		tab.setSelected(true);

		switch (tab.getType())
		{
			case BESTIARY:
			{
				this.mc.displayGuiScreen(new GuiGuidebookDiscoveryBestiary(this, this.aePlayer));
				break;
			}
			case STRUCTURES:
			{
				this.mc.displayGuiScreen(new GuiGuidebookDiscoveryStructures(this, this.aePlayer));
				break;
			}
			case CHARACTERS:
			{
				this.mc.displayGuiScreen(new GuiGuidebookDiscoveryCharacters(this, this.aePlayer));
				break;
			}
			case BIOMES:
			{
				this.mc.displayGuiScreen(new GuiGuidebookDiscoveryBiomes(this, this.aePlayer));
				break;
			}
		}
	}

	@Override
	protected List<IGuiElement> createLeftPage(final int screenX, final int screenY, final float u, final float v)
	{
		final GuiTexture leftPage = new GuiTexture(Dim2D.build().width(this.PAGE_WIDTH).height(this.PAGE_HEIGHT).x(screenX).y(screenY).flush(), LEFT_PAGE);

		final DiscoveryTab.DiscoveryTabType openedTab = this.aePlayer.getModule(PlayerProgressModule.class).getOpenedDiscoveryTabType();

		Pos2D screen = Pos2D.flush(screenX, screenY);

		this.bestiaryTab = new DiscoveryTab(screen.clone().addX(24).addY(26).flush(), DiscoveryTab.DiscoveryTabType.BESTIARY, openedTab);
		this.structureTab = new DiscoveryTab(screen.clone().addX(24 + 32).addY(26).flush(), DiscoveryTab.DiscoveryTabType.STRUCTURES, openedTab);
		this.characterTab = new DiscoveryTab(screen.clone().addX(24 + 64).addY(26).flush(), DiscoveryTab.DiscoveryTabType.CHARACTERS, openedTab);
		this.biomeTab = new DiscoveryTab(screen.clone().addX(24 + 96).addY(26).flush(), DiscoveryTab.DiscoveryTabType.BIOMES, openedTab);

		this.bestiaryTab.addAdvancedClickEvent(this::onClickTab);
		this.structureTab.addAdvancedClickEvent(this::onClickTab);
		this.characterTab.addAdvancedClickEvent(this::onClickTab);
		this.biomeTab.addAdvancedClickEvent(this::onClickTab);

		return Lists.newArrayList(leftPage, this.bestiaryTab, this.structureTab, this.characterTab, this.biomeTab);
	}

	@Override
	protected List<IGuiElement> createRightPage(final int screenX, final int screenY, final float u, final float v)
	{
		final GuiTexture rightPage = new GuiTexture(Dim2D.build().width(this.PAGE_WIDTH).height(this.PAGE_HEIGHT).x(screenX).y(screenY).flush(),
				RIGHT_PAGE_GENERAL);

		return Lists.newArrayList(rightPage);
	}
}
