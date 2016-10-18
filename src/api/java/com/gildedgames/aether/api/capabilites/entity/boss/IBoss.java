package com.gildedgames.aether.api.capabilites.entity.boss;

import com.gildedgames.aether.api.loot.LootPool;
import net.minecraft.entity.Entity;

public interface IBoss<T extends Entity>
{

	IBossManager<T> getBossManager();

	LootPool getLootPool();

}
