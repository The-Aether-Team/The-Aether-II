package com.gildedgames.aether.client.gui.dialog;

import com.gildedgames.aether.api.shop.IGuiCurrencyValue;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerCurrencyModule;
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

public class GuiCoins extends GuiElement implements IGuiCurrencyValue
{

	private static final ResourceLocation GILT = AetherCore.getResource("textures/gui/shop/gilt.png");

	private static final ResourceLocation GILTAE = AetherCore.getResource("textures/gui/shop/giltae.png");

	private static final ResourceLocation GILTAEN = AetherCore.getResource("textures/gui/shop/giltaen.png");

	private static final ResourceLocation GILTAENI = AetherCore.getResource("textures/gui/shop/giltaeni.png");

	private Coin gilt, giltae, giltaen, giltaeni;

	private boolean shouldDisplayAlways;

	public GuiCoins(Rect rect, boolean shouldDisplayAlways)
	{
		super(rect, true);

		this.dim().mod().width(51).height(19).flush();

		this.shouldDisplayAlways = shouldDisplayAlways;
	}

	@Override
	public void setCurrencyValue(double value)
	{
		double giltaeni = 0;
		double giltaen = 0;
		double giltae = 0;
		double gilt;

		if (value >= 1)
		{
			int[] brokenUp = PlayerCurrencyModule.breakUpCurrency((long) value);

			giltaeni = brokenUp[0];
			giltaen = brokenUp[1];
			giltae = brokenUp[2];
			gilt = brokenUp[3];
		}
		else
		{
			gilt = value;
		}

		this.gilt.setCount(gilt);
		this.giltae.setCount(giltae);
		this.giltaen.setCount(giltaen);
		this.giltaeni.setCount(giltaeni);

		this.giltaeni.state().setVisible(giltaeni > 0 || this.shouldDisplayAlways);
		this.giltaen.state().setVisible(giltaen > 0 || this.shouldDisplayAlways);
		this.giltae.state().setVisible(giltae > 0 || this.shouldDisplayAlways);
		this.gilt.state().setVisible(gilt > 0 || this.shouldDisplayAlways);

		Coin[] coins = new Coin[4];

		int index = 0;

		if (this.giltaeni.state().isVisible())
		{
			coins[index++] = this.giltaeni;
		}

		if (this.giltaen.state().isVisible())
		{
			coins[index++] = this.giltaen;
		}

		if (this.giltae.state().isVisible())
		{
			coins[index++] = this.giltae;
		}

		if (this.gilt.state().isVisible())
		{
			coins[index++] = this.gilt;
		}

		Pos2D center = Pos2D.flush(this.dim().width() / 2, this.dim().height() / 2);

		if (index == 1)
		{
			coins[0].dim().mod().pos(center).center(true).flush();
		}
		else if (index == 2)
		{
			float totalWidth = coins[0].dim().width() + coins[1].dim().width();

			coins[0].dim().mod().pos(center).centerY(true).centerX(false).addX((-totalWidth / 2) - 1).flush();
			coins[1].dim().mod().pos(center).centerY(true).centerX(false).x(coins[0].dim().originalState().x() + coins[0].dim().width() + 2).flush();
		}
		else if (index > 2)
		{
			float maxWidth = Math.max(coins[0].dim().width(), coins[2].dim().width());

			coins[0].dim().mod().center(false).x(0).y(0).flush();
			coins[1].dim().mod().center(false).x(maxWidth + 2).y(0).flush();

			coins[2].dim().mod().center(false).x(0).y(11).flush();

			if (coins[3] != null)
			{
				coins[3].dim().mod().center(false).x(maxWidth + 2).y(11).flush();
			}

			float maxBackWidth = Math.max(coins[1].dim().originalState().x() + coins[1].dim().width(),
					coins[3] != null ? coins[3].dim().originalState().x() + coins[3].dim().width() : 0);

			this.dim().mod().width(maxBackWidth).flush();
		}
	}

	@Override
	public void build()
	{
		this.gilt = new Coin(Dim2D.build().width(7).height(7).addX(0).addY(0).flush(), GILT);
		this.giltae = new Coin(Dim2D.build().width(7).height(7).addX(27).addY(0).flush(), GILTAE);
		this.giltaen = new Coin(Dim2D.build().width(7).height(7).addX(0).addY(15).flush(), GILTAEN);
		this.giltaeni = new Coin(Dim2D.build().width(7).height(7).addX(27).addY(15).flush(),
				GILTAENI);

		this.context().addChildren(this.gilt, this.giltae, this.giltaen, this.giltaeni);
	}

	private static class Coin extends GuiElement
	{
		private GuiTexture icon;

		private ResourceLocation iconResource;

		private GuiText count;

		public Coin(Rect rect, ResourceLocation icon)
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
