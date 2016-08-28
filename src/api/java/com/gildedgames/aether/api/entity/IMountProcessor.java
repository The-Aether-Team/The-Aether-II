package com.gildedgames.aether.api.entity;

import net.minecraft.entity.Entity;

public interface IMountProcessor
{

	void onUpdate(Entity mount, Entity rider);

	void onHoldSpaceBar(Entity mount, Entity rider);

	float getMountedStepHeight(Entity mount);

	boolean canBeMounted(Entity mount);

	void onMountedBy(Entity mount, Entity rider);

	void onDismountedBy(Entity mount, Entity rider);

}
