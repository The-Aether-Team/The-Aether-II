package com.gildedgames.aether.client.gui.container.guidebook.discovery;

import com.gildedgames.aether.api.travellers_guidebook.ITGManager;
import com.gildedgames.aether.client.gui.container.IExtendedContainer;
import com.gildedgames.aether.client.gui.misc.GuiScrollableGuidebook;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.travellers_guidebook.entries.TGEntryEffectsPage;
import com.gildedgames.orbis.lib.client.gui.data.Text;
import com.gildedgames.orbis.lib.client.gui.util.GuiText;
import com.gildedgames.orbis.lib.client.gui.util.GuiTextBox;
import com.gildedgames.orbis.lib.client.gui.util.GuiTexture;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.IGuiElement;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.IGuiViewer;
import com.gildedgames.orbis.lib.client.rect.Dim2D;
import com.gildedgames.orbis.lib.client.rect.Pos2D;
import com.gildedgames.orbis.lib.client.rect.Rect;
import com.gildedgames.orbis.lib.util.InputHelper;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiGuidebookDiscoveryEffects extends GuiGuidebookDiscovery
{
	private static final ResourceLocation RIGHT_PAGE_EFFECTS = AetherCore.getResource("textures/gui/guidebook/discovery/guidebook_discovery_right_effect.png");

	private List<TGEntryEffectsPage> effectsEntries;

	private List<EffectsSlot> slots;

	private GuiTexture effectFrame;

	private GuiText effectTitle;

	private GuiTextBox effectDescription;

	private List<CurativeItemSlot> curativesEntries;

	public GuiGuidebookDiscoveryEffects(final IGuiViewer prevViewer, final PlayerAether aePlayer)
	{
		super(prevViewer, aePlayer);
	}

	@Override
	public void initGui()
	{
		super.initGui();
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException
	{
		super.actionPerformed(button);
	}

	@Override
	protected List<IGuiElement> createLeftPage(final int screenX, final int screenY, final float u, final float v) {
		List<IGuiElement> elements = super.createLeftPage(screenX, screenY, u, v);
		final ITGManager tgManager = AetherCore.PROXY.content().tgManager();

		this.effectsEntries = tgManager.getEntriesWithTagAndClass("effects", TGEntryEffectsPage.class);
		this.slots = Lists.newArrayList();

		for (int i = 0; i < this.effectsEntries.size(); i++) {
			final TGEntryEffectsPage page = this.effectsEntries.get(i);

			final int x = screenX + 29 + ((i % 6) * 18);
			final int y = screenY + 58 + ((i / 6) * 18);

			final EffectsSlot slot = new EffectsSlot(this.aePlayer, Pos2D.flush(x, y), page);

			slot.addClickEvent(() -> {
				this.effectFrame.setResourceLocation(page.getDisplayTexture());
				this.effectFrame.state().setVisible(true);

				this.effectTitle.setText(new Text(new TextComponentTranslation(page.getLocalizedEffectName()), 1.0F));

				this.effectDescription
						.setText(new Text(new TextComponentTranslation(page.getUnlocalizedDescription()),
								0.65F));

				this.effectDescription.tryRebuild();

				for (int j = 0; j < 6; j++)
				{
					CurativeItemSlot curativeRender = this.curativesEntries.get(j);

					if (!page.getCurativeItems().isEmpty() && j < page.getCurativeItems().size())
					{
						String curativeName = page.getCurativeItems().get(j);
						Item curativeItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(curativeName.replaceAll("\"", "")));

						if (curativeItem != null)
						{
							ItemStack curativeStack = new ItemStack(curativeItem);

							curativeRender.setItemStack(curativeStack);
						}
					}
					else
					{
						curativeRender.setItemStack(ItemStack.EMPTY);
					}
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
		List<IGuiElement> elements = super.createRightPage(screenX, screenY, u, v);

		this.effectFrame = new GuiTexture(Dim2D.build().x(screenX + 72).y(screenY + 30).width(32).height(32).flush(), null);
		this.effectFrame.state().setVisible(false);

		this.effectTitle = new GuiText(Dim2D.build().x(screenX + 88).y(screenY + 10).flush(), new Text(new TextComponentString(""), 1.0F));
		this.effectTitle.dim().mod().centerX(true).flush();

		this.effectDescription = new GuiTextBox(Dim2D.build().x(screenX + 24).y(screenY + 74).width(120).flush(), false,
				new Text(new TextComponentString(""),
						0.65F));

		// Empty stats scroll until click on entry
		final Rect effectDescriptionScrollArea = Dim2D.build().width(120 + 9).height(61).flush();

		final GuiTexture rightPage = new GuiTexture(Dim2D.build().width(this.PAGE_WIDTH).height(this.PAGE_HEIGHT).x(screenX).y(screenY).flush(),
				RIGHT_PAGE_EFFECTS);

		final GuiText curatives = new GuiText(Dim2D.build().x(screenX + 63).y(screenY + 138).flush(),
				new Text(new TextComponentTranslation("gui.guidebook.discovery.curatives"), 1.0F));

		elements.addAll(Lists.newArrayList(rightPage,
				this.effectFrame,
				this.effectTitle,
				new GuiScrollableGuidebook(this.effectDescription, effectDescriptionScrollArea, true),
				curatives));

		this.curativesEntries = new ArrayList<>();

		// Creates "ghost" entries that will be filled when an effect is clicked.
		for (int i = 0; i < 6; i++)
		{
			final int x = 30 + ((i % 6) * 18);
			final int y = 149 + ((i / 6) * 18);

			CurativeItemSlot curativeElement = new CurativeItemSlot(Dim2D.build().x(screenX + x).y(screenY + y).width(18).height(18).flush());

			this.curativesEntries.add(curativeElement);
			elements.add(curativeElement);
		}

		return elements;
	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);

		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution scaledresolution = new ScaledResolution(mc);

		for (EffectsSlot slot : this.slots)
		{
			String effectName = slot.getPage().getLocalizedEffectName();

			if (InputHelper.isHovered(slot))
			{
				this.drawHoveringText(effectName,
						Mouse.getX() * scaledresolution.getScaledWidth() / mc.displayWidth,
						scaledresolution.getScaledHeight() - (Mouse.getY() - 42) * scaledresolution.getScaledHeight() / mc.displayHeight
								- 1, mc.fontRenderer);
			}
		}

		for (CurativeItemSlot slot : this.curativesEntries)
		{
			if (InputHelper.isHovered(slot) && !slot.getItemStack().isEmpty())
			{
				this.setHoveredDescription(slot.getItemStack().getTooltip(Minecraft.getMinecraft().player, Minecraft.getMinecraft().gameSettings.advancedItemTooltips ?
						ITooltipFlag.TooltipFlags.ADVANCED :
						ITooltipFlag.TooltipFlags.NORMAL));
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
