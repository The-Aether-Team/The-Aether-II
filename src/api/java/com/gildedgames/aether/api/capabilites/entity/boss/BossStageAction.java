package com.gildedgames.aether.api.capabilites.entity.boss;

import com.gildedgames.util.io_manager.io.NBT;
import net.minecraft.entity.Entity;

public interface BossStageAction<T extends Entity> extends NBT
{

	boolean shouldRemove();

	void update(T entity);

}
