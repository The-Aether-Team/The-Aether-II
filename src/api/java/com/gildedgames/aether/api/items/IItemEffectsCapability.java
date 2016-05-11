package com.gildedgames.aether.api.items;

import com.gildedgames.aether.api.entities.effects.EntityEffectInstance;
import com.gildedgames.aether.api.entities.effects.EntityEffectProcessor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface IItemEffectsCapability
{
	/**
	 * @return A list containing the effects the item tied to this capability will give an entity.
	 */
	List<Pair<EntityEffectProcessor, EntityEffectInstance>> getEffectPairs();
}
