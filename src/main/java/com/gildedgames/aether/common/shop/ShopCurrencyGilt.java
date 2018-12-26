package com.gildedgames.aether.common.shop;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.api.shop.ICurrencyListener;
import com.gildedgames.aether.api.shop.IGuiCurrencyValue;
import com.gildedgames.aether.api.shop.IShopCurrency;
import com.gildedgames.aether.client.gui.dialog.GuiCoinsContainer;
import com.gildedgames.orbis_api.client.rect.Rect;
import net.minecraft.nbt.NBTTagCompound;

public class ShopCurrencyGilt implements IShopCurrency
{
	public ShopCurrencyGilt()
	{

	}

	@Override
	public long getValue(IPlayerAether playerAether)
	{
		return playerAether.getCurrencyModule().getCurrencyValue();
	}

	@Override
	public void addValue(long value, IPlayerAether playerAether)
	{
		playerAether.getCurrencyModule().add(value);
	}

	@Override
	public void removeValue(long value, IPlayerAether playerAether)
	{
		playerAether.getCurrencyModule().take(value);
	}

	@Override
	public IGuiCurrencyValue createCurrencyValueGui(Rect rect)
	{
		return new GuiCoinsContainer(rect, true, false);
	}

	@Override
	public IGuiCurrencyValue createBuyItemCurrencyValueGui(Rect rect)
	{
		return new GuiCoinsContainer(rect, false, true);
	}

	@Override
	public IGuiCurrencyValue createSellItemCurrencyValueGui(Rect rect)
	{
		return new GuiCoinsContainer(rect, false, false);
	}

	@Override
	public void listenForCurrency(IPlayerAether playerAether, ICurrencyListener listener)
	{
		playerAether.getCurrencyModule().listen(listener);
	}

	@Override
	public boolean unlistenForCurrency(IPlayerAether playerAether, ICurrencyListener listener)
	{
		return playerAether.getCurrencyModule().unlisten(listener);
	}

	@Override
	public void update(IPlayerAether playerAether)
	{

	}

	@Override
	public void write(NBTTagCompound tag)
	{

	}

	@Override
	public void read(NBTTagCompound tag)
	{

	}
}
