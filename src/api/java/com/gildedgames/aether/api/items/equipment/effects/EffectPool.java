package com.gildedgames.aether.api.items.equipment.effects;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A stateful representation of an effect for an entity.
 */
public class EffectPool
{
	private final Collection<IEffectInstance> instances = new ArrayList<>();

	private final IEffectProcessor<IEffectInstance, IEffectState> processor;

	private IEffectState state;

	public EffectPool(IEffectProcessor<IEffectInstance, IEffectState> processor)
	{
		this.processor = processor;
	}

	/**
	 * Removes an instance from this entity, and rebuilds the state as needed.
	 * @param instance The instance to remove
	 */
	public void removeInstance(IEffectInstance instance)
	{
		this.instances.remove(instance);

		this.rebuildState();
	}

	/**
	 * Adds an instance to this entity, and rebuilds the state as needed.
	 * @param instance The instance to add
	 */
	public void addInstance(IEffectInstance instance)
	{
		this.instances.add(instance);

		this.rebuildState();
	}

	/**
	 * Rebuilds the state of this effect from the active instances.
	 */
	private void rebuildState()
	{
		this.state = this.processor.createState(this.instances);
	}

	/**
	 * Used to determine if the entity has any instances of the effect. If not, the pool is removed
	 * from the entity.
	 * @return True if the entity has instances of this effect, otherwise false
	 */
	public boolean isEmpty()
	{
		return this.instances.size() <= 0;
	}

	/**
	 * @return The state of this effect for the entity
	 */
	public IEffectState getState()
	{
		return this.state;
	}

	/**
	 * @return The processor of this effect for the entity.
	 */
	public IEffectProcessor<IEffectInstance, IEffectState> getProcessor()
	{
		return this.processor;
	}
}
