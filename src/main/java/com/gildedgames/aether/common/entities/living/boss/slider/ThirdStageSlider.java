package com.gildedgames.aether.common.entities.living.boss.slider;

import com.gildedgames.aether.api.capabilites.entity.boss.BossStage;
import com.gildedgames.aether.api.capabilites.entity.boss.IBossManager;

public class ThirdStageSlider extends BossStage<EntitySlider>
{

	public ThirdStageSlider()
	{

	}

	@Override
	protected boolean conditionsMet(EntitySlider entity, IBossManager<EntitySlider> manager)
	{
		return entity.getHealth() <= 100;
	}

	@Override
	protected void onStageBegin(EntitySlider entity, IBossManager<EntitySlider> manager)
	{

	}

}
