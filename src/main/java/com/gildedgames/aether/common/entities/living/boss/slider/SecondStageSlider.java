package com.gildedgames.aether.common.entities.living.boss.slider;

import com.gildedgames.aether.api.capabilites.entity.boss.BossStage;
import com.gildedgames.aether.api.capabilites.entity.boss.IBossManager;

public class SecondStageSlider extends BossStage<EntitySlider>
{

	public SecondStageSlider()
	{

	}

	@Override protected boolean conditionsMet(EntitySlider entity, IBossManager<EntitySlider> manager)
	{
		return entity.getHealth() <= 250;
	}

	@Override protected void onStageBegin(EntitySlider entity, IBossManager<EntitySlider> manager)
	{
		entity.setCritical(true);
	}

}
