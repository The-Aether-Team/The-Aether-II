package com.gildedgames.aether.api.shop;

import net.minecraft.util.ResourceLocation;

import java.util.Optional;
import java.util.Random;

public interface IShopManager
{

	Optional<IShopDefinition> getShopDefinition(final ResourceLocation resource);

	IShopInstance createInstance(IShopDefinition definition, Random rand);

}
