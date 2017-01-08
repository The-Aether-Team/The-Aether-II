package com.gildedgames.aether.common.entities.effects;

import com.gildedgames.aether.api.items.equipment.effects.IEffect;
import com.gildedgames.aether.api.items.equipment.effects.IEffectInstance;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A stateful representation of an effect for an entity.
 */
public class EffectPool
{
	private final Collection<IEffectProvider> providers = new ArrayList<>();

	private final IEffect<IEffectProvider> factory;

	private IEffectInstance instance;

	public EffectPool(IEffect<IEffectProvider> factory)
	{
		this.factory = factory;
	}

	/**
	 * Removes an instance from this entity, and rebuilds the state as needed.
	 * @param instance The instance to remove
	 */
	public void removeInstance(IEffectProvider instance)
	{
		this.providers.remove(instance);

		this.rebuildState();
	}

	/**
	 * Adds an instance to this entity, and rebuilds the state as needed.
	 * @param instance The instance to add
	 */
	public void addInstance(IEffectProvider instance)
	{
		this.providers.add(instance);

		this.rebuildState();
	}

	/**
	 * Rebuilds the state of this effect from the active instances.
	 */
	private void rebuildState()
	{
		this.instance = this.factory.createInstance(this.providers);
	}

	/**
	 * Used to determine if the entity has any instances of the effect. If not, the pool is removed
	 * from the entity.
	 * @return True if the entity has instances of this effect, otherwise false
	 */
	public boolean isEmpty()
	{
		return this.providers.size() <= 0;
	}

	/**
	 * @return The state of this effect for the entity
	 */
	public IEffectInstance getInstance()
	{
		return this.instance;
	}

	/**
	 * @return The processor of this effect for the entity.
	 */
	public IEffect<IEffectProvider> getFactory()
	{
		return this.factory;
	}
}
