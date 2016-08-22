package com.gildedgames.aether.common.entities.effects.processors;

import com.gildedgames.aether.common.entities.effects.AbstractEffectProcessor;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import net.minecraft.entity.Entity;

import java.util.List;

/**
 * Sets player's "air" level to 300, which is one bubble worth of air. This means allows
 * them to breathe under water.
 * @author Brandon Pearce
 */
public class BreatheUnderwaterEffect extends AbstractEffectProcessor<EntityEffectInstance>
{

	public BreatheUnderwaterEffect()
	{
		super("ability.breatheUnderwater.localizedName", "ability.breatheUnderwater.desc");
	}

	@Override
	public void tick(Entity source, List<EntityEffectInstance> all)
	{
		source.setAir(300);
	}

}
