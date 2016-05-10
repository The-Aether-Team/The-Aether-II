package com.gildedgames.aether.items;

import com.gildedgames.aether.entities.effects.EntityEffectInstance;
import com.gildedgames.aether.entities.effects.EntityEffectProcessor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface IItemEffectsCapability
{
	List<Pair<EntityEffectProcessor, EntityEffectInstance>> getEffectPairs();
}
