package com.gildedgames.aether.client.gui.container.guidebook.discovery;

import com.gildedgames.aether.api.travellers_guidebook.ITGManager;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.travellers_guidebook.entries.TGEntryBestiaryPage;
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
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.List;

public class GuiGuidebookDiscoveryBestiary extends GuiGuidebookDiscovery
{
	private static final ResourceLocation RIGHT_PAGE_MOB = AetherCore.getResource("textures/gui/guidebook/discovery/guidebook_discovery_right_mob.png");

	private List<TGEntryBestiaryPage> bestiaryEntries;

	private List<BestiarySlot> slots;

	private GuiTexture beastFrame;

	private GuiText beastTitle;

	public GuiGuidebookDiscoveryBestiary(final IGuiViewer prevViewer, final PlayerAether aePlayer)
	{
		super(prevViewer, aePlayer);
	}

	@Override
	public void build(final IGuiContext context)
	{
		super.build(context);

		final ITGManager tgManager = AetherCore.PROXY.content().tgManager();

		this.bestiaryEntries = tgManager.getEntriesWithTagAndClass("bestiary", TGEntryBestiaryPage.class);
		this.slots = Lists.newArrayList();

		for (int i = 0; i < this.bestiaryEntries.size(); i++)
		{
			final TGEntryBestiaryPage page = this.bestiaryEntries.get(i);

			final int x = 95 + ((i % 6) * 18);
			final int y = 94 + ((i / 6) * 18);

			final BestiarySlot slot = new BestiarySlot(this.aePlayer, Pos2D.flush(x, y), page);

			slot.addClickEvent(() -> {
				final boolean isUnderstood = page.isUnderstood(this.aePlayer);

				this.beastFrame.setResourceLocation(isUnderstood ? page.getDiscoveredTexture() : page.getSilhouetteTexture());
				this.beastFrame.state().setVisible(true);

				this.beastTitle.setText(new Text(new TextComponentTranslation(isUnderstood ? page.getEntityName() : "???"), 1.0F));
			});

			this.slots.add(slot);
			context.addChildren(slot);
		}
	}

	@Override
	protected List<IGuiElement> createRightPage(final int screenX, final int screenY, final float u, final float v)
	{
		this.beastFrame = new GuiTexture(Dim2D.build().x(screenX + 25).y(screenY + 27).width(58).height(71).flush(), null);
		this.beastFrame.state().setVisible(false);

		this.beastTitle = new GuiText(Dim2D.build().x(screenX + 88).y(screenY + 10).flush(), new Text(new TextComponentString(""), 1.0F));
		this.beastTitle.dim().mod().centerX(true).flush();

		final GuiTexture rightPage = new GuiTexture(Dim2D.build().width(this.PAGE_WIDTH).height(this.PAGE_HEIGHT).x(screenX).y(screenY).flush(),
				RIGHT_PAGE_MOB);

		return Lists.newArrayList(rightPage, this.beastFrame, this.beastTitle);
	}
}
