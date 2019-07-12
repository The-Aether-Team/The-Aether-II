package com.gildedgames.aether.api.shop;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.orbis.lib.client.rect.Rect;
import com.gildedgames.orbis.lib.util.mc.NBT;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

public interface IShopCurrency extends NBT
{
	long getValue(IPlayerAether playerAether);

	void addValue(long value, IPlayerAether playerAether);

	void removeValue(long value, IPlayerAether playerAether);

	@OnlyIn(Dist.CLIENT)
	IGuiCurrencyValue createCurrencyValueGui(Rect rect);

	@OnlyIn(Dist.CLIENT)
	IGuiCurrencyValue createBuyItemCurrencyValueGui(Rect rect);

	@OnlyIn(Dist.CLIENT)
	IGuiCurrencyValue createSellItemCurrencyValueGui(Rect rect);

	void listenForCurrency(IPlayerAether playerAether, ICurrencyListener listener);

	boolean unlistenForCurrency(IPlayerAether playerAether, ICurrencyListener listener);

	void update(IPlayerAether playerAether);
}
