package com.gildedgames.aether.api.entity;

import com.gildedgames.aether.api.shop.IShopInstanceGroup;

import javax.annotation.Nullable;

public interface Character
{
	@Nullable
	IShopInstanceGroup getShopInstanceGroup();
}
