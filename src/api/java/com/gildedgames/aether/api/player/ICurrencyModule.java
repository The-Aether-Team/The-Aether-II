package com.gildedgames.aether.api.player;

import com.gildedgames.aether.api.shop.ICurrencyListener;

public interface ICurrencyModule
{
	long getCurrencyValue();

	void add(long gilt);

	void take(long gilt);

	void listen(ICurrencyListener listener);

	boolean unlisten(ICurrencyListener listener);
}
