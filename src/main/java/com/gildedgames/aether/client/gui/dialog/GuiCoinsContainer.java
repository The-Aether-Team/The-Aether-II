package com.gildedgames.aether.client.gui.dialog;

import com.gildedgames.aether.api.shop.IGuiCurrencyValue;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.lib.client.gui.util.GuiLine;
import com.gildedgames.orbis.lib.client.gui.util.GuiTextLabel;
import com.gildedgames.orbis.lib.client.gui.util.GuiTexture;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.GuiElement;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.IGuiElement;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.IGuiEvent;
import com.gildedgames.orbis.lib.client.rect.Dim2D;
import com.gildedgames.orbis.lib.client.rect.Pos2D;
import com.gildedgames.orbis.lib.client.rect.Rect;
import com.gildedgames.orbis.lib.util.InputHelper;
import com.gildedgames.orbis.lib.util.MathUtil;
import net.minecraft.util.ResourceLocation;

public class GuiCoinsContainer extends GuiElement implements IGuiCurrencyValue
{
	private static final ResourceLocation PRICE_UP = AetherCore.getResource("textures/gui/shop/price_up.png");

	private static final ResourceLocation PRICE_DOWN = AetherCore.getResource("textures/gui/shop/price_down.png");

	private GuiCoins coinsCount, originalPrice;

	private GuiTextLabel origPriceBg;

	private GuiTexture discountIndicator;

	private final boolean shouldDisplayAlways;

	private final boolean displaysDiscount;

	public GuiCoinsContainer(Rect rect, boolean shouldDisplayAlways, boolean displaysDiscount)
	{
		super(rect, true);

		this.dim().mod().width(51).height(19).flush();

		this.shouldDisplayAlways = shouldDisplayAlways;
		this.displaysDiscount = displaysDiscount;
	}

	@Override
	public void setCurrencyValue(double value)
	{
		this.coinsCount.setCurrencyValue(value);

		this.coinsCount.state().setVisible(true);

		Pos2D center = Pos2D.flush(this.dim().width() / 2, this.dim().height() / 2);

		this.coinsCount.dim().mod().pos(center).center(true).flush();
	}

	@Override
	public void setNonFilteredCurrencyValue(double value)
	{
		this.originalPrice.setCurrencyValue(value);

		this.originalPrice.state().setVisible(false);
		this.originalPrice.state().setEnabled(false);

		if (MathUtil.epsilonEquals(this.originalPrice.getCurrencyValue(), this.coinsCount.getCurrencyValue()) || value <= 0.0D || !this.displaysDiscount)
		{
			this.discountIndicator.state().setVisible(false);
		}
		else if (this.originalPrice.getCurrencyValue() > this.coinsCount.getCurrencyValue())
		{
			this.discountIndicator.setResourceLocation(PRICE_DOWN);
			this.discountIndicator.state().setVisible(true);
		}
		else
		{
			this.discountIndicator.setResourceLocation(PRICE_UP);
			this.discountIndicator.state().setVisible(true);
		}
	}

	@Override
	public void build()
	{
		this.discountIndicator = new GuiTexture(Dim2D.build().width(7).height(9).x(-2).y(-3).flush(), PRICE_DOWN);

		this.discountIndicator.state().setVisible(false);

		this.coinsCount = new GuiCoins(Dim2D.flush(), this.shouldDisplayAlways);
		this.originalPrice = new GuiCoins(Dim2D.flush(), this.shouldDisplayAlways);

		this.origPriceBg = new GuiTextLabel(Dim2D.build().area(this.dim().width(), this.dim().height()).flush());

		this.context().addChildren(this.coinsCount, this.discountIndicator, this.origPriceBg, this.originalPrice);

		this.originalPrice.state().setZOrder(5);
		this.origPriceBg.state().setZOrder(5);

		this.origPriceBg.state().setEnabled(false);
		this.origPriceBg.state().setVisible(false);

		this.originalPrice.state().setVisible(false);
		this.originalPrice.state().setEnabled(false);

		float edge = -2.9F;

		GuiLine line = new GuiLine(-edge - 0.65F, -edge,
				this.originalPrice.dim().width() + edge + 0.65F, this.originalPrice.dim().height() + edge, 155, 0, 0, 255, 8.0F);

		this.originalPrice.context().addChildren(line);

		line.state().setZOrder(5);

		if (this.displaysDiscount)
		{
			this.discountIndicator.state().addEvent(new IGuiEvent()
			{
				@Override
				public void onHovered(IGuiElement element)
				{
					float x = -element.dim().x() + (float) InputHelper.getMouseX() + 3;
					float y = -element.dim().y() + (float) InputHelper.getMouseY() - GuiCoinsContainer.this.originalPrice.dim().height() - 3;

					GuiCoinsContainer.this.originalPrice.dim().mod().pos(x, y).flush();
					GuiCoinsContainer.this.origPriceBg.dim().mod().pos(x, y).flush();
				}

				@Override
				public void onHoverEnter(IGuiElement element)
				{
					GuiCoinsContainer.this.originalPrice.state().setVisible(true);
					GuiCoinsContainer.this.originalPrice.state().setEnabled(true);

					GuiCoinsContainer.this.origPriceBg.state().setEnabled(true);
					GuiCoinsContainer.this.origPriceBg.state().setVisible(true);
				}

				@Override
				public void onHoverExit(IGuiElement element)
				{
					GuiCoinsContainer.this.originalPrice.state().setVisible(false);
					GuiCoinsContainer.this.originalPrice.state().setEnabled(false);

					GuiCoinsContainer.this.origPriceBg.state().setEnabled(false);
					GuiCoinsContainer.this.origPriceBg.state().setVisible(false);
				}
			});
		}
	}

}
