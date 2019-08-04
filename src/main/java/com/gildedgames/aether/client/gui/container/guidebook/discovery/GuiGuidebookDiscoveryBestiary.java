package com.gildedgames.aether.client.gui.container.guidebook.discovery;

import com.gildedgames.aether.api.travellers_guidebook.ITGManager;
import com.gildedgames.aether.client.gui.container.guidebook.discovery.stats.GuiStat;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.travellers_guidebook.entries.TGEntryBestiaryPage;
import com.gildedgames.orbis.lib.client.gui.data.Text;
import com.gildedgames.orbis.lib.client.gui.util.GuiText;
import com.gildedgames.orbis.lib.client.gui.util.GuiTextBox;
import com.gildedgames.orbis.lib.client.gui.util.GuiTexture;
import com.gildedgames.orbis.lib.client.gui.util.decorators.GuiScrollable;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.IGuiContext;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.IGuiElement;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.IGuiViewer;
import com.gildedgames.orbis.lib.client.rect.Dim2D;
import com.gildedgames.orbis.lib.client.rect.Pos2D;
import com.gildedgames.orbis.lib.client.rect.Rect;
import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.List;

public class GuiGuidebookDiscoveryBestiary extends GuiGuidebookDiscovery
{
	private static final ResourceLocation RIGHT_PAGE_MOB = AetherCore.getResource("textures/gui/guidebook/discovery/guidebook_discovery_right_mob.png");

	private static final ResourceLocation HEALTH_ICON = AetherCore.getResource("textures/gui/guidebook/icons/heart.png");

	private static final ResourceLocation SLASH_DEFENSE_ICON = AetherCore.getResource("textures/gui/overlay/slash_defense.png");

	private static final ResourceLocation PIERCE_DEFENSE_ICON = AetherCore.getResource("textures/gui/overlay/pierce_defense.png");

	private static final ResourceLocation IMPACT_DEFENSE_ICON = AetherCore.getResource("textures/gui/overlay/impact_defense.png");

	private List<TGEntryBestiaryPage> bestiaryEntries;

	private List<BestiarySlot> slots;

	private GuiTexture beastFrame;

	private GuiText beastTitle;

	private GuiTextBox beastDescription;

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
				final boolean completeOverview = page.hasUnlockedCompleteOverview(this.aePlayer);

				this.beastFrame.setResourceLocation(page.isUnlocked(this.aePlayer) ? page.getDiscoveredTexture() : page.getSilhouetteTexture());
				this.beastFrame.state().setVisible(true);

				this.beastTitle.setText(new Text(new TextComponentTranslation(page.getEntityName()), 1.0F));

				if (!isUnderstood)
				{
					// Replace beast name with ? characters
					this.beastTitle.setTextMutator((text) -> text.replaceAll("[^\\s]", "?"));
				}

				this.beastDescription
						.setText(new Text(new TextComponentTranslation(completeOverview ? page.getUnlocalizedDescription() : "?"),
								0.65F));

				this.beastDescription.tryRebuild();
			});

			this.slots.add(slot);
			context.addChildren(slot);
		}
	}

	private IGuiElement buildStats(final TGEntryBestiaryPage page)
	{
		return null;
	}

	@Override
	protected List<IGuiElement> createRightPage(final int screenX, final int screenY, final float u, final float v)
	{
		this.beastFrame = new GuiTexture(Dim2D.build().x(screenX + 25).y(screenY + 25).width(58).height(71).flush(), null);
		this.beastFrame.state().setVisible(false);

		this.beastTitle = new GuiText(Dim2D.build().x(screenX + 88).y(screenY + 10).flush(), new Text(new TextComponentString(""), 1.0F));
		this.beastTitle.dim().mod().centerX(true).flush();

		this.beastDescription = new GuiTextBox(Dim2D.build().x(screenX + 92).y(screenY + 24).width(52).flush(), false,
				new Text(new TextComponentString(""),
						0.65F));

		final Rect beastDescriptionScrollArea = Dim2D.build().width(52 + 15).height(73).flush();

		final GuiTexture rightPage = new GuiTexture(Dim2D.build().width(this.PAGE_WIDTH).height(this.PAGE_HEIGHT).x(screenX).y(screenY).flush(),
				RIGHT_PAGE_MOB);

		final GuiText stats = new GuiText(Dim2D.build().x(screenX + 40).y(screenY + 102).flush(),
				new Text(new TextComponentTranslation("gui.guidebook.discovery.stats"), 1.0F));
		final GuiText moves = new GuiText(Dim2D.build().x(screenX + 105).y(screenY + 102).flush(),
				new Text(new TextComponentTranslation("gui.guidebook.discovery.moves"), 1.0F));

		return Lists.newArrayList(rightPage,
				this.beastFrame,
				this.beastTitle,
				new GuiScrollable(this.beastDescription, beastDescriptionScrollArea, true),
				stats,
				moves);
	}
}
