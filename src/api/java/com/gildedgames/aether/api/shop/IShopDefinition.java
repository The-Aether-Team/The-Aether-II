package com.gildedgames.aether.api.shop;

import java.util.Collection;
import java.util.List;

public interface IShopDefinition
{
	List<IShopBuyDefinition> getBuyDefinitions();

	Collection<String> getUnlocalizedGreetings();

	int getMaxStock();

	int getMinStock();
}
