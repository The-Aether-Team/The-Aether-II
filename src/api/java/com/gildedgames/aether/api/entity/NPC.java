package com.gildedgames.aether.api.entity;

import com.gildedgames.aether.api.shop.IShopInstanceGroup;

import javax.annotation.Nullable;

public interface NPC
{
	@Nullable
	IShopInstanceGroup getShopInstanceGroup();
}
