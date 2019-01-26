package com.gildedgames.aether.client.gui.dialog;

import com.gildedgames.aether.api.shop.IShopBuy;
import com.gildedgames.aether.api.shop.IShopInstance;
import com.gildedgames.aether.api.shop.ShopUtil;
import com.gildedgames.aether.client.gui.IExtendedGui;
import com.gildedgames.aether.client.gui.util.ToolTipCurrencyHelper;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.orbis_api.client.gui.util.GuiAbstractButton;
import com.gildedgames.orbis_api.client.gui.util.GuiTexture;
import com.gildedgames.orbis_api.client.gui.util.gui_library.GuiElement;
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
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiShopBuy extends GuiElement
{
	private static final ResourceLocation BUTTON = AetherCore.getResource("textures/gui/shop/button.png");

	private static final ResourceLocation BUTTON_PRESSED = AetherCore.getResource("textures/gui/shop/button_pressed.png");

	private static final ToolTipCurrencyHelper toolTipHelper = new ToolTipCurrencyHelper();

	private final IShopInstance shopInstance;

	private final int buyIndex;

	public GuiShopBuy(Rect rect, int buyIndex, IShopInstance shopInstance)
	{
		super(rect, true);

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
	public void build()
	{
		GuiAbstractButton button = new GuiAbstractButton(Dim2D.build().width(18).height(18).flush(),
				new GuiTexture(Dim2D.build().width(18).height(18).flush(), BUTTON),
				new GuiTexture(Dim2D.build().width(18).height(18).flush(), BUTTON_PRESSED),
				new GuiTexture(Dim2D.build().width(18).height(18).flush(), BUTTON));

		GuiElement itemstack = new GuiElement(Dim2D.flush(), true)
		{
			@Override
			public void onDraw(GuiElement element)
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

				FontRenderer font = GuiShopBuy.this.getShopBuy().getItemStack().getItem().getFontRenderer(GuiShopBuy.this.getShopBuy().getItemStack());

				if (font == null)
				{
					font = this.viewer().fontRenderer();
				}

				RenderItem renderitem = this.viewer().mc().getRenderItem();
				renderitem.zLevel = 200.0F;

				renderitem
						.renderItemAndEffectIntoGUI(this.viewer().mc().player, GuiShopBuy.this.getShopBuy().getItemStack(), (int) GuiShopBuy.this.dim().x() + 1,
								(int) GuiShopBuy.this.dim().y() + 1);

				RenderHelper.disableStandardItemLighting();

				renderitem.zLevel = 0.0F;

				boolean hasEnough =
						GuiShopBuy.this.shopInstance.getCurrencyType().getValue(PlayerAether.getPlayer(Minecraft.getMinecraft().player)) >= ShopUtil
								.getFilteredPrice(GuiShopBuy.this.shopInstance, GuiShopBuy.this
										.getShopBuy());

				int xOffset = (Math.max(String.valueOf(GuiShopBuy.this.getShopBuy().getStock()).length() - 1, 0)) * -6;

				this.viewer().getActualScreen().drawString(font, String.valueOf(GuiShopBuy.this.getShopBuy().getStock()),
						(int) GuiShopBuy.this.dim().x() + 12 + xOffset, (int) GuiShopBuy.this.dim().y() + (int) GuiShopBuy.this.dim().height() - 8,
						!hasEnough ? 0xba4a4a : 0xFFFFFF);

				if (InputHelper.isHovered(GuiShopBuy.this))
				{
					GuiScreen gui = Minecraft.getMinecraft().currentScreen;

					if (gui instanceof IExtendedGui)
					{
						ItemStack stack = GuiShopBuy.this.getShopBuy().getItemStack();

						IExtendedGui extendedGui = (IExtendedGui) gui;
						extendedGui.setHoveredDescription(stack, stack.getTooltip(Minecraft.getMinecraft().player,
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
		};

		this.context().addChildren(button, itemstack);

		itemstack.state().setZOrder(1);
	}
}
