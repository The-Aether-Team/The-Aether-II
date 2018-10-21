package com.gildedgames.aether.api.shop;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.orbis_api.client.rect.Rect;
import com.gildedgames.orbis_api.util.mc.NBT;

public interface IShopCurrency extends NBT
{
	long getValue(IPlayerAether playerAether);

	void addValue(long value, IPlayerAether playerAether);

	void removeValue(long value, IPlayerAether playerAether);

	IGuiCurrencyValue createCurrencyValueGui(Rect rect);

	IGuiCurrencyValue createItemValueGui(Rect rect);

	void listenForCurrency(IPlayerAether playerAether, ICurrencyListener listener);

	boolean unlistenForCurrency(IPlayerAether playerAether, ICurrencyListener listener);

	void update(IPlayerAether playerAether);
}
