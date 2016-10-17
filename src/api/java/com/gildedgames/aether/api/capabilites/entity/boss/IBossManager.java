package com.gildedgames.aether.api.capabilites.entity.boss;

import com.gildedgames.util.io_manager.io.NBT;
import net.minecraft.entity.Entity;

import java.util.List;

public interface IBossManager<T extends Entity> extends NBT
{

	String getName();

	List<BossStage<T>> getStages();

	List<BossStageAction<T>> getActions();

	void addStageAction(BossStageAction<T> action);

	void updateStagesAndActions();

	boolean hasBegun(BossStage stage);

	boolean hasBegun(Class<? extends BossStage> stage);

}
