package com.gildedgames.aether.api.travellers_guidebook;

/**
 * This MUST implement hashCode() as it is heavily
 * used in maps.
 */
public interface ITGCondition
{
	/**
	 * This identifier must be globally unique, representing both the
	 * base condition and the unique data that this particular condition
	 * is handling.
	 *
	 * E.G: Let's say you have a "SeeEntity" condition, and the entity
	 * that this particular condition is looking for is a "Pig". The unique
	 * identifier could be:
	 *
	 * seeEntity:entity.pig
	 * @return The unique identifier for this condition and its sub data.
	 */
	String getUniqueIdentifier();

	void subscribe(ITGConditionListener listener);
}
