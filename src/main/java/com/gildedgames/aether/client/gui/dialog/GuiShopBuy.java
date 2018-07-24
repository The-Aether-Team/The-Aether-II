package com.gildedgames.aether.client.gui.dialog;

import com.gildedgames.aether.api.shop.IShopBuy;
import com.gildedgames.aether.api.shop.IShopInstance;
import com.gildedgames.aether.client.gui.IExtendedGui;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.orbis_api.client.gui.util.GuiAbstractButton;
import com.gildedgames.orbis_api.client.gui.util.GuiFrame;
import com.gildedgames.orbis_api.client.gui.util.GuiTexture;
import com.gildedgames.orbis_api.client.rect.Dim2D;
import com.gildedgames.orbis_api.client.rect.Rect;
import com.gildedgames.orbis_api.util.InputHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.ResourceLocation;

public class GuiShopBuy extends GuiFrame
{
	private static final ResourceLocation BUTTON = AetherCore.getResource("textures/gui/shop/button.png");

	private static final ResourceLocation BUTTON_PRESSED = AetherCore.getResource("textures/gui/shop/button_pressed.png");

	private IShopInstance shopInstance;

	private int buyIndex;

	public GuiShopBuy(Rect rect, int buyIndex, IShopInstance shopInstance)
	{
		super(null, rect);

		this.buyIndex = buyIndex;
		this.shopInstance = shopInstance;
	}

	public IShopBuy getShopBuy()
	{
		return this.shopInstance.getStock().get(this.buyIndex);
	}

	public int getBuyIndex()
	{
		return this.buyIndex;
	}

	@Override
	public void init()
	{
		GuiAbstractButton button = new GuiAbstractButton(Dim2D.build().width(18).height(18).flush(),
				new GuiTexture(Dim2D.build().width(18).height(18).flush(), BUTTON),
				new GuiTexture(Dim2D.build().width(18).height(18).flush(), BUTTON_PRESSED),
				new GuiTexture(Dim2D.build().width(18).height(18).flush(), BUTTON));

		this.addChildren(button);
	}

	@Override
	public void postDrawAllChildren()
	{
		GlStateManager.pushMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableRescaleNormal();
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		GlStateManager.pushMatrix();

		//TODO: WHY IS THE LIGHTING REVERSED, EVEN WITH STANDARD GUI ITEM LIGHTING. WTF????
		RenderHelper.enableStandardItemLighting();

		GlStateManager.popMatrix();
		GlStateManager.disableLighting();

		FontRenderer font = this.getShopBuy().getItemStack().getItem().getFontRenderer(this.getShopBuy().getItemStack());

		if (font == null)
		{
			font = this.fontRenderer;
		}

		RenderItem renderitem = this.mc.getRenderItem();
		renderitem.zLevel = 200.0F;

		renderitem.renderItemAndEffectIntoGUI(this.mc.player, this.getShopBuy().getItemStack(), (int) this.dim().x() + 1, (int) this.dim().y() + 1);

		RenderHelper.disableStandardItemLighting();

		renderitem.zLevel = 0.0F;

		boolean hasEnough = PlayerAether.getPlayer(this.mc.player).getCurrencyModule().getCurrencyValue() >= this.getShopBuy().getPrice();

		int xOffset = (Math.max(String.valueOf(this.getShopBuy().getStock()).length() - 1, 0)) * -6;

		this.drawString(font, String.valueOf(this.getShopBuy().getStock()),
				(int) this.dim().x() + 12 + xOffset, (int) this.dim().y() + (int) this.dim().height() - 8, !hasEnough ? 0xba4a4a : 0xFFFFFF);

		if (InputHelper.isHovered(this))
		{
			GuiScreen gui = Minecraft.getMinecraft().currentScreen;

			if (gui instanceof IExtendedGui)
			{
				IExtendedGui extendedGui = (IExtendedGui) gui;
				extendedGui.setHoveredDescription(this.getShopBuy().getItemStack().getTooltip(Minecraft.getMinecraft().player,
						Minecraft.getMinecraft().gameSettings.advancedItemTooltips ?
								ITooltipFlag.TooltipFlags.ADVANCED :
								ITooltipFlag.TooltipFlags.NORMAL));
			}
		}

		GlStateManager.popMatrix();
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
		RenderHelper.enableStandardItemLighting();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
