package com.gildedgames.aether.client.gui.container.guidebook.discovery;

import com.gildedgames.aether.api.cache.IEntityStats;
import com.gildedgames.aether.api.travellers_guidebook.ITGManager;
import com.gildedgames.aether.client.gui.container.guidebook.discovery.stats.GuiStat;
import com.gildedgames.aether.client.gui.misc.GuiScrollableGuidebook;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.travellers_guidebook.entries.TGEntryBestiaryPage;
import com.gildedgames.orbis.lib.client.gui.data.Text;
import com.gildedgames.orbis.lib.client.gui.util.GuiText;
import com.gildedgames.orbis.lib.client.gui.util.GuiTextBox;
import com.gildedgames.orbis.lib.client.gui.util.GuiTexture;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.*;
import com.gildedgames.orbis.lib.client.rect.Dim2D;
import com.gildedgames.orbis.lib.client.rect.Pos2D;
import com.gildedgames.orbis.lib.client.rect.Rect;
import com.gildedgames.orbis.lib.util.InputHelper;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class GuiGuidebookDiscoveryBestiary extends GuiGuidebookDiscovery
{
	private static final ResourceLocation RIGHT_PAGE_MOB = AetherCore.getResource("textures/gui/guidebook/discovery/guidebook_discovery_right_mob.png");

	private static final ResourceLocation HEALTH_ICON = AetherCore.getResource("textures/gui/guidebook/icons/heart.png");

	private static final ResourceLocation SLASH_DEFENSE_ICON = AetherCore.getResource("textures/gui/overlay/slash_damage.png");

	private static final ResourceLocation PIERCE_DEFENSE_ICON = AetherCore.getResource("textures/gui/overlay/pierce_damage.png");

	private static final ResourceLocation IMPACT_DEFENSE_ICON = AetherCore.getResource("textures/gui/overlay/impact_damage.png");

	private List<TGEntryBestiaryPage> bestiaryEntries;

	private List<BestiarySlot> slots;

	private GuiTexture beastFrame;

	private GuiText beastTitle;

	private GuiTextBox beastDescription;

	private GuiScrollableGuidebook statsArea;

	public GuiGuidebookDiscoveryBestiary(final IGuiViewer prevViewer, final PlayerAether aePlayer)
	{
		super(prevViewer, aePlayer);
	}

	@Override
	public void build(final IGuiContext context)
	{
		super.build(context);
	}

	private IGuiElement buildStats(final TGEntryBestiaryPage page)
	{
		final IEntityStats stats = page.getEntityStats();
		final IGuiElement statsElement = new GuiElement(Dim2D.build().width(52).flush(), false);

		final GuiStat healthStat = new GuiStat(
				new GuiTexture(Dim2D.build().width(14).height(14).flush(), HEALTH_ICON),
				new Text(new TextComponentString(String.valueOf(MathHelper.floor(stats.getMaxHealth()))), 1.0F));
		final GuiStat slashStat = new GuiStat(
				new GuiTexture(Dim2D.build().width(14).height(14).flush(), SLASH_DEFENSE_ICON),
				new Text(new TextComponentString(String.valueOf(MathHelper.floor(stats.getSlashDefenseLevel()))), 1.0F));
		final GuiStat pierceStat = new GuiStat(
				new GuiTexture(Dim2D.build().width(14).height(14).flush(), PIERCE_DEFENSE_ICON),
				new Text(new TextComponentString(String.valueOf(MathHelper.floor(stats.getPierceDefenseLevel()))), 1.0F));
		final GuiStat impactStat = new GuiStat(
				new GuiTexture(Dim2D.build().width(14).height(14).flush(), IMPACT_DEFENSE_ICON),
				new Text(new TextComponentString(String.valueOf(MathHelper.floor(stats.getImpactDefenseLevel()))), 1.0F));

		statsElement.build(this);

		GuiLibHelper.alignVertically(this, Pos2D.flush(5, 5), 5, healthStat, slashStat, pierceStat, impactStat);

		statsElement.context().addChildren(healthStat, slashStat, pierceStat, impactStat);

		GuiLibHelper.assembleMinMaxArea(statsElement);

		return statsElement;
	}

	private IGuiElement resetStats(final TGEntryBestiaryPage page)
	{
		final IGuiElement statsElement = new GuiElement(Dim2D.build().width(52).flush(), false);

		statsElement.build(this);

		return statsElement;
	}

	@Override
	protected List<IGuiElement> createLeftPage(final int screenX, final int screenY, final float u, final float v) {
		List<IGuiElement> elements = super.createLeftPage(screenX, screenY, u, v);
		final ITGManager tgManager = AetherCore.PROXY.content().tgManager();

		this.bestiaryEntries = tgManager.getEntriesWithTagAndClass("bestiary", TGEntryBestiaryPage.class);
		this.slots = Lists.newArrayList();

		for (int i = 0; i < this.bestiaryEntries.size(); i++) {
			final TGEntryBestiaryPage page = this.bestiaryEntries.get(i);

			final int x = screenX + 29 + ((i % 6) * 18);
			final int y = screenY + 58 + ((i / 6) * 18);

			final BestiarySlot slot = new BestiarySlot(this.aePlayer, Pos2D.flush(x, y), page);

			slot.addClickEvent(() -> {
				final boolean isUnderstood = page.isUnderstood(this.aePlayer);
				final boolean completeOverview = page.hasUnlockedCompleteOverview(this.aePlayer);

				this.beastFrame.setResourceLocation(page.isUnlocked(this.aePlayer) ? page.getDiscoveredTexture() : page.getSilhouetteTexture());
				this.beastFrame.state().setVisible(true);

				this.beastTitle.setText(new Text(new TextComponentTranslation(page.getEntityName()), 1.0F));

				if (!isUnderstood) {
					// Replace beast name with ? characters
					this.beastTitle.setTextMutator((text) -> text.replaceAll("[^\\s]", "?"));
				}

				this.beastDescription
						.setText(new Text(new TextComponentTranslation(completeOverview ? page.getUnlocalizedDescription() : "?"),
								0.65F));

				this.beastDescription.tryRebuild();

				if (isUnderstood) {
					final IGuiElement statsContent = this.buildStats(page);

					statsContent.dim().mod().pos(this.statsArea.dim().min()).flush();

					this.statsArea.setDecorated(statsContent);
				}
				else
				{
					final IGuiElement statsContent = this.resetStats(page);

					statsContent.dim().mod().pos(this.statsArea.dim().min()).flush();

					this.statsArea.setDecorated(statsContent);
				}
			});

			this.slots.add(slot);
			elements.add(slot);
		}

		return elements;
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

		// Empty stats scroll until click on entry
		this.statsArea = new GuiScrollableGuidebook(new GuiElement(Dim2D.build().x(screenX + 25).y(screenY + 114).flush(), false),
				Dim2D.build().width(52 + 8).height(56).flush(), true);

		final Rect beastDescriptionScrollArea = Dim2D.build().width(52 + 9).height(73).flush();

		final GuiTexture rightPage = new GuiTexture(Dim2D.build().width(this.PAGE_WIDTH).height(this.PAGE_HEIGHT).x(screenX).y(screenY).flush(),
				RIGHT_PAGE_MOB);

		final GuiText stats = new GuiText(Dim2D.build().x(screenX + 40).y(screenY + 102).flush(),
				new Text(new TextComponentTranslation("gui.guidebook.discovery.stats"), 1.0F));
		final GuiText moves = new GuiText(Dim2D.build().x(screenX + 105).y(screenY + 102).flush(),
				new Text(new TextComponentTranslation("gui.guidebook.discovery.moves"), 1.0F));

		return Lists.newArrayList(rightPage,
				this.beastFrame,
				this.beastTitle,
				new GuiScrollableGuidebook(this.beastDescription, beastDescriptionScrollArea, true),
				stats,
				moves,
				this.statsArea);
	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);

		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution scaledresolution = new ScaledResolution(mc);

		for (BestiarySlot slot : this.slots)
		{
			String beastName = slot.getPage().getEntityName();

			if (!slot.getPage().isUnderstood(this.aePlayer))
			{
				beastName = beastName.replaceAll("[^\\s]", "?");
			}

			if (InputHelper.isHovered(slot))
			{
				this.drawHoveringText(beastName,
						Mouse.getX() * scaledresolution.getScaledWidth() / mc.displayWidth,
						scaledresolution.getScaledHeight() - (Mouse.getY() - 42) * scaledresolution.getScaledHeight() / mc.displayHeight
								- 1, mc.fontRenderer);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	protected void drawHoveringText(String text, int x, int y, FontRenderer font)
	{
		GL11.glDisable(GL11.GL_DEPTH_TEST);

		int k = font.getStringWidth(text);

		int x1 = x + 12;
		int y1 = y - 12;
		int z1 = 8;

		this.zLevel = 300.0F;
		int color1 = -267386864;
		this.drawGradientRect(x1 - 3, y1 - 4, x1 + k + 3, y1 - 3, color1, color1);
		this.drawGradientRect(x1 - 3, y1 + z1 + 3, x1 + k + 3, y1 + z1 + 4, color1, color1);
		this.drawGradientRect(x1 - 3, y1 - 3, x1 + k + 3, y1 + z1 + 3, color1, color1);
		this.drawGradientRect(x1 - 4, y1 - 3, x1 - 3, y1 + z1 + 3, color1, color1);
		this.drawGradientRect(x1 + k + 3, y1 - 3, x1 + k + 4, y1 + z1 + 3, color1, color1);
		int color2 = 1347420415;
		int color2BG = (color2 & 16711422) >> 1 | color2 & -16777216;
		this.drawGradientRect(x1 - 3, y1 - 3 + 1, x1 - 3 + 1, y1 + z1 + 3 - 1, color2, color2BG);
		this.drawGradientRect(x1 + k + 2, y1 - 3 + 1, x1 + k + 3, y1 + z1 + 3 - 1, color2, color2BG);
		this.drawGradientRect(x1 - 3, y1 - 3, x1 + k + 3, y1 - 3 + 1, color2, color2);
		this.drawGradientRect(x1 - 3, y1 + z1 + 2, x1 + k + 3, y1 + z1 + 3, color2BG, color2BG);

		font.drawString(text, x1, y1, -1);

		this.zLevel = 0.0F;

		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
}
