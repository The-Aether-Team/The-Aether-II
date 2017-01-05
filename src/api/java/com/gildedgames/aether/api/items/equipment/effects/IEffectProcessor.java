package com.gildedgames.aether.api.items.equipment.effects;

import com.gildedgames.aether.api.capabilites.entity.IPlayerAetherCapability;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;

/***
 * Processors implement an effect for an entity.
 *
 * A {@link IEffectState} contains the actual instance of an effect on an entity, which can contain
 * variables such as how long the effect has been active.
 *
 * A {@link IEffectInstance} contains an immutable instance of an effect an equipment item provides. Instances
 * are used to build an {@link IEffectState} for the entity, and are shared globally throughout the mod.
 *
 * The {@link IEffectProcessor} is the mod's global implementor of an effect for any entity, and
 * does/should not contain any state information. **Doing so will lead to severe bugs.**
 *
 * @param <T> An immutable data structure for this effect's instances
 * @param <S> An object that will represent the state of an effect
 */
public interface IEffectProcessor<T extends IEffectInstance, S extends IEffectState>
{
	/**
	 * Called when the entity updates.
	 * @param player The player entity that is updating
	 * @param state The processor's state
	 */
	void onEntityUpdate(IPlayerAetherCapability player, S state);

	/**
	 * Adds effect tooltips to {@param label}.
	 * @param label The {@link Collection} to add to
	 * @param state The {@link S} the effect's state
	 */
	void addItemInformation(Collection<String> label, S state);

	/**
	 * Creates an {@link IEffectState} representing the effect's state of an entity.
	 * @param instances The instances of this effect the entity currently has
	 * @return The effect's state on the entity
	 */
	IEffectState createState(Collection<T> instances);

	/**
	 * The unique identifier used to link {@link IEffectInstance} and {@link IEffectState} to this effect. This
	 * should never change during runtime, but may across restarts and updates. Therefore, it is not recommended
	 * to serialize the identifier to disk for any reason.
	 * @return The {@link ResourceLocation} representing this effect's unique identifier
	 */
	ResourceLocation getIdentifier();
}
