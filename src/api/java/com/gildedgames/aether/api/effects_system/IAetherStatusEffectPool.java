package com.gildedgames.aether.api.effects_system;

import java.util.HashMap;

/**
 * Requires an EntityLivingBase being attached to it (recommended through constructor
 */
public interface IAetherStatusEffectPool
{

	/**
	 * Initialize and add all status effects.
	 */
	void initPool();

	/**
	 * Call supplied Tick() method for all StatusEffects in pool.
	 */
	void Tick();

	/**
	 * Handles all calls to add buildup to effects.
	 * Given the effectType and buildup, addBuildup to corresponding effect.
	 * Method should also handle any temporary additions to resistances.
	 * @param effectType The effect buildup should be applied to.
	 * @param buildup The amount of buildup to add to the effect.
	 */
	void applyStatusEffect(IAetherStatusEffects.effectTypes effectType, int buildup);

	/**
	 * @param effectType The effect to retrieve buildup from.
	 * @return integer representing the current effect's buildup.
	 */
	int getBuildupFromEffect(IAetherStatusEffects.effectTypes effectType);

	/**
	 * @param effectType The effect to retrieve application status information.
	 * @return boolean representing if the effect is being applied or not.
	 */
	boolean isEffectApplied(IAetherStatusEffects.effectTypes effectType);

	/**
	 * @param effectType The effect to retrive resistance information.
	 * @return double representing effect resistance.
	 */
	double getResistanceToEffect(IAetherStatusEffects.effectTypes effectType);

	/**
	 * Resistances to effects are multiplied towards buildup, given this they have the limits of 0.0 and 2.0.
	 * Due to multiplication principles, adding more resistance to an effect will lower that Entity's resistance. (buildup * 2.0resistance) =
	 * 																												buildup*2 additional buildup.
	 * Decreasing resistance to an effect will raise that Entity's resistance. (buildup * 0.0resistance) = 0 additional buildup.
	 * @param effectType The effect to apply resistance to.
	 * @param addResistance	The amount of resistance to add to effect (can be negative, to lower resistance number).
	 */
	void addResistanceToEffect(IAetherStatusEffects.effectTypes effectType, double addResistance);

	HashMap<String, IAetherStatusEffects> getPool();
}
