package com.gildedgames.aether.client.gui.dialog;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerCurrencyModule;
import com.gildedgames.orbis.lib.client.gui.data.Text;
import com.gildedgames.orbis.lib.client.gui.util.GuiText;
import com.gildedgames.orbis.lib.client.gui.util.GuiTexture;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.GuiElement;
import com.gildedgames.orbis.lib.client.rect.Dim2D;
import com.gildedgames.orbis.lib.client.rect.Pos2D;
import com.gildedgames.orbis.lib.client.rect.Rect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;

public class GuiCoins extends GuiElement
{

	public static final ResourceLocation GILT = AetherCore.getResource("textures/gui/shop/gilt.png");

	private Coin gilt;

	private double value;

	public GuiCoins(Rect rect, boolean shouldDisplayAlways)
	{
		super(rect, true);

		this.dim().mod().width(32).height(19).flush();
	}

	public double getCurrencyValue()
	{
		return this.value;
	}

	public void setCurrencyValue(double value)
	{
		this.value = value;

		this.gilt.setCount(value);

		this.gilt.state().setVisible(true);

		Pos2D center = Pos2D.flush(this.dim().width() / 2, this.dim().height() / 2);

		this.gilt.dim().mod().pos(center).center(true).flush();
	}

	@Override
	public void build()
	{
		this.gilt = new Coin(Dim2D.build().width(7).height(7).addX(0).addY(0).flush(), GILT);

		this.context().addChildren(this.gilt);
	}

	private static class Coin extends GuiElement
	{
		private GuiTexture icon;

		private final ResourceLocation iconResource;

		private GuiText count;

		public Coin(Rect rect, ResourceLocation icon)
		{
			super(rect, false);

			this.iconResource = icon;
		}

		public void setCount(double count)
		{
			String text = count < 1 && count > 0 ? TextFormatting.GRAY + String.format("%.2f", count) : String.valueOf((int) count);

			this.count.setText(new Text(new TextComponentString(text), 1.0F));

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
