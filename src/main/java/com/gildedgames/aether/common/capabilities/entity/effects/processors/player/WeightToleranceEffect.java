package com.gildedgames.aether.common.capabilities.entity.effects.processors.player;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.common.capabilities.entity.effects.AbstractEffectProcessorPlayer;

public class WeightToleranceEffect extends AbstractEffectProcessorPlayer<EntityEffectInstance>
{

	public WeightToleranceEffect()
	{
		super("ability.weightTolerance.localizedName", "ability.weightTolerance.desc");
	}

}
