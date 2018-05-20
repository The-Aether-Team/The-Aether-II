package com.gildedgames.aether.api.entity;

import com.gildedgames.aether.api.shop.IShopInstance;

import javax.annotation.Nullable;

public interface NPC
{

	@Nullable
	IShopInstance getShopInstance();

}
