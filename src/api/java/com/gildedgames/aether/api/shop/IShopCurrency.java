package com.gildedgames.aether.api.shop;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.orbis.lib.client.rect.Rect;
import com.gildedgames.orbis.lib.util.mc.NBT;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IShopCurrency extends NBT
{
	long getValue(IPlayerAether playerAether);

	void addValue(long value, IPlayerAether playerAether);

	void removeValue(long value, IPlayerAether playerAether);

	@SideOnly(Side.CLIENT)
	IGuiCurrencyValue createCurrencyValueGui(Rect rect);

	@SideOnly(Side.CLIENT)
	IGuiCurrencyValue createBuyItemCurrencyValueGui(Rect rect);

	@SideOnly(Side.CLIENT)
	IGuiCurrencyValue createSellItemCurrencyValueGui(Rect rect);

	void listenForCurrency(IPlayerAether playerAether, ICurrencyListener listener);

	boolean unlistenForCurrency(IPlayerAether playerAether, ICurrencyListener listener);

	void update(IPlayerAether playerAether);
}
