package com.gildedgames.aether.client.gui.dialog;

import com.gildedgames.aether.api.shop.IGuiCurrencyValue;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis_api.client.gui.data.Text;
import com.gildedgames.orbis_api.client.gui.util.GuiText;
import com.gildedgames.orbis_api.client.gui.util.GuiTexture;
import com.gildedgames.orbis_api.client.gui.util.gui_library.GuiElement;
import com.gildedgames.orbis_api.client.rect.Dim2D;
import com.gildedgames.orbis_api.client.rect.Pos2D;
import com.gildedgames.orbis_api.client.rect.Rect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class GuiPumpkinCurrency extends GuiElement implements IGuiCurrencyValue
{

	private static final ResourceLocation COIN_ICON = AetherCore.getResource("textures/gui/shop/plumproot_coin.png");

	private PlumprootCount plumprootCount;

	public GuiPumpkinCurrency(Rect rect)
	{
		super(rect, true);

		this.dim().mod().width(51).height(19).flush();
	}

	@Override
	public void setCurrencyValue(double value)
	{
		this.plumprootCount.setCount(value);

		this.plumprootCount.state().setVisible(true);

		Pos2D center = Pos2D.flush(this.dim().width() / 2, this.dim().height() / 2);

		this.plumprootCount.dim().mod().pos(center).center(true).flush();
	}

	@Override
	public void setNonFilteredCurrencyValue(double value)
	{

	}

	@Override
	public void build()
	{
		this.plumprootCount = new PlumprootCount(Dim2D.build().width(7).height(7).addX(0).addY(0).flush(), COIN_ICON);

		this.context().addChildren(this.plumprootCount);
	}

	private static class PlumprootCount extends GuiElement
	{
		private GuiTexture icon;

		private final ResourceLocation iconResource;

		private GuiText count;

		public PlumprootCount(Rect rect, ResourceLocation icon)
		{
			super(rect, false);

			this.iconResource = icon;
		}

		public void setCount(double count)
		{
			String text = count < 1 && count > 0 ? TextFormatting.GRAY + String.format("%.2f", count) : String.valueOf((int) count);

			this.count.setText(
					new Text(new TextComponentString(text), 1.0F));

			this.dim().mod().width(9 + (this.viewer().fontRenderer().getStringWidth(text))).height(8).flush();
		}

		@Override
		public void build()
		{
			this.icon = new GuiTexture(Dim2D.build().width(7).height(7).addX(0).addY(0).flush(), this.iconResource);
			this.count = new GuiText(Dim2D.build().addX(9).addY(0).flush(),
					new Text(new TextComponentString(String.valueOf(0)), 1.0F));

			this.context().addChildren(this.icon, this.count);
		}
	}

}
