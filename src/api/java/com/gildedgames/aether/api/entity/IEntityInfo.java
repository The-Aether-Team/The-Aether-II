package com.gildedgames.aether.api.entity;

import net.minecraft.entity.Entity;

public interface IEntityInfo
{
	Entity lookingAtEntity();

	int getTicksEyesClosed();

	void update();
}
