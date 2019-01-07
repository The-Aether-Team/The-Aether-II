package com.gildedgames.aether.api.shop;

import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public interface IShopManager
{

	Optional<IShopDefinition> getShopDefinition(final ResourceLocation resource);

	IShopInstance createInstance(ResourceLocation definitionLocation, IShopDefinition definition, Random rand);

	List<IShopFilter> getGlobalFilters();

	void registerGlobalFilter(IShopFilter filter);

}
