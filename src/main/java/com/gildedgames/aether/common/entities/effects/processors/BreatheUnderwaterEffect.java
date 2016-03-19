package com.gildedgames.aether.common.entities.effects.processors;

import java.util.List;

import net.minecraft.entity.Entity;

import com.gildedgames.aether.common.entities.effects.AbstractEffectProcessor;
import com.gildedgames.aether.common.entities.effects.EffectInstance;


/**
 * Sets player's "air" level to 300, which is one bubble worth of air. This means allows
 * them to breathe under water.
 * @author Brandon Pearce
 */
public class BreatheUnderwaterEffect extends AbstractEffectProcessor<EffectInstance>
{
	
	public BreatheUnderwaterEffect()
	{
		super("ability.breatheUnderwater.name", "ability.breatheUnderwater.desc");
	}

	@Override
	public void tick(Entity source, List<EffectInstance> all)
	{
		source.setAir(300);
	}

}
