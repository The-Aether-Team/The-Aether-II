package com.gildedgames.aether.client.gui.tab;

import com.gildedgames.aether.api.registry.tab.ITab;
import com.gildedgames.aether.api.registry.tab.ITabClient;
import com.gildedgames.aether.api.registry.tab.ITabGroup;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.ReflectionAether;
import com.gildedgames.aether.common.containers.tab.util.TabGroupHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class RenderTabGroup extends Gui
{

	private static final ResourceLocation TEXTURE_TAB_ITEMS = new ResourceLocation("textures/gui/container/creative_inventory/tab_items.png");

	private static final ResourceLocation TEXTURE_TABS = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");

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

	/**
	 * Renders the {@link TabGroupHandler} and all of its containing {@link ITab ITab}s
	 */
	public void render(ITabGroup<ITabClient> tabGroup)
	{
		if (tabGroup.getEnabledTabs().size() <= 1)
		{
			return;
		}

		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution scaledresolution = new ScaledResolution(mc);

		int xPosition = 0;
		int yPosition = 0;

		if (!AetherCore.CONFIG.getDisplayTabsOnLeft())
		{
			xPosition = (scaledresolution.getScaledWidth() - 28 * tabGroup.getEnabledTabs().size()) / 2;

			GuiScreen gui = Minecraft.getMinecraft().currentScreen;

			if (gui instanceof GuiContainerCreative)
			{
				int guiLeft = ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, (GuiContainer)gui, ReflectionAether.GUI_LEFT.getMappings());
				int xSize = ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, (GuiContainer)gui, ReflectionAether.X_SIZE.getMappings());

				if (guiLeft == 160 + (gui.width - xSize - 200) / 2)
				{
					xPosition += (200 - (28 * tabGroup.getEnabledTabs().size())) / 2;
				}
			}
		}

		mc.getTextureManager().bindTexture(TEXTURE_TAB_ITEMS);

		GL11.glColor4f(1.f, 1.f, 1.f, 1.f);
		GL11.glDisable(GL11.GL_LIGHTING);

		//this.drawTexturedModalRect(centerX - width / 2, topY, 0, 0, width / 2, 15);
		//this.drawTexturedModalRect(centerX - width / 2, topY + 15, 0, 130, width / 2, 5);
		//this.drawTexturedModalRect(centerX, topY, 194 - width / 2, 0, width / 2, 15);
		//this.drawTexturedModalRect(centerX, topY + 15, 194 - width / 2, 130, width / 2, 5);

		//this.drawCenteredString(mc.fontRendererObj, I18n.translateToLocal(tabGroup.getSelectedTab().getUnlocalizedName()), centerX, topY + 8, 0xFFFFFFFF);

		for (ITabClient tab : tabGroup.getEnabledTabs())
		{
			if (tab != null && tab.isEnabled())
			{
				mc.getTextureManager().bindTexture(TEXTURE_TABS);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

				int u = 28;

				if (tab == tabGroup.getSelectedTab())
				{
					int v = 3 * 32;
					this.drawTexturedModalRect(xPosition, yPosition - 3, u, v, 28, 14);
					this.drawTexturedModalRect(xPosition, yPosition + 10, u, v + 22, 28, 10);
				}
				else
				{
					int v = 2 * 32;
					this.drawTexturedModalRect(xPosition, yPosition, u, v, 28, 11);
					this.drawTexturedModalRect(xPosition, yPosition + 10, u, v + 20, 28, 12);
				}

				if (tab.getIcon() != null)
				{
					mc.renderEngine.bindTexture(tab.getIcon());

					Gui.drawModalRectWithCustomSizedTexture(xPosition + 6, yPosition - 1, 0, 0, 16, 16, 16, 16);
				}

				xPosition += 27;
			}
		}

		ITab hoveredTab = this.getHoveredTab(tabGroup);

		if (hoveredTab != null)
		{
			this.drawHoveringText(I18n.translateToLocal(hoveredTab.getUnlocalizedName()), Mouse.getX() * scaledresolution.getScaledWidth() / mc.displayWidth, scaledresolution.getScaledHeight() - (Mouse.getY() - 42) * scaledresolution.getScaledHeight() / mc.displayHeight - 1, mc.fontRendererObj);
		}
	}

	/**
	 * @return The current {@link ITab} that is hovered over by the player's mouse cursor
	 */
	public ITabClient getHoveredTab(ITabGroup<ITabClient> tabGroup)
	{
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution scaledresolution = new ScaledResolution(mc);

		int x = Mouse.getX() * scaledresolution.getScaledWidth() / mc.displayWidth;
		int y = scaledresolution.getScaledHeight() - Mouse.getY() * scaledresolution.getScaledHeight() / mc.displayHeight - 1;

		if (y >= 0 && y <= 19)
		{
			int xPosition = 0;

			if (!AetherCore.CONFIG.getDisplayTabsOnLeft())
			{
				xPosition = (scaledresolution.getScaledWidth() - 28 * tabGroup.getEnabledTabs().size()) / 2;
			}

			x -= xPosition;

			if (x > 0 && x < 28 * tabGroup.getEnabledTabs().size())
			{
				return tabGroup.getEnabledTabs().get(x / 28);
			}
		}

		return null;
	}

}
