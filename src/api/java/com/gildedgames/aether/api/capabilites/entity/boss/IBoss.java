package com.gildedgames.aether.api.capabilites.entity.boss;

import net.minecraft.entity.Entity;

public interface IBoss<T extends Entity>
{

	IBossManager<T> getBossManager();

}
